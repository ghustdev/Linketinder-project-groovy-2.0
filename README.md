# Linketinder MVP - Projeto Full Stack

Plataforma de recrutamento em linha de comando com fluxo de vagas, curtidas e match bidirecional entre candidato e empresa.

<img width="1891" height="946" alt="mvp linketinder" src="https://github.com/user-attachments/assets/64d87f35-c135-4eb7-a5c3-d2a9bc809023" />

## Deploy
[linketinder.io](https://linketinder.vercel.app/)


## Estado atual

- Aplicação CLI funcional com menu expandido (opções `0` a `11`)
- Fluxo completo de vagas e curtidas implementado
- Match registrado quando há reciprocidade para a mesma vaga
- Suíte de testes automatizados com Spock validando modelos, serviços e repositório

## Funcionalidades

- Cadastro e listagem de empresas
- Cadastro e listagem de candidatos
- Cadastro de vagas por empresa
- Feed de vagas para candidato curtir
- Visualização de curtidas do candidato
- Visualização de curtidas recebidas pela empresa
- Curtiu de volta da empresa com geração de match
- Listagem de todos os matches, matches por candidato e por empresa
- Carga inicial em memória: `7` empresas, `7` candidatos e `2` vagas

## Stack

- Groovy `4.0.15`
- Gradle Wrapper `9.2.0`
- Spock `2.3` (Groovy 4)
- JUnit Platform
- Mockito (disponível no projeto)

## Pré-requisitos

- JDK 17+
- Terminal Bash

## Execução

```bash
groovy src/main/groovy/Main.groovy
```

## Testes

```bash
./gradlew test
```

Resultado da execução mais recente:

- `BUILD SUCCESSFUL`

Relatórios:

- `build/reports/tests/test/index.html`
- `build/test-results/test/`

## Estrutura principal

```text
src/main/groovy
├── Main.groovy
├── model
│   ├── Candidato.groovy
│   ├── Curtida.groovy
│   ├── Empresa.groovy
│   ├── InterfacePessoa.groovy
│   ├── Match.groovy
│   ├── Pessoa.groovy
│   └── Vaga.groovy
├── repository
│   └── Repository.groovy
├── services
│   ├── PessoaServices.groovy
│   ├── SistemaCurtidas.groovy
│   └── VagaServices.groovy
└── view
    ├── Cli.groovy
    └── Cli*Action.groovy

src/test/groovy
├── model
├── repository
└── services
```

## Documentação

- [Visão técnica e arquitetura](docs/PROJETO.md)
