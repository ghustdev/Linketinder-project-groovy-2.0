package model

import exceptions.DuplicateResourceException
import spock.lang.Specification

class CandidateTest extends Specification {

    def "deve curtir vaga e registrar curtida no candidato"() {
        given:
        def company = Company.builder().name("Tech").cnpj("11").build()
        def vacancy = Vacancy.builder().id(1).name("Dev").company(company).build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()

        when:
        def like = candidate.likeVacancy(vacancy)

        then:
        like != null
        like.candidate == candidate
        like.vacancy == vacancy
        like.company == company
        candidate.likedVacancies.size() == 1
    }

    def "deve impedir curtida duplicada para mesma vaga"() {
        given:
        def company = Company.builder().name("Tech").cnpj("11").build()
        def vacancy = Vacancy.builder().id(1).name("Dev").company(company).build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()
        candidate.likeVacancy(vacancy)

        when:
        candidate.likeVacancy(vacancy)

        then:
        thrown(DuplicateResourceException)
    }

    def "deve inicializar lista de curtidas quando estiver nula"() {
        given:
        def company = Company.builder().name("Tech").cnpj("11").build()
        def vacancy = Vacancy.builder().id(1).name("Dev").company(company).build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()
        candidate.likedVacancies = null

        when:
        candidate.likeVacancy(vacancy)

        then:
        candidate.likedVacancies != null
        candidate.likedVacancies.size() == 1
    }
}
