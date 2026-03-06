package services

import model.Empresa
import repository.Repository
import spock.lang.Specification

class VagaServicesTest extends Specification {

    def "deve buscar empresa por cnpj ignorando case"() {
        given:
        def repository = new Repository()
        def service = new VagaServices(repository, repository.arrayEmpresas.first())
        def cnpj = repository.arrayEmpresas.first().cnpj

        when:
        def empresa = service.searchEmpresa(cnpj.toLowerCase())

        then:
        empresa != null
        empresa.cnpj == cnpj
    }

    def "deve retornar null para empresa e candidato inexistentes"() {
        given:
        def repository = new Repository()
        def service = new VagaServices(repository, repository.arrayEmpresas.first())

        expect:
        service.searchEmpresa("nao-existe") == null
        service.searchCandidato("nao-existe") == null
    }

    def "deve buscar vaga por id existente e retornar null para id inexistente"() {
        given:
        def repository = new Repository()
        def service = new VagaServices(repository, repository.arrayEmpresas.first())

        expect:
        service.searchIdVaga(1) != null
        service.searchIdVaga(999) == null
    }

    def "deve criar vaga com proximo id sequencial"() {
        given:
        def repository = new Repository()
        def empresa = repository.arrayEmpresas.first()
        def service = new VagaServices(repository, empresa)
        def idAntes = repository.arrayVagas*.id.max()

        when:
        service.createVaga("Nova Vaga", "Desc", empresa, ["Groovy"])

        then:
        repository.arrayVagas.size() == 3
        repository.arrayVagas.last().id == idAntes + 1
        repository.arrayVagas.last().title == "Nova Vaga"
    }

    def "listagem de vagas deve executar sem lançar excecao"() {
        given:
        def repository = new Repository()
        def service = new VagaServices(repository, repository.arrayEmpresas.first())

        when:
        service.listVagas()

        then:
        noExceptionThrown()
    }
}
