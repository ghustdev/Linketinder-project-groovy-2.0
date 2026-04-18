package services

import exceptions.DuplicateResourceException
import model.Candidate
import model.Company
import model.Vacancy
import spock.lang.Specification

class LikeServicesTest extends Specification {

    def "deve criar match quando empresa curte candidato na mesma vaga curtida pelo candidato"() {
        given:
        def likeSystem = new LikeServices()
        def company = Company.builder().name("Tech").cnpj("11").build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()
        def vacancy = Vacancy.builder().id(1L).name("Dev").company(company).build()
        likeSystem.candidateLikesVacancy(candidate, vacancy)

        when:
        def match = likeSystem.companyLikesCandidate(company, candidate, vacancy)

        then:
        match != null
        match.isMatch(company.likedCandidates, likeSystem.allCandidateLikes)
        likeSystem.allMatches.size() == 1
    }

    def "nao deve permitir curtida duplicada da empresa para mesmo candidato e vaga"() {
        given:
        def likeSystem = new LikeServices()
        def company = Company.builder().name("Tech").cnpj("11").build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()
        def vacancy = Vacancy.builder().id(1L).name("Dev").company(company).build()
        likeSystem.candidateLikesVacancy(candidate, vacancy)
        likeSystem.companyLikesCandidate(company, candidate, vacancy)

        when:
        likeSystem.companyLikesCandidate(company, candidate, vacancy)

        then:
        thrown(DuplicateResourceException)
    }

    def "listagem de curtidas recebidas da empresa deve funcionar sem erro de metodo ausente"() {
        given:
        def likeSystem = new LikeServices()
        def company = Company.builder().name("Tech").cnpj("11").build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()
        def vacancy = Vacancy.builder().id(1L).name("Dev").company(company).build()
        likeSystem.candidateLikesVacancy(candidate, vacancy)
        def receivedLikes = likeSystem.allCandidateLikes.findAll { it.vacancy.company.cnpj == company.cnpj }

        when:
        likeSystem.listCompanyReceivedLikes(receivedLikes)

        then:
        noExceptionThrown()
    }
}
