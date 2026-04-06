package services

import repository.Repository
import spock.lang.Specification

class PessoaServicesBehaviorTest extends Specification {

    def "deve persistir candidato com todos os campos no repositorio real"() {
        given:
        def repository = new Repository()
        def service = new PessoaServices(repository)
        def totalAntes = repository.arrayCandidatos.size()

        when:
        service.createCandidato("Novo", "novo@mail.com", "999", 20, "SP", "01000", "Desc", ["Groovy"])

        then:
        repository.arrayCandidatos.size() == totalAntes + 1
        repository.arrayCandidatos.last().with {
            nome == "Novo"
            email == "novo@mail.com"
            cpf == "999"
            old == 20
            state == "SP"
            cep == "01000"
            descricao == "Desc"
            skills == ["Groovy"]
        }
    }

    def "deve persistir empresa com todos os campos no repositorio real"() {
        given:
        def repository = new Repository()
        def service = new PessoaServices(repository)
        def totalAntes = repository.arrayEmpresas.size()

        when:
        service.createEmpresa("Empresa X", "x@mail.com", "0001", "Brasil", "GO", "74000", "Desc", ["Java"])

        then:
        repository.arrayEmpresas.size() == totalAntes + 1
        repository.arrayEmpresas.last().with {
            nome == "Empresa X"
            email == "x@mail.com"
            cnpj == "0001"
            country == "Brasil"
            state == "GO"
            cep == "74000"
            descricao == "Desc"
            skills == ["Java"]
        }
    }

    def "listagem de candidatos e empresas deve executar sem excecao"() {
        given:
        def repository = new Repository()
        def service = new PessoaServices(repository)

        when:
        service.listCandidatos()
        service.listEmpresas()

        then:
        noExceptionThrown()
    }
}
