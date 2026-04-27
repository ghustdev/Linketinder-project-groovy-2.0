# Documentação do Projeto Backend

## 1. Visão geral

O **Linketinder Backend** é uma aplicação CLI em Groovy para recrutamento com persistência em PostgreSQL via JDBC.
O fluxo principal cobre:

- gestão de candidatos e empresas
- publicação de vagas
- curtidas de candidato em vaga
- curtidas de empresa em candidato
- geração de match quando há reciprocidade para a mesma vaga

O sistema mantém separação por camadas (`view`, `services`, `dao`, `repositories`, `entities`) e usa DAOs para acesso ao banco.
Nesta versão, o MVC fica explícito com a adição da camada `controllers`.

## 2. Arquitetura

### 2.1 Camadas

- **View (`view/*Cli.groovy`)**
  - menu interativo (`MenuCli`) e entrada via `IEntradaConsolePadrao` / `EntradaConsolePadrao`
  - coordena a interação do usuário e delega para `controllers/*Controller`

- **Controllers (`controllers/*Controller.groovy`)**
  - camada de aplicação: recebe ações vindas do CLI e delega para `services/*Service`
  - mantém o `view` mais enxuto e deixa o MVC explícito

- **Services**
  - `CandidatoService`, `EmpresaService`, `VagaService`: regras e orquestração de persistência via DAO
  - `CurtidaService`: regras de curtida/match em memória em runtime

- **Repositories (`repositories/*`)**
  - interfaces de persistência e de "storage" em memória
  - `MatchRepositoryParaMock` é a implementação em memória usada para curtidas/matches

- **DAO (`dao.*`)**
  - `CandidatoDao`, `EmpresaDao`, `VagaDao`, `CompetenciaDao`
  - acesso ao PostgreSQL usando `PreparedStatement`
  - `ConexaoDB` centraliza ciclo de vida e criação da conexão (ver seção 10)

### 2.2 Ponto de entrada

`Main.groovy` instancia as dependências e conecta as camadas:

- `*Dao` (JDBC)
- `*Service`
- `*Controller`
- `MatchRepositoryParaMock` + `CurtidaService`
- `*Cli` + `MenuCli`

Depois inicia o loop principal com `menuCli.menuPrincipal()`.

## 3. Modelo de domínio

### 3.1 Interface base (`InterfacePessoa`)

`Pessoa` concentra atributos comuns (`nome`, `email`, `pais`, `cep`, `descricao`) e é estendida por `Candidato` e `Empresa`.

### 3.2 `Candidato`

- campos: `cpf`, `sobrenome`, `nascimento`, `formacao`, `linkedin`, `competencias`
- `competencias` é uma lista de objetos `Competencia`

### 3.3 `Empresa`

- campos: `cnpj`, `descricao`, `pais`, `cep`, `email`, `nome`

### 3.4 `Vaga`

- campos: `id`, `titulo`, `descricao`, `estado`, `cidade`, `empresa`, `competenciasRequeridas`
- `competenciasRequeridas` é uma lista de objetos `Competencia`

### 3.5 `Curtida`

Representa a ação de curtir uma vaga por um candidato (`candidato`, `vaga`, `empresa`, `dataCurtida`).

### 3.6 `Match`

Representa a reciprocidade: empresa curtiu o candidato **e** o candidato já tinha curtido uma vaga daquela empresa (`candidato`, `empresa`, `vaga`, `dataMatch`).

## 4. Menu CLI atual

- `[1]` Adicionar empresa (com vaga)
- `[2]` Adicionar candidato
- `[3]` Listar empresas
- `[4]` Listar candidatos
- `[5]` Empresa cadastrar vaga (empresa já cadastrada)
- `[6]` Empresa visualizar curtidas e curtir candidato
- `[7]` Candidato visualizar vagas e curtir vaga
- `[8]` Candidato visualizar curtidas
- `[9]` Listar todos os matches
- `[10]` Listar matches de um candidato
- `[11]` Listar matches de uma empresa
- `[0]` Encerrar

## 5. Fluxos principais

### 5.1 Cadastro de empresa e vaga

1. Seleciona opção `[1]`
2. Informa dados da empresa
3. Em seguida informa dados da vaga (estado/cidade e competências)
4. `VagaService.criarVaga(...)` persiste a vaga no BD

### 5.2 Candidato curte vaga

1. Seleciona opção `[7]`
2. Escolhe candidato pelo CPF
3. Visualiza vagas e informa ID
4. `CurtidaService.candidatoCurteVaga(...)` registra a curtida no repositório em memória

### 5.3 Empresa curte candidato e gera match

1. Seleciona opção `[6]`
2. Escolhe empresa pelo CNPJ
3. Visualiza curtidas recebidas nas suas vagas
4. Seleciona candidato (CPF) + vaga (ID)
5. `CurtidaService.empresaCurteCandidato(...)` tenta gerar match
6. Match só é salvo quando a curtida do candidato já existe para a mesma vaga

## 6. Persistência e estado em runtime

- PostgreSQL persiste: `candidatos`, `empresas`, `vagas`, `competencias` e as tabelas de relacionamento.
- Curtidas e matches: ficam em memória durante a execução, via `MatchRepositoryParaMock`.
  - reiniciar a aplicação apaga curtidas/matches

## 7. Build e execução

Executar aplicação:

```bash
groovy src/main/groovy/Main.groovy
```

Executar testes (observação abaixo):

```bash
./gradlew test
```

## 8. Limitações atuais

- sem validação formal de CPF/CNPJ/CEP (o CLI lê texto livre)
- sem autenticação/perfis (empresa/candidato/admin)
- sem API HTTP (somente CLI)
- curtidas/matches não persistem no banco

## 9. Banco de dados

Scripts SQL:

- `docs/db_linketinder_ddl.sql` (schema)
- `docs/db_linketinder_dml.sql` (massa de dados/exemplos)
- `docs/db_linketinder_dcl.sql` (consultas úteis)

### 9.1 CPF/CNPJ (formato)

Os scripts DDL assumem armazenamento **somente dígitos**:

- CPF: `CHAR(11)`
- CNPJ: `CHAR(14)`

Como o backend atualmente **não normaliza** entrada (não remove máscara), use CPF/CNPJ sem pontuação ao digitar no CLI.

## 10. Configuração de conexão

O backend lê `linketinder.properties` (na raiz do projeto) para conectar no PostgreSQL:

- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`

### 10.1 Ciclo de vida da conexão (ConexaoDB)

O acesso ao banco é centralizado em `dao.ConexaoDB`:

- carrega as propriedades uma única vez (cache em memória)
- cria e mantém uma única `java.sql.Connection` reutilizada durante a execução do CLI
- o ponto de entrada (`Main.groovy`) fecha a conexão no encerramento chamando `ConexaoDB.fecharConexao()` em um bloco `finally`

Essa escolha é intencional para o cenário atual (aplicação CLI, single-thread). Em cenários concorrentes/produção, o caminho natural é evoluir para um pool (`DataSource`).

### 10.2 Factory Method para criação da conexão

`ConexaoDB` não cria a conexão “direto” de forma espalhada. A criação é delegada para um **Factory Method** via a interface `dao.IConexaoFactory`:

- implementação padrão: `dao.ConexaoJDBCFactory` (JDBC puro via `DriverManager`)
- para trocar o mecanismo de conexão (ex.: outro driver, pool, etc.), basta implementar `IConexaoFactory` e configurar no início da aplicação com `ConexaoDB.definirFabrica(...)` (antes do primeiro acesso ao banco)
