# Linketinder Backend (Groovy + PostgreSQL)

Aplicação CLI para recrutamento, com persistência em PostgreSQL via JDBC.

## Requisitos

- Java 21
- PostgreSQL

## Banco de dados

Scripts do banco estão em:

- `docs/db_linketinder_ddl.sql` (tabelas)
- `docs/db_linketinder_dml.sql` (massa de dados/exemplos)
- `docs/db_linketinder_dcl.sql` (consultas úteis)

Crie o banco e execute primeiro o `ddl` (e opcionalmente o `dml`) antes de rodar a aplicação.

## Configuração

O projeto lê as configurações de conexão a partir do arquivo `linketinder.properties` na raiz do projeto:

- `DB_URL` (ex.: `jdbc:postgresql://localhost:5432/db_linketinder`)
- `DB_USER`
- `DB_PASSWORD`

Exemplo:

```properties
DB_URL=jdbc:postgresql://localhost:5432/db_linketinder
DB_USER=postgres
DB_PASSWORD=postgres
```

## Execução

```bash
groovy src/main/groovy/Main.groovy
```
```bash
./gradlew run
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

- Curtidas de candidatos e matches ficam em memória durante a execução (via `MatchRepositoryParaMock`), não persistem no PostgreSQL.
- Competências são objetos (`Competencia`) em candidatos e vagas.

## Conexão com banco (Factory Method)

A conexão com o PostgreSQL é centralizada em `dao.ConexaoDB`:

- Lê `linketinder.properties` uma única vez (cache em memória).
- Mantém uma única `java.sql.Connection` reutilizada durante a execução do CLI.
- O `Main.groovy` garante o fechamento no `finally` chamando `ConexaoDB.fecharConexao()`.

O ponto de criação da conexão usa **Factory Method** via a interface `dao.IConexaoFactory`:

- Implementação padrão: `dao.ConexaoJDBCFactory` (usa `DriverManager.getConnection`).
- Para trocar a forma de conexão (ex.: outro driver, DataSource/pool, etc.), implemente `IConexaoFactory` e chame `ConexaoDB.definirFabrica(...)` no início do `Main` (antes de qualquer DAO acessar o banco).

Exemplo (ideia):

```groovy
class MinhaFactory implements IConexaoFactory {
  @Override
  Connection criarConexao(String url, String usuario, String senha) {
    // criar e retornar Connection por outro mecanismo
  }
}

ConexaoDB.definirFabrica(new MinhaFactory())
```

## Simplificações recentes

Mudanças feitas para reduzir estado duplicado e remover pontos frágeis no fluxo:

- Curtidas do candidato e da empresa deixaram de ser armazenadas dentro de `Candidato`/`Empresa`; a fonte de verdade em runtime é o repositório de match em memória (`repositories.IMatchRepository`).
- Fluxo de “candidato curtir vaga” e “visualizar curtidas” passou a funcionar mesmo quando o candidato é recarregado do banco (evita depender de um objeto específico em memória).
- Remoção de normalização automática de CPF/CNPJ no backend (sem `DocumentoUtils`); as buscas agora delegam diretamente para o DAO.
- Ajustes no input do CLI: `EntradaConsolePadrao.pausar()` não imprime texto extra.
- Testes Spock refatorados para uma sintaxe mais direta (stubs e expectativas no `then`, sem encadeamentos confusos).

## Clean Code e SOLID (avaliação)

O que já está bem encaminhado:

- Separação por camadas (`view` para CLI/IO, `services` para regras e orquestração, `dao` para JDBC, `entities` para domínio).
- Repositórios como interfaces em `repositories/*`, facilitando mock em testes.
- Exceções de domínio separadas em `exceptions/*`.

O que ainda falta para ficar mais “SOLID” e sustentável:


- Persistência de curtidas/matches: hoje o banco persiste candidatos/empresas/vagas/competências, mas curtidas e matches são apenas em memória (e se perdem ao reiniciar).
- SRP/Clareza no CLI: algumas rotas do CLI fazem múltiplas consultas repetidas e misturam listagem com validação de vazio; dá para simplificar e evitar chamadas duplicadas.
- Conexão com DB: hoje já está centralizada em `dao.ConexaoDB` e falha com exceção quando a configuração está inválida; em cenários reais, o próximo passo seria trocar para pool/DataSource e melhorar observabilidade (logs/métricas).

Importância dessas simplificações no desenvolvimento:

- Menos estado duplicado reduz bugs “fantasmas” (ex.: objetos recarregados do DB que não carregam listas em memória).
- Fonte de verdade única facilita debug e manutenção: o comportamento fica previsível.
- Testes mais legíveis reduzem tempo de onboarding e ajudam a evoluir o código sem regressões.
- Código mais coeso e com dependências mais abstratas (interfaces) facilita trocar implementações (ex.: mock em teste, repo real no futuro) sem reescrever regra de negócio.
