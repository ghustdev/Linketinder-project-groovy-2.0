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
| [`backend-linketinder`](./backend-linketinder/) | Groovy + Gradle | CLI com fluxo completo de vagas, curtidas e match |
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
├── backend-linketinder/   # CLI Groovy — model / repository / services / view
└── frontend-linketinder/  # SPA TypeScript — models / repository / services
```

O backend segue arquitetura em camadas (`view → services → repository → model`) com armazenamento em memória e carga inicial de 7 empresas, 7 candidatos e 2 vagas.

O frontend persiste dados no LocalStorage do navegador e não depende de nenhum servidor.

---

## Backend

### Stack

- Groovy `4.0.15`
- Gradle Wrapper `9.2.0`
- Spock `2.3` (Groovy 4) + JUnit Platform

### Pré-requisitos

- JDK 17+

### Executar

```bash
cd backend-linketinder
groovy src/main/groovy/Main.groovy
```

### Testes

```bash
cd backend-linketinder
./gradlew test
```

Relatórios gerados em `build/reports/tests/test/index.html`.

### Menu CLI

| Opção | Ação |
|---|---|
| `1` | Adicionar empresa |
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
├── model/
│   ├── Pessoa.groovy / InterfacePessoa.groovy
│   ├── Candidato.groovy / Empresa.groovy
│   ├── Vaga.groovy / Curtida.groovy / Match.groovy
├── repository/
│   └── Repository.groovy
├── services/
│   ├── PessoaServices.groovy
│   ├── VagaServices.groovy
│   └── SistemaCurtidas.groovy
└── view/
    ├── Cli.groovy
    └── Cli*Action.groovy

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

## Licença

Este projeto está sob a licença de `Gustavo Cardoso` e `Acelera ZG`.

## 👨‍💻 Autor

Desenvolvido por [ghustdev](https://github.com/ghustdev)

---

⭐ Se este projeto foi útil para você, considere dar uma estrela!

---

## Limitações atuais

- Backend sem persistência em banco de dados (armazenamento em memória)
- Sem autenticação ou controle de perfis
- Sem API HTTP — comunicação apenas via CLI
- Frontend sem integração com backend (dados independentes no LocalStorage)

---

## Documentação técnica

- [Arquitetura e fluxos do backend](./backend-linketinder/docs/PROJETO.md)
- [README do frontend](./frontend-linketinder/README.md)
