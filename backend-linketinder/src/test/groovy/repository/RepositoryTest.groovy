package repository

import spock.lang.Specification

class RepositoryTest extends Specification {

    def "deve inicializar colecoes vazias"() {
        when:
        def repository = new Repository()

        then:
        repository.companies != null
        repository.candidates != null
        repository.vacancies != null
        repository.allCandidateLikes != null
        repository.allMatches != null
    }
}
