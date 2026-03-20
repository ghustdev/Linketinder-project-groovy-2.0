# Linketinder MVP - Frontend

Sistema híbrido entre Tinder e LinkedIn para conectar candidatos e empresas.

## 🚀 Como Executar

### 1. Instalar dependências
```bash
pnpm install
```

### 2. Rodar o projeto
```bash
pnpm dev
```

O projeto abrirá automaticamente em `http://localhost:3000`

## 📁 Estrutura do Projeto

```
frontend-linketinder/
├── index.html              # Página principal
├── src/
│   ├── main.ts            # Arquivo principal TypeScript
│   ├── models/            # Classes de dados
│   │   ├── Candidato.ts
│   │   ├── Empresa.ts
│   │   └── Vaga.ts
│   ├── services/          # Lógica de negócio
│   │   ├── StorageService.ts  # Gerencia LocalStorage
│   │   └── UIService.ts       # Manipula DOM
│   └── assets/
│       └── global.css     # Estilos globais
```

## 🎨 Funcionalidades

### Aba Vagas (Visão Candidato)
- Feed de cards com vagas disponíveis
- Exibe: Título, Requisitos, País e Estado (empresa anônima)

### Aba Candidatos (Visão Empresa)
- Feed de cards com candidatos
- Exibe: Competências, Descrição, Formação e Estado (candidato anônimo)
- Botão para visualizar gráfico de competências

### Aba Cadastro
- Formulários para Candidato, Empresa e Vaga
- Listagem e exclusão de cadastros

## 🎨 Paleta de Cores

- Preto: `#000000`
- Laranja: `#FF7158`
- Magenta: `#FD2B7B`
- Cinza: `#424242`

## 💾 Armazenamento

Todos os dados são salvos no **LocalStorage** do navegador.
