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
- As habilidades agora são objetos (`Competencia`) em candidatos e vagas.

## Refatoração
Backend e DB:
- [x] Nomes autoexplicativos: alterei os nomes das variáveis, parâmetros, classes e métodos para inglês e agora faz mais sentido, deixei padronizado.
- [x] Regra do escoteiro: limpei o código e deixei a lógica muito mais limpa entre as partes, ou seja, basicamente refatorei, tirando comentários, tratamento de exceções e testes
- [x] Crie funções pequenas: reformulei e modularizei as funções para deixar mais divididas, estavam muito grandes.
- [x] Don't Repeat Yourself (DRY):
- [x] Somente comente se for estritamente necessário: retirei todos os comentários, para isso, tive que deixar o código e os métodos muito mais claros e simples de entender.
- [x] Tratamento de erros: Já havia tratamento de exceções, porém melhorei ele aplicando modularidade no package "exception"
- [x] Testes: Já havia alguns testes, porém adicionai mais alguns testes, principalmente para o banco de dados

Frontend:
- [x] Até esse momento fiz apenas a refatoração do Backend, mas irei daqui em diante rushar em todos Desafios do Acelera, e consequentemente refatorar todos os códigos, incluindo os Frontends