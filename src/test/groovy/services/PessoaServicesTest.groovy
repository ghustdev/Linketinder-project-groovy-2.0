package services

import spock.lang.Specification
import spock.lang.Subject
import repository.Repository
import services.PessoaServices

class PessoaServicesTest extends Specification {
    def repositoryMock = Mock(Repository)

    @Subject
    def service = new PessoaServices(repositoryMock)

    def "deve criar um candidato e adicioná-lo ao repositório"() {
        given: "os dados de um candidato"
        def name = "Gustavo"
        def email = "gustavo@email.com"
        def cpf = "123"
        def old = 35
        def state = "GO"
        def cep = "74000"
        def description = "Dev"
        def skills = ["Java", "Groovy"]

        repositoryMock.arrayCandidatos >> []

        when: "chama método de criação do candidato"
        service.createCandidato(name, email, cpf, old, state, cep, description, skills)

        then: "o candidato deve ter sido adicionado à lista do repositório"
        repositoryMock.arrayCandidatos.size() == 1
        repositoryMock.arrayCandidatos[0].cpf == cpf
    }

    def "deve criar uma empresa e adicioná-la ao repositório"() {
        given: "os dados da empresa"
        def name = "ZG"
        def email = "zg@email.com"
        def cnpj = "123/0001"
        def country = "BR"
        def state = "GO"
        def cep = "74000"
        def description = "Saúde"
        def skills = ["Java" , "Groovy"]

        repositoryMock.arrayEmpresas >> []

        when: "chama método de criação da empresa"
        service.createEmpresa(name, email, cnpj, country, state, cep, description, skills)

        then: "a lista de empresas do repositório deve conter a nova empresa"
        repositoryMock.arrayEmpresas.size() == 1
        repositoryMock.arrayEmpresas[0].cnpj == cnpj
    }
}
