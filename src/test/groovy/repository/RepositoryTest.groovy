package repository

import spock.lang.Specification

class RepositoryTest extends Specification {

    def "deve carregar dados iniciais de candidatos empresas e vagas"() {
        when:
        def repository = new Repository()

        then:
        repository.arrayEmpresas.size() == 7
        repository.arrayCandidatos.size() == 7
        repository.arrayVagas.size() == 2
        repository.arrayVagas.every { it.empresa != null }
    }
}
