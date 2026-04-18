package model

import spock.lang.Specification

class CandidatoTest extends Specification {

    def "deve curtir vaga e registrar curtida no candidato"() {
        given:
        def empresa = Empresa.builder().name("Tech").cnpj("11").build()
        def vaga = Vaga.builder().id(1).name("Dev").enterprise(empresa).build()
        def candidato = Candidato.builder().name("Ana").cpf("123").build()

        when:
        def curtida = candidato.curtirVaga(vaga)

        then:
        curtida != null
        curtida.candidate == candidato
        curtida.vacancy == vaga
        curtida.enterprise == empresa
        candidato.likeVacancies.size() == 1
    }

    def "deve impedir curtida duplicada para mesma vaga"() {
        given:
        def empresa = Empresa.builder().name("Tech").cnpj("11").build()
        def vaga = Vaga.builder().id(1).name("Dev").enterprise(empresa).build()
        def candidato = Candidato.builder().name("Ana").cpf("123").build()
        candidato.curtirVaga(vaga)

        when:
        candidato.curtirVaga(vaga)

        then:
        thrown(IllegalStateException)
    }

    def "deve inicializar lista de curtidas quando estiver nula"() {
        given:
        def empresa = Empresa.builder().name("Tech").cnpj("11").build()
        def vaga = Vaga.builder().id(1).name("Dev").enterprise(empresa).build()
        def candidato = Candidato.builder().name("Ana").cpf("123").build()
        candidato.likeVacancies = null

        when:
        candidato.curtirVaga(vaga)

        then:
        candidato.likeVacancies != null
        candidato.likeVacancies.size() == 1
    }
}
