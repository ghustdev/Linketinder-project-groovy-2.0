# Linketinder (Groovy CLI Version)

MVP de uma plataforma de recrutamento em linha de comando inspirada no conceito de *match* por competências.

## Status atual

O projeto está funcional como aplicação CLI e já possui testes automatizados para a camada de serviço.

## Funcionalidades implementadas

- Menu interativo no terminal
- Cadastro de empresas
- Cadastro de candidatos
- Listagem de empresas
- Listagem de candidatos
- Carga inicial com dados em memória (7 candidatos e 7 empresas)

## Estrutura do projeto

```text
src/main/groovy
├── Main.groovy
├── model
│   ├── Candidato.groovy
│   ├── Empresa.groovy
│   ├── InterfacePessoa.groovy
│   └── Pessoa.groovy
├── repository
│   └── Repository.groovy
├── services
│   └── PessoaServices.groovy
└── view
    └── Interface.groovy

src/test/groovy
└── services
    └── PessoaServicesTest.groovy
```

#### Modelagem UML Básica (apenas para visualizar)
<div align="center">
    <img width="721" height="541" alt="UML Linketinder Groovy 2 0 drawio" src="https://github.com/user-attachments/assets/81d8d3b9-a33a-439a-b136-34a640adf317" />
</div>

## Tecnologias

- Groovy 4
- Gradle (wrapper: 9.2.0)
- Spock Framework
- JUnit Platform
- Mockito (dependência disponível no projeto)

## Pré-requisitos

- JDK 17+
- Bash/terminal

## Como executar

Na raiz do projeto:

```bash
groovy src/main/groovy/Main.groovy
```

Menu disponível:

- `[1]` Adicionar empresa
- `[2]` Adicionar candidato
- `[3]` Listar empresas
- `[4]` Listar candidatos
- `[0]` Encerrar

## Como testar

Executar todos os testes:

```bash
./gradlew test
```

Relatórios gerados em:

- `build/reports/tests/test/index.html`
- `build/test-results/test/`

## Documentação detalhada

- [Documentação técnica do projeto](docs/PROJETO.md)
- [Documentação de testes](docs/TESTES.md)

## Limitações atuais

- Persistência apenas em memória
- Sem validações de formato/consistência (CPF, CNPJ, e-mail, CEP)
- Sem mecanismo de match automático entre candidatos e empresas

## Próximos passos sugeridos

- Persistência em banco de dados
- Validação de entrada e tratamento de erros por regra de domínio
- Implementação de algoritmo de match por skills
- Expansão da cobertura de testes (camadas de repositório e interface)
