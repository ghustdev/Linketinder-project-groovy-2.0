# Documentação do Projeto

## 1. Visão geral

O **Linketinder** é uma aplicação CLI em Groovy para recrutamento, com fluxo de:

- gestão de candidatos e empresas
- publicação de vagas
- curtidas de candidato em vaga
- curtidas de empresa em candidato
- geração de match quando há reciprocidade para a mesma vaga

O sistema roda com armazenamento em memória e separação por camadas (`view`, `services`, `repository`, `model`).

## 2. Arquitetura

### 2.1 Camadas

- **View (`view.Cli` + `Cli*Action`)**
  - menu interativo e entrada via `Scanner`
  - delega cada ação para classes específicas (`CliCreateVagaAction`, `CliListMatchesAction`, etc.)

- **Services**
  - `PessoaServices`: criação e listagem de candidatos/empresas
  - `VagaServices`: busca de empresa/candidato/vaga, criação e listagem de vagas
  - `SistemaCurtidas`: registro de curtidas e manutenção de matches

- **Repository (`repository.Repository`)**
  - listas em memória para candidatos, empresas, vagas, curtidas e matches
  - carga inicial de dados para uso imediato da CLI

- **Model (`model.*`)**
  - `Pessoa` + `InterfacePessoa`: estrutura base
  - `Candidato`, `Empresa`, `Vaga`, `Curtida`, `Match`

### 2.2 Ponto de entrada

`Main.groovy` instancia:

- `Repository`
- `PessoaServices`
- `VagaServices`
- `SistemaCurtidas`
- `Cli`

Depois inicia o loop principal com `cli.cliMenu()`.

## 3. Modelo de domínio

### 3.1 Interface base (`InterfacePessoa`)

- `name`
- `description`
- `email`
- `state`
- `cep`
- `skills`

### 3.2 `Candidato`

- campos específicos: `cpf`, `old`
- mantém `vagasCurtidas`
- regras:
  - impede curtida duplicada na mesma vaga (`jaCortiuVaga`)
  - cria objeto `Curtida` ao curtir vaga

### 3.3 `Empresa`

- campos específicos: `cnpj`, `country`
- mantém `candidatosCurtidos`
- regras:
  - valida candidato e vaga obrigatórios
  - valida se a vaga pertence à empresa
  - impede curtida duplicada para mesmo candidato + vaga
  - retorna `Match` apenas quando há reciprocidade

### 3.4 `Vaga`

- `id`, `title`, `description`, `empresa`, `skillsRequests`

### 3.5 `Curtida`

- referência para `candidato`, `vaga`, `empresa` e `date`

### 3.6 `Match`

- referência para `candidato`, `empresa`, `vaga` e `dateMatch`
- regra `isMatch(...)` compara curtidas usando chave composta:
  - `cpf|cnpj|vagaId`

## 4. Menu CLI atual

- `[1]` Adicionar empresa
- `[2]` Adicionar candidato
- `[3]` Listar empresas
- `[4]` Listar candidatos
- `[5]` Empresa cadastrar vaga
- `[6]` Empresa visualizar curtidas e curtir candidato
- `[7]` Candidato visualizar vagas e curtir vaga
- `[8]` Candidato visualizar curtidas
- `[9]` Listar todos os matches
- `[10]` Listar matches de um candidato
- `[11]` Listar matches de uma empresa
- `[0]` Encerrar

## 5. Fluxos principais

### 5.1 Empresa cria vaga

1. Seleciona opção `[5]`
2. Escolhe empresa pelo CNPJ
3. Informa dados da vaga
4. `VagaServices.createVaga(...)` cria com próximo ID sequencial

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

Inicialização automática no `Repository`:

- `7` empresas
- `7` candidatos
- `2` vagas

## 7. Build e execução

Executar aplicação:

```bash
groovy src/main/groovy/Main.groovy
```

Executar testes:

```bash
./gradlew test
```

## 8. Limitações atuais

- persistência apenas em memória
- sem validação formal de CPF/CNPJ/e-mail/CEP
- sem autenticação/perfis (empresa/candidato/admin)
- sem API HTTP (somente CLI)
