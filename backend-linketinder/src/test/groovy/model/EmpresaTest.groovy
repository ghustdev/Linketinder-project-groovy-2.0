package model

import spock.lang.Specification

class EmpresaTest extends Specification {

    def "deve lançar erro quando candidato ou vaga forem nulos"() {
        given:
        def empresa = Empresa.builder().nome("Tech").cnpj("11").build()

        when:
        empresa.curtirCandidato(null, null, [])

        then:
        thrown(IllegalArgumentException)
    }

    def "deve lançar erro quando vaga não pertence à empresa"() {
        given:
        def empresaA = Empresa.builder().nome("Tech A").cnpj("11").build()
        def empresaB = Empresa.builder().nome("Tech B").cnpj("22").build()
        def candidato = Candidato.builder().nome("Ana").cpf("123").build()
        def vaga = Vaga.builder().id(1).nome("Dev").empresa(empresaB).build()

        when:
        empresaA.curtirCandidato(candidato, vaga, [])

        then:
        thrown(IllegalArgumentException)
    }

    def "deve retornar null quando empresa curte candidato sem curtida prévia do candidato"() {
        given:
        def empresa = Empresa.builder().nome("Tech").cnpj("11").build()
        def candidato = Candidato.builder().nome("Ana").cpf("123").build()
        def vaga = Vaga.builder().id(1).nome("Dev").empresa(empresa).build()

        when:
        def match = empresa.curtirCandidato(candidato, vaga, [])

        then:
        match == null
        empresa.candidatosCurtidos.size() == 1
    }

    def "deve criar match quando candidato já curtiu a vaga da empresa"() {
        given:
        def empresa = Empresa.builder().nome("Tech").cnpj("11").build()
        def candidato = Candidato.builder().nome("Ana").cpf("123").build()
        def vaga = Vaga.builder().id(1).nome("Dev").empresa(empresa).build()
        def curtidaDoCandidato = [new Curtida(candidato: candidato, vaga: vaga, empresa: empresa)]

        when:
        def match = empresa.curtirCandidato(candidato, vaga, curtidaDoCandidato)

        then:
        match != null
        match.candidato == candidato
        match.empresa == empresa
        match.vaga == vaga
    }

    def "deve impedir curtida duplicada da empresa para mesmo candidato e vaga"() {
        given:
        def empresa = Empresa.builder().nome("Tech").cnpj("11").build()
        def candidato = Candidato.builder().nome("Ana").cpf("123").build()
        def vaga = Vaga.builder().id(1).nome("Dev").empresa(empresa).build()
        def curtidaDoCandidato = [new Curtida(candidato: candidato, vaga: vaga, empresa: empresa)]
        empresa.curtirCandidato(candidato, vaga, curtidaDoCandidato)

        when:
        empresa.curtirCandidato(candidato, vaga, curtidaDoCandidato)

        then:
        thrown(IllegalStateException)
    }
}
