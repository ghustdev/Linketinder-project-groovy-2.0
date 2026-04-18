package model

import spock.lang.Specification

class MatchTest extends Specification {

    def "deve retornar false em isMatch quando dados obrigatórios estiverem nulos"() {
        expect:
        !new Match().isMatch([], [])
    }

    def "deve retornar true quando curtidas coincidem para candidato empresa e vaga"() {
        given:
        def company = Company.builder().name("Tech").cnpj("11").build()
        def candidate = Candidate.builder().name("Ana").cpf("123").build()
        def vacancy = Vacancy.builder().id(7).name("Dev").company(company).build()
        def match = new Match(candidate: candidate, company: company, vacancy: vacancy)
        def like = new Like(candidate: candidate, company: company, vacancy: vacancy)

        when:
        def isMatch = match.isMatch([like], [like])

        then:
        isMatch
    }

    def "buildKey deve gerar chave padronizada"() {
        expect:
        Match.buildKey("123", "11", 9) == "123|11|9"
    }
}
