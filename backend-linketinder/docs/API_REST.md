# 🚀 Linketinder API - Documentação e Guia de Implementação

Bem-vindo ao **Linketinder**, um projeto desenvolvido para o desafio ZG-HERO. Esta API foi construída utilizando **Groovy** e **Servlets puros** (`javax.servlet` / Java EE), sem o uso de frameworks como Spring ou Micronaut, focando no entendimento profundo de como a Web funciona nos bastidores do ecossistema Java.

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Groovy 4.0
- **Servidor:** Apache Tomcat 9.0 (via Plugin Gretty 3.1.8)
- **Persistência:** PostgreSQL & JDBC puro
- **Gerenciador de Dependências:** Gradle
- **Serialização JSON:** Google Gson

---

## 🏗️ Estratégia de Arquitetura

Como a restrição do projeto era **não utilizar frameworks**, o maior desafio arquitetural foi gerenciar a Injeção de Dependências e a Inicialização do Banco de Dados no contexto de um servidor Web.

### 1. O "Cérebro" da Aplicação (`WebListener`)

Em aplicações Web com Servlets, a classe `Main` tradicional (via terminal) é ignorada pelo Tomcat. Para resolver isso, implementamos um `ServletContextListener` (`DependencyInjectionListener`). Ele atua no exato momento em que o servidor sobe, realizando as seguintes tarefas:

- Inicia a fábrica de conexões com o banco de dados.
- Instancia todos os DAOs e Services manualmente (Injeção de Dependência Manual).
- Armazena essas instâncias em um contexto global (`ServletContext`), funcionando como um "container" provisório para que os Controllers possam acessar os serviços de forma limpa.

### 2. Controllers (Servlets)

Cada domínio da aplicação (Candidato, Empresa, Vaga) possui seu próprio Servlet mapeado via anotação `@WebServlet`. O papel do Controller foi estritamente reduzido a:

- Receber a requisição HTTP.
- Desserializar o corpo (JSON) usando `Gson`.
- Delegar a regra de negócio para a camada de Serviço (`Service`).
- Retornar o Status HTTP apropriado (200, 201, 400, 500).

### 3. Persistência (DAO + JDBC)

Os endpoints não acessam o banco diretamente. O fluxo foi mantido em camadas:

- `Controller (Servlet)` -> desserializa JSON e valida entrada
- `Service` -> regras e orquestração
- `DAO` -> JDBC puro (INSERT/SELECT) e conversões necessárias (ex.: datas)

---

## 🚦 Endpoints da API

Base URL (padrão Gretty/Tomcat local):

- `http://localhost:8080`

### Padrão de Request/Response

Headers recomendados (Postman):

- `Content-Type: application/json`
- `Accept: application/json`

Status codes usados na implementacao atual:

- `201 Created` em cadastros (POST) quando a operacao conclui
- `200 OK` em consultas (GET) quando a operacao conclui
- `400 Bad Request` em POST quando ocorre erro de validacao/parse/regra capturada como excecao
- `500 Internal Server Error` em GET quando ocorre erro interno no servlet/service/dao

### 👤 Candidatos (`/candidatos`)

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| **POST** | `/candidatos` | Cadastra um novo candidato no banco de dados. |
| **GET** | `/candidatos` | Retorna uma lista com todos os candidatos cadastrados. |
| **GET** | `/candidatos?cpf=123` | Busca e retorna um candidato específico através do Query Param `cpf`. |

**Resposta de Sucesso (POST):**

`201 Created`

```json
{ "mensagem": "Candidato cadastrado!" }
```

**Resposta de Erro (POST):**

`400 Bad Request`

```json
{ "erro": "Erro ao criar candidato - <detalhe>" }
```

**Exemplo de Payload (POST):**

> Nota: As competências devem ser enviadas como uma lista de objetos.

```json
{
  "nome": "Murilo",
  "sobrenome": "Cardoso",
  "email": "teste@email.com",
  "cpf": "111.444.555-33",
  "nascimento": "2005-07-05",
  "pais": "BR",
  "cep": "74000-000",
  "descricao": "Dev Junior em transição de carreira",
  "formacao": "UFG",
  "linkedin": "https://linkedin.com/in/murilo",
  "competencias": [
    { "nome": "Java" },
    { "nome": "Groovy" },
    { "nome": "PostgreSQL" }
  ]
}
```

### 🏢 Empresas (`/empresas`)

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| **POST** | `/empresas` | Cadastra uma nova empresa. |
| **GET** | `/empresas` | Lista todas as empresas. |
| **GET** | `/empresas?cnpj=12.345.678/0001-90` | Busca e retorna uma empresa específica através do Query Param `cnpj`. |

**Resposta de Sucesso (POST):**

`201 Created`

```json
{ "mensagem": "Empresa cadastrada!" }
```

**Resposta de Erro (POST):**

`400 Bad Request`

```json
{ "erro": "Erro ao criar empresa - <detalhe>" }
```

**Exemplo de Payload (POST):**

```json
{
  "nome": "Acme Tecnologia LTDA",
  "email": "contato@acme.com.br",
  "cnpj": "12.345.678/0001-90",
  "pais": "BR",
  "cep": "01001-000",
  "descricao": "Empresa de tecnologia focada em backend."
}
```

### 💼 Vagas (`/vagas`)

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| **POST** | `/vagas` | Cadastra uma nova vaga associada a uma empresa. |
| **GET** | `/vagas` | Lista todas as vagas disponíveis. |
| **GET** | `/vagas?id=1` | Busca e retorna uma vaga específica através do Query Param `id`. |

**Resposta de Sucesso (POST):**

`201 Created`

```json
{ "mensagem": "Vaga cadastrada!" }
```

**Resposta de Erro (POST):**

`400 Bad Request`

```json
{ "erro": "Erro ao criar vaga - <detalhe>" }
```

**Exemplo de Payload (POST):**

```json
{
  "empresaId": 1,
  "titulo": "Desenvolvedor(a) Java Backend",
  "descricao": "Atuar com Java 17, Servlets e PostgreSQL.",
  "estado": "SP",
  "cidade": "Sao Paulo",
  "competenciasRequeridas": [
    { "nome": "Java" },
    { "nome": "SQL" }
  ]
}
```

---

## 🧠 Desafios e Resoluções (Troubleshooting)

Durante o desenvolvimento "from scratch" sem o Spring, vários desafios de infraestrutura surgiram. Abaixo estão os principais aprendizados documentados:

### 1. Incompatibilidade Gretty vs Gradle (O "Cobertor Curto")

**O Problema:** O Gretty 3.1.0 lançava o erro `Unable to load class 'org.gradle.util.VersionNumber'` no Gradle 8+. Ao atualizar para o Gretty 4.x, perdíamos o suporte ao Tomcat 9 e à biblioteca legada `javax.servlet`.

**A Solução:** Fixamos a versão exata `3.1.8` do Gretty no `build.gradle`. Esta versão atua como um "patch", resolvendo os conflitos com versões modernas do Gradle, mas mantendo a compatibilidade do Tomcat 9.

### 2. O "ClassLoader Hell" (Ocultação do Driver JDBC)

**O Problema:** O Tomcat possui um isolamento severo de classloaders. Ao usar o clássico `DriverManager.getConnection()`, o Tomcat lançava a exceção `No suitable driver found for jdbc:postgresql...`, ignorando a dependência do Gradle.

**A Solução:** Foi necessário aplicar um bypass instanciando o driver nativo diretamente no código da nossa fábrica de conexão:

```groovy
import org.postgresql.Driver

Properties props = new Properties()
props.setProperty("user", "postgres")
props.setProperty("password", "senha")

// Instanciação direta blinda o código contra a ocultação do Tomcat
Driver driverPostgres = new Driver()
return driverPostgres.connect(url, props)
```

### 3. A Rigidez do JDBC com Tipos de Data

**O Problema:** O Groovy e o Gson lidam com `java.util.Date`, mas o PostgreSQL exige obrigatoriamente objetos `java.sql.Date` para persistência (`Cannot cast object '...' with class 'java.util.Date' to class 'java.sql.Date'`).

**A Solução:** Extraímos os milissegundos da data original (`getTime()`) e instanciamos a data do SQL diretamente na chamada do `PreparedStatement` dentro do DAO:

```groovy
stmt.setDate(3, new java.sql.Date(candidato.nascimento.getTime()))
```

### 4. Desserialização Automática de Datas (Gson)

**O Problema:** JSONs com datas em formato String (`"2005-07-05"`) quebravam o parseamento padrão do Gson.

**A Solução:** Em vez de usar lógicas condicionais manuais (if/else), configuramos um `GsonBuilder` global nos Controllers para reconhecer o padrão de entrada:

```groovy
private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create()
```

---

## 🚀 Como rodar o projeto localmente

1. Garanta que o Java (JDK 17 ou 21) e o PostgreSQL estão devidamente instalados.
2. Clone este repositório.
3. Configure suas credenciais locais de banco de dados no arquivo `linketinder.properties` na raiz do projeto:

```properties
DB_URL=jdbc:postgresql://localhost:5432/db_linketinder
DB_USER=postgres
DB_PASSWORD=postgres
```

4. Abra o terminal na raiz do projeto e execute:

```bash
./gradlew appRun
```

5. Aguarde a mensagem de sucesso no terminal. A API estará disponível em:

`http://localhost:8080/`
