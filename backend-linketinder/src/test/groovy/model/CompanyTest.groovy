package model

import exceptions.DuplicateResourceException
import exceptions.ValidationException
import spock.lang.Specification

class CompanyTest extends Specification {

    def "deve lançar erro quando candidato ou vaga forem nulos"() {
        given:
        def company = Company.builder().name("Tech").cnpj("11").build()

        when:
        company.likeCandidate(null, null, [])

        then:
        thrown(ValidationException)
    }

    def "deve lançar erro quando vaga não pertence à empresa"() {
        given:
        def companyA = Company.builder().name("Tech A").cnpj("11").build()
        def companyB = Company.builder().name("Tech B").cnpj("22").build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()
        def vacancy = Vacancy.builder().id(1).name("Dev").company(companyB).build()

        when:
        companyA.likeCandidate(candidate, vacancy, [])

        then:
        thrown(ValidationException)
    }

    def "deve retornar null quando empresa curte candidato sem curtida prévia do candidato"() {
        given:
        def company = Company.builder().name("Tech").cnpj("11").build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()
        def vacancy = Vacancy.builder().id(1).name("Dev").company(company).build()

        when:
        def match = company.likeCandidate(candidate, vacancy, [])

        then:
        match == null
        company.likedCandidates.size() == 1
    }

    def "deve criar match quando candidato já curtiu a vaga da empresa"() {
        given:
        def company = Company.builder().name("Tech").cnpj("11").build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()
        def vacancy = Vacancy.builder().id(1).name("Dev").company(company).build()
        def candidateLikes = [new Like(candidate: candidate, vacancy: vacancy, company: company)]

        when:
        def match = company.likeCandidate(candidate, vacancy, candidateLikes)

        then:
        match != null
        match.candidate == candidate
        match.company == company
        match.vacancy == vacancy
    }

    def "deve impedir curtida duplicada da empresa para mesmo candidato e vaga"() {
        given:
        def company = Company.builder().name("Tech").cnpj("11").build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()
        def vacancy = Vacancy.builder().id(1).name("Dev").company(company).build()
        def candidateLikes = [new Like(candidate: candidate, vacancy: vacancy, company: company)]
        company.likeCandidate(candidate, vacancy, candidateLikes)

        when:
        company.likeCandidate(candidate, vacancy, candidateLikes)

        then:
        thrown(DuplicateResourceException)
    }
}
