package model

import spock.lang.Specification

class MatchTest extends Specification {

    def "deve retornar false em isMatch quando dados obrigatórios estiverem nulos"() {
        expect:
        !new Match().isMatch([], [])
    }

    def "deve retornar true quando curtidas coincidem para candidato empresa e vaga"() {
        given:
        def empresa = Empresa.builder().name("Tech").cnpj("11").build()
        def candidato = Candidato.builder().name("Ana").cpf("123").build()
        def vaga = Vaga.builder().id(7).title("Dev").empresa(empresa).build()
        def match = new Match(candidato: candidato, empresa: empresa, vaga: vaga)
        def curtida = new Curtida(candidato: candidato, empresa: empresa, vaga: vaga)

        when:
        def isMatch = match.isMatch([curtida], [curtida])

        then:
        isMatch
    }

    def "buildKey deve gerar chave padronizada"() {
        expect:
        Match.buildKey("123", "11", 9) == "123|11|9"
    }
}
