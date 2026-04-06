# Documentação do Projeto Backend

## 1. Visão geral

O **Linketinder Backend** é uma aplicação CLI em Groovy para recrutamento com persistência em PostgreSQL via JDBC.
O fluxo principal cobre:

- gestão de candidatos e empresas
- publicação de vagas
- curtidas de candidato em vaga
- curtidas de empresa em candidato
- geração de match quando há reciprocidade para a mesma vaga

O sistema mantém a separação por camadas (`view`, `services`, `repository`, `model`) e usa DAOs para acesso ao banco.

## 2. Arquitetura

### 2.1 Camadas

- **View (`view.Cli` + `Cli*Action`)**
  - menu interativo e entrada via `Scanner`
  - delega cada ação para classes específicas (`CliCreateVagaAction`, `CliListMatchesAction`, etc.)

- **Services**
  - `PessoaServices`: criação e listagem de candidatos/empresas via JDBC
  - `VagaServices`: busca de empresa/candidato/vaga, criação e listagem de vagas via JDBC
  - `SistemaCurtidas`: registro de curtidas e manutenção de matches

- **Repository (`repository.Repository`)**
  - mantém listas em memória para uso pelas regras de Curtidas/Matches
  - é carregado a partir do banco no início da aplicação

- **Model (`model.*`)**
  - `Pessoa` + `InterfacePessoa`: estrutura base
  - `Candidato`, `Empresa`, `Vaga`, `Competencia`, `Curtida`, `Match`

- **DAO (`dao.*`)**
  - `CandidatoDao`, `EmpresaDao`, `VagaDao`, `CompetenciaDao`
  - acesso ao PostgreSQL usando `PreparedStatement`

### 2.2 Ponto de entrada

`Main.groovy` instancia:

- `Repository`
- `PessoaServices` (com DAOs)
- `VagaServices` (com DAOs)
- `SistemaCurtidas`
- `Cli`

Depois carrega os dados do banco no `Repository` e inicia o loop principal com `cli.cliMenu()`.

## 3. Modelo de domínio

### 3.1 Interface base (`InterfacePessoa`)

- `name`
- `description`
- `email`
- `cep`
- `pais`

### 3.2 `Candidato`

- campos específicos: `cpf`, `sobrenome`, `data_nascimento`, `pais`, `senha_hash`
- `skills` é uma lista de objetos `Competencia`
- mantém `vagasCurtidas`
- regras:
  - impede curtida duplicada na mesma vaga (`jaCortiuVaga`)
  - cria objeto `Curtida` ao curtir vaga

### 3.3 `Empresa`

- campos específicos: `cnpj`, `pais`, `senha_hash`
- mantém `candidatosCurtidos`
- regras:
  - valida candidato e vaga obrigatórios
  - valida se a vaga pertence à empresa
  - impede curtida duplicada para mesmo candidato + vaga
  - retorna `Match` apenas quando há reciprocidade

### 3.4 `Vaga`

- `id`, `nome`, `descricao`, `empresa`, `estado`, `cidade`, `skillsRequests`
  - `skillsRequests` é uma lista de objetos `Competencia`

### 3.5 `Curtida`

- referência para `candidato`, `vaga`, `empresa` e `date`

### 3.6 `Match`

- referência para `candidato`, `empresa`, `vaga` e `dateMatch`
- regra `isMatch(...)` compara curtidas usando chave composta:
  - `cpf|cnpj|vagaId`

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
4. `VagaServices.createVaga(...)` persiste a vaga no BD

### 5.2 Candidato curte vaga

1. Seleciona opção `[7]`
2. Escolhe candidato pelo CPF
3. Visualiza vagas e informa ID
4. `SistemaCurtidas.candidatoCurteVaga(...)` registra curtida global e no candidato

### 5.3 Empresa curte candidato e gera match

1. Seleciona opção `[6]`
2. Escolhe empresa pelo CNPJ
3. Visualiza curtidas recebidas nas suas vagas
4. Seleciona candidato (CPF) + vaga (ID)
5. `SistemaCurtidas.empresaCurteCandidato(...)` tenta gerar match
6. Match só é salvo quando a curtida do candidato já existe para a mesma vaga

## 6. Dados de bootstrap

Não há dados de bootstrap no código. Os dados vêm do banco PostgreSQL.

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

- sem validação formal de CPF/CNPJ/CEP além de unicidade
- sem autenticação/perfis (empresa/candidato/admin)
- sem API HTTP (somente CLI)
- testes antigos ainda referem-se ao modelo em memória e podem falhar

## 9. Banco de dados

O script principal do schema está em:

- `docs/db_linketinder.sql`

### 9.1 Variáveis de ambiente

O backend usa JDBC. Configure com:

- `DB_URL` (ex.: `jdbc:postgresql://localhost:5432/linketinder`)
- `DB_USER`
- `DB_PASSWORD`

Se não forem definidos, o sistema usa:

- `jdbc:postgresql://localhost:5432/linketinder`
- usuário `postgres`
- senha `postgres`
