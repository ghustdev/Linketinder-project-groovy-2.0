package services

import exceptions.DuplicateResourceException
import model.Candidate
import model.Company
import model.Vacancy
import spock.lang.Specification

class LikeServicesBehaviorTest extends Specification {

    def "deve registrar curtida do candidato na lista global do sistema"() {
        given:
        def system = new LikeServices()
        def company = Company.builder().name("Tech").cnpj("11").build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()
        def vacancy = Vacancy.builder().id(1L).name("Dev").company(company).build()

        when:
        def like = system.candidateLikesVacancy(candidate, vacancy)

        then:
        like != null
        system.allCandidateLikes.size() == 1
        system.allCandidateLikes.first().candidate.cpf == candidate.cpf
    }

    def "deve retornar null quando empresa curte candidato sem curtida reciproca e nao criar match"() {
        given:
        def system = new LikeServices()
        def company = Company.builder().name("Tech").cnpj("11").build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()
        def vacancy = Vacancy.builder().id(1L).name("Dev").company(company).build()

        when:
        def match = system.companyLikesCandidate(company, candidate, vacancy)

        then:
        match == null
        system.allMatches.empty
    }

    def "nao deve duplicar match quando metodo for chamado novamente para o mesmo conjunto"() {
        given:
        def system = new LikeServices()
        def company = Company.builder().name("Tech").cnpj("11").build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()
        def vacancy = Vacancy.builder().id(1L).name("Dev").company(company).build()
        system.candidateLikesVacancy(candidate, vacancy)
        system.companyLikesCandidate(company, candidate, vacancy)

        when:
        def secondAttempt = null
        try {
            secondAttempt = system.companyLikesCandidate(company, candidate, vacancy)
        } catch (DuplicateResourceException ignored) {
        }

        then:
        secondAttempt == null
        system.allMatches.size() == 1
    }

    def "listagem de curtidas do candidato deve executar sem excecao"() {
        given:
        def system = new LikeServices()
        def company = Company.builder().name("Tech").cnpj("11").build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()
        def vacancy = Vacancy.builder().id(1L).name("Dev").company(company).build()
        system.candidateLikesVacancy(candidate, vacancy)

        when:
        system.listCandidateLikes(candidate.likedVacancies)

        then:
        noExceptionThrown()
    }
}
