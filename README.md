# Linketinder

Plataforma de recrutamento inspirada no modelo de match bidirecional: candidatos e empresas se visualizam anonimamente e só se conectam quando há interesse mútuo pela mesma vaga.

<img width="1891" height="946" alt="mvp linketinder" src="https://github.com/user-attachments/assets/64d87f35-c135-4eb7-a5c3-d2a9bc809023" />

## Deploy

[linketinder.vercel.app](https://linketinder.vercel.app/)

---

## Visão geral

O projeto é composto por dois módulos independentes:

| Módulo | Tecnologia | Descrição |
|---|---|---|
| [`backend-linketinder`](./backend-linketinder/) | Groovy + Gradle + PostgreSQL | API REST via Servlets com persistência em banco de dados |
| [`frontend-linketinder`](./frontend-linketinder/) | TypeScript + Vite | SPA com feed de vagas/candidatos, cadastro e gráfico de competências |

---

## Funcionalidades

- Cadastro e listagem de candidatos e empresas
- Cadastro de vagas vinculadas a uma empresa
- Feed de vagas para candidato curtir (empresa permanece anônima)
- Feed de candidatos para empresa visualizar (candidato permanece anônimo)
- Curtida de empresa em candidato com geração automática de match quando há reciprocidade
- Listagem de matches globais, por candidato e por empresa
- Gráfico de barras com frequência de competências dos candidatos

---

## Arquitetura

```
Linketinder/
├── backend-linketinder/   # API REST Groovy — entities / dao / services / controllers / view
└── frontend-linketinder/  # SPA TypeScript — models / repository / services
```

O backend segue arquitetura em camadas (`view → controllers → services → dao → entities`) com persistência em PostgreSQL via JDBC puro e servidor Tomcat 9 via Gretty.

O frontend persiste dados no LocalStorage do navegador e não depende de nenhum servidor (integração com a API planejada para etapa futura).

---

## Backend

### Stack

- Groovy `4.0`
- Gradle Wrapper
- Apache Tomcat `9.0` via Gretty `3.1.8`
- PostgreSQL + JDBC puro
- Google Gson (serialização JSON)
- Spock `2.3` (Groovy 4) + JUnit Platform

### Pré-requisitos

- JDK 17+
- PostgreSQL rodando localmente
- Arquivo `linketinder.properties` na raiz do módulo:

```properties
DB_URL=jdbc:postgresql://localhost:5432/db_linketinder
DB_USER=postgres
DB_PASSWORD=postgres
```

### Banco de dados

Antes de subir a aplicação, execute os scripts na ordem:

```bash
psql -U postgres -f docs/db_linketinder_ddl.sql   # cria schema
psql -U postgres -f docs/db_linketinder_dml.sql   # insere dados de exemplo
```

Consultas de validação disponíveis em `docs/db_linketinder_dcl.sql`.

### Schema

| Tabela | Descrição |
|---|---|
| `candidatos` | Dados pessoais do candidato |
| `empresas` | Dados da empresa |
| `vagas` | Vagas vinculadas a uma empresa (1:N) |
| `competencias` | Lista única de competências |
| `candidato_competencia` | N:N entre candidatos e competências |
| `vaga_competencia` | N:N entre vagas e competências |

### Executar

```bash
cd backend-linketinder
./gradlew appRun
```

API disponível em `http://localhost:8080`.

### Testes

```bash
cd backend-linketinder
./gradlew test
```

Relatórios gerados em `build/reports/tests/test/index.html`.

### Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/candidatos` | Cadastra candidato |
| `GET` | `/candidatos` | Lista todos os candidatos |
| `GET` | `/candidatos?cpf=` | Busca candidato por CPF |
| `POST` | `/empresas` | Cadastra empresa |
| `GET` | `/empresas` | Lista todas as empresas |
| `GET` | `/empresas?cnpj=` | Busca empresa por CNPJ |
| `POST` | `/vagas` | Cadastra vaga |
| `GET` | `/vagas` | Lista todas as vagas |
| `GET` | `/vagas?id=` | Busca vaga por ID |

Documentação completa dos payloads e status codes em [`docs/API_REST.md`](./backend-linketinder/docs/API_REST.md).

### Menu CLI (modo alternativo)

O backend também pode ser executado via CLI sem o servidor:

```bash
groovy src/main/groovy/Main.groovy
```

| Opção | Ação |
|---|---|
| `1` | Adicionar empresa (com vaga) |
| `2` | Adicionar candidato |
| `3` | Listar empresas |
| `4` | Listar candidatos |
| `5` | Empresa cadastrar vaga |
| `6` | Empresa visualizar curtidas e curtir candidato |
| `7` | Candidato visualizar vagas e curtir vaga |
| `8` | Candidato visualizar curtidas recebidas |
| `9` | Listar todos os matches |
| `10` | Listar matches de um candidato |
| `11` | Listar matches de uma empresa |
| `0` | Encerrar |

### Estrutura

```
src/main/groovy/
├── Main.groovy
├── entities/
│   ├── Pessoa.groovy / Candidato.groovy / Empresa.groovy
│   ├── Vaga.groovy / Competencia.groovy
│   ├── Curtida.groovy / Match.groovy
├── dao/
│   ├── ConexaoDB.groovy / IConexaoFactory.groovy / ConexaoJDBCFactory.groovy
│   ├── CandidatoDao.groovy / EmpresaDao.groovy
│   ├── VagaDao.groovy / CompetenciaDao.groovy
├── services/
│   ├── CandidatoService.groovy / EmpresaService.groovy
│   ├── VagaService.groovy / CurtidaService.groovy
├── repositories/
│   └── MatchRepositoryParaMock.groovy
├── controllers/
│   ├── CandidatoController.groovy
│   ├── EmpresaController.groovy
│   └── VagaController.groovy
└── view/
    ├── MenuCli.groovy
    └── *Cli.groovy

src/test/groovy/
├── model/        # CandidatoTest, EmpresaTest, MatchTest
├── repository/   # RepositoryTest
└── services/     # PessoaServices, SistemaCurtidas, VagaServices (unit + behavior)
```

---

## Frontend

### Stack

- TypeScript `5`
- Vite `5`
- Chart.js `4`
- Font Awesome

### Pré-requisitos

- Node.js + pnpm

### Executar

```bash
cd frontend-linketinder
pnpm install
pnpm dev        # http://localhost:3000
```

### Build de produção

```bash
pnpm build
```

### Estrutura

```
src/
├── main.ts                  # Ponto de entrada — eventos e navegação
├── models/                  # Candidato.ts / Empresa.ts / Vaga.ts
├── repository/
│   └── StorageService.ts    # Persistência via LocalStorage
├── services/
│   └── UIService.ts         # Renderização de DOM e validações regex
└── assets/
    └── global.css
```

---

## Limitações atuais

- Frontend sem integração com a API (dados independentes no LocalStorage)
- Curtidas e matches não persistem no banco — ficam em memória durante a execução do CLI
- Sem autenticação ou controle de perfis
- Sem validação formal de CPF/CNPJ/CEP no CLI (leitura de texto livre)

---

## Documentação técnica

- [Arquitetura e fluxos do backend](./backend-linketinder/docs/PROJETO.md)
- [API REST — endpoints e payloads](./backend-linketinder/docs/API_REST.md)
- [Schema SQL (DDL)](./backend-linketinder/docs/db_linketinder_ddl.sql)
- [Dados de exemplo (DML)](./backend-linketinder/docs/db_linketinder_dml.sql)
- [Consultas de validação](./backend-linketinder/docs/db_linketinder_dcl.sql)
- [README do frontend](./frontend-linketinder/README.md)

---

## Licença

Este projeto está sob a licença de `Gustavo Cardoso` e `Acelera ZG`.

## Autor

Desenvolvido por [ghustdev](https://github.com/ghustdev)
