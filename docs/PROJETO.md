# Documentação do Projeto

## 1. Visão geral

O **Linketinder** é um MVP em Groovy que executa no terminal para cadastro e listagem de candidatos e empresas com foco em competências técnicas.

O sistema é orientado a objetos, com armazenamento em memória e separação em camadas simples: interface (CLI), serviço e repositório.

## 2. Arquitetura MVC

### 2.1 Camadas

- **View (`view.Interface`)**
  - Exibe o menu de interação
  - Coleta entradas do usuário via `Scanner`
  - Chama os serviços e o repositório para listar/cadastrar dados

- **Service (`services.PessoaServices`)**
  - Centraliza criação de objetos `Candidato` e `Empresa`
  - Encapsula uso de builders das entidades

- **Repository (`repository.Repository`)**
  - Mantém listas em memória (`arrayCandidatos`, `arrayEmpresas`)
  - Inicializa a base com dados de exemplo

- **Model (`model.*`)**
  - `Pessoa`: classe base abstrata
  - `InterfacePessoa`: contrato com campos comuns
  - `Candidato` e `Empresa`: entidades principais

### 2.2 Ponto de entrada

- `Main.groovy`
  - Instancia `Repository`
  - Instancia `PessoaServices`
  - Instancia `Interface`
  - Inicia o menu com `cliMenu()`

## 3. Modelo de dados

## 3.1 Campos comuns (`InterfacePessoa`)

- `name`
- `description`
- `email`
- `state`
- `cep`
- `skills` (`List<String>`)

## 3.2 Candidato

Campos específicos:

- `cpf`
- `old` (idade)

## 3.3 Empresa

Campos específicos:

- `cnpj`
- `country`

## 4. Fluxos principais

## 4.1 Cadastro de candidato

1. Usuário seleciona opção `[2]`
2. CLI coleta os dados
3. `PessoaServices.createCandidato(...)` cria entidade via builder
4. Entidade é adicionada em `repository.arrayCandidatos`

## 4.2 Cadastro de empresa

1. Usuário seleciona opção `[1]`
2. CLI coleta os dados
3. `PessoaServices.createEmpresa(...)` cria entidade via builder
4. Entidade é adicionada em `repository.arrayEmpresas`

## 4.3 Listagens

- Opção `[3]`: percorre `arrayEmpresas` e imprime cada empresa
- Opção `[4]`: percorre `arrayCandidatos` e imprime cada candidato

## 5. Dados iniciais

O repositório carrega automaticamente:

- 7 candidatos
- 7 empresas

Isso permite usar o sistema sem necessidade de cadastro prévio.

## 6. Build e execução

### 6.1 Build e testes

```bash
./gradlew test
```

### 6.2 Execução da aplicação CLI

```bash
groovy src/main/groovy/Main.groovy
```

## 7. Limitações conhecidas

- Persistência apenas em memória
- Não há validação formal de CPF/CNPJ/e-mail/CEP
- Não há mecanismo de match por skills entre candidatos e empresas
- Não há API HTTP, apenas CLI

## 8. Evolução recomendada

- Criar camada de validação de domínio
- Adicionar persistência (ex.: PostgreSQL)
- Implementar casos de uso de match
- Evoluir estrutura para testes de integração e contrato
