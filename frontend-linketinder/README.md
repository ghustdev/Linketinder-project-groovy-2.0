# Linketinder — Frontend

Sistema híbrido entre Tinder e LinkedIn para conectar candidatos e empresas. O candidato visualiza vagas anonimamente e a empresa visualiza candidatos anonimamente.

## Como Executar

**Instalar dependências**
```bash
pnpm install
```

**Rodar em desenvolvimento**
```bash
pnpm dev
```
Abre automaticamente em `http://localhost:3000`

**Build de produção**
```bash
pnpm build
```

## Estrutura do Projeto

```
frontend-linketinder/
├── index.html                  # Página principal (SPA)
├── vite.config.ts              # Configuração do Vite
├── tsconfig.json               # Configuração do TypeScript
└── src/
    ├── main.ts                 # Ponto de entrada — eventos e navegação
    ├── models/
    │   ├── Candidato.ts        # Modelo de dados do candidato
    │   ├── Empresa.ts          # Modelo de dados da empresa
    │   └── Vaga.ts             # Modelo de dados da vaga
    ├── repository/
    │   └── StorageService.ts   # Persistência via LocalStorage
    ├── services/
    │   └── UIService.ts        # Renderização de DOM e validações regex
    └── assets/
        ├── global.css          # Estilos globais
        └── favicon.ico         # Ícone da aplicação
```

## Funcionalidades

### Aba Vagas — visão do candidato
- Feed de cards com vagas disponíveis
- Exibe título, competências exigidas, país e estado
- Empresa permanece anônima

### Aba Candidatos — visão da empresa
- Feed de cards com candidatos disponíveis
- Exibe competências, descrição, formação e estado
- Candidato permanece anônimo
- Botão para abrir gráfico de barras com frequência de competências

### Aba Cadastro
- Formulário de **Candidato**: nome, e-mail, CPF, idade, estado, CEP, descrição, formação, competências e LinkedIn
- Formulário de **Empresa**: nome, e-mail, CNPJ, país, estado, CEP e descrição
- Formulário de **Vaga**: título, descrição, CNPJ da empresa e competências exigidas
- Listagem e exclusão de candidatos, empresas e vagas
- Ao excluir uma empresa, todas as suas vagas são removidas automaticamente

## Validações

Todos os formulários validam os campos antes de salvar, usando expressões regulares definidas em `UIService.regex`:

| Campo       | Formato esperado              |
|-------------|-------------------------------|
| Nome        | Apenas letras e espaços       |
| E-mail      | formato@dominio.com           |
| CPF         | `000.000.000-00`              |
| CNPJ        | `00.000.000/0000-00`          |
| CEP         | `00000-000`                   |
| LinkedIn    | `linkedin.com/in/perfil`      |
| Competência | Letras, números e `# + . -`   |

Uma vaga só pode ser cadastrada se o CNPJ informado corresponder a uma empresa já cadastrada.

## Armazenamento

Todos os dados são persistidos no **LocalStorage** do navegador sob as chaves `candidatos`, `empresas` e `vagas`. Não há backend — a aplicação funciona completamente offline.

## Paleta de Cores

| Nome    | Hex       |
|---------|-----------|
| Preto   | `#000000` |
| Laranja | `#FF7158` |
| Magenta | `#FD2B7B` |
| Cinza   | `#424242` |

## Tecnologias

- [TypeScript](https://www.typescriptlang.org/)
- [Vite](https://vitejs.dev/)
- [Chart.js](https://www.chartjs.org/) — gráfico de competências
- [Font Awesome](https://fontawesome.com/) — ícones
