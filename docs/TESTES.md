# Documentação de Testes

## 1. Objetivo

Descrever como os testes do projeto estão organizados, como executá-los e qual comportamento eles cobrem atualmente.

## 2. Stack de testes

- **Spock Framework** (principal)
- **JUnit Platform** (execução)
- **Gradle Test Task** (`./gradlew test`)

Dependências estão declaradas em `build.gradle`.

## 3. Estrutura atual

Arquivo de teste existente:

- `src/test/groovy/services/PessoaServicesTest.groovy`

Classe testada:

- `services.PessoaServices`

## 4. Cenários cobertos

## 4.1 Criação de candidato

Caso de teste:

- `deve criar um candidato e adicioná-lo ao repositório`

Verificações:

- `createCandidato(...)` adiciona item na lista `arrayCandidatos`
- CPF do candidato adicionado confere com o valor de entrada

## 4.2 Criação de empresa

Caso de teste:

- `deve criar uma empresa e adicioná-la ao repositório`

Verificações:

- `createEmpresa(...)` adiciona item na lista `arrayEmpresas`
- CNPJ da empresa adicionada confere com o valor de entrada

## 5. Como executar

Na raiz do projeto:

```bash
./gradlew test
```

## 6. Saídas e relatórios

Após execução bem-sucedida:

- Resultado XML: `build/test-results/test/`
- Relatório HTML: `build/reports/tests/test/index.html`

## 7. Estado atual da suíte

Execução mais recente nesta atualização:

- Comando: `./gradlew test`
- Resultado: **BUILD SUCCESSFUL**

## 8. Lacunas de cobertura

Atualmente não há testes para:

- `view.Interface` (fluxo de entrada/saída no terminal)
- `repository.Repository` (carga inicial)
- validações de dados de entrada
- cenários de erro com entradas inválidas