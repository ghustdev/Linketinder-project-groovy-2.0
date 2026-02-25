# Linketinder (Groovy CLI)

MVP de uma plataforma de recrutamento inspirada no conceito de *match* do Tinder com foco em competências técnicas, no estilo LinkedIn.

## Contexto do projeto

Este projeto foi desenvolvido a partir do desafio proposto no **Acelera ZG**: criar uma solução prática para aproximar empresas e candidatos com base em habilidades, reduzindo ruído de relevância social e priorizando aderência técnica. Mas vale também o lado da zoeira kkkk

## Objetivo do MVP

Entregar uma aplicação de terminal simples para validar o fluxo principal de cadastro e visualização de perfis de:

- Candidatos
- Empresas

Cada perfil possui dados cadastrais e lista de competências (*skills*).

## Funcionalidades implementadas

- Menu interativo no terminal
- Listagem de empresas cadastradas
- Listagem de candidatos cadastrados
- Cadastro de novas empresas
- Cadastro de novos candidatos
- Carga inicial com dados pré-cadastrados (7 candidatos e 7 empresas)

## Requisitos do desafio

### Obrigatórios

- Estruturas para candidatos e empresas com os campos exigidos
- Atributo de competências para ambos os tipos
- Menu simples de terminal para listagem de dados
- Base inicial com no mínimo 5 candidatos e 5 empresas

### Opcionais

- Cadastro de novos candidatos e empresas implementado

## Arquitetura atual

A aplicação segue um MVC com separação simples por camadas:

- `src/Main.groovy`: ponto de entrada da aplicação
- `src/view/Interface.groovy`: menu CLI e interação com usuário
- `src/services/PessoaServices.groovy`: regras de criação de candidatos e empresas
- `src/repository/Repository.groovy`: armazenamento em memória e dados iniciais
- `src/model/Pessoa.groovy`: interface e classe base (`Pessoa`)
- `src/model/Cadidato.groovy`: entidade `Candidato`
- `src/model/Empresa.groovy`: entidade `Empresa`

## Tecnologias

- Groovy 5
- Execução em linha de comando
- Estruturas em memória (`List`) para persistência temporária

## Como executar

### Pré-requisitos

- JDK 17+ (recomendado)
- Groovy instalado no ambiente

### Execução

Na raiz do projeto:

```bash
groovy src/Main.groovy
```

Após iniciar, use o menu:

- `[1]` Adicionar empresa
- `[2]` Adicionar candidato
- `[3]` Listar empresas
- `[4]` Listar candidatos
- `[0]` Encerrar

## Estado do projeto

Projeto em estágio de MVP, com foco em validação funcional local via terminal.

Evoluções naturais para próximas versões:

- Persistência em banco de dados
- Validação robusta de CPF/CNPJ, e-mail e CEP
- Regras de match entre skills de candidatos e empresas
- Testes automatizados
- API REST e interface web

## Licença

Este projeto está sob a licença MIT. Consulte `LICENSE` para detalhes.
