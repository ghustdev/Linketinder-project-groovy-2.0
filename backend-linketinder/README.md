# Linketinder Backend (Groovy + PostgreSQL)

Aplicação CLI para recrutamento, com persistência em PostgreSQL via JDBC.

## Requisitos

- Java 21
- PostgreSQL

## Banco de dados

O schema está em:

- `docs/db_linketinder.sql`

Crie o banco e rode o script antes de executar a aplicação.

## Configuração

Defina as variáveis de ambiente de conexão:

- `DB_URL` (ex.: `jdbc:postgresql://localhost:5432/linketinder`)
- `DB_USER`
- `DB_PASSWORD`

Se não forem definidas, os padrões são:

- `jdbc:postgresql://localhost:5432/linketinder`
- usuário `postgres`
- senha `postgres`

## Execução

```bash
groovy src/main/groovy/Main.groovy
```

## Menu principal

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

## Observações

- Curtidas e Matches seguem a lógica atual em memória.
- Testes antigos ainda podem falhar por dependerem do modelo anterior em memória.
