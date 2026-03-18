package services

import repository.Repository
import spock.lang.Specification

class SistemaCurtidasBehaviorTest extends Specification {

    def "deve registrar curtida do candidato na lista global do sistema"() {
        given:
        def repository = new Repository()
        def sistema = new SistemaCurtidas()
        def candidato = repository.arrayCandidatos.first()
        def vaga = repository.arrayVagas.first()

        when:
        def curtida = sistema.candidatoCurteVaga(candidato, vaga)

        then:
        curtida != null
        sistema.allCurtidasCandidatos.size() == 1
        sistema.allCurtidasCandidatos.first().candidato.cpf == candidato.cpf
    }

    def "deve retornar null quando empresa curte candidato sem curtida reciproca e nao criar match"() {
        given:
        def repository = new Repository()
        def sistema = new SistemaCurtidas()
        def candidato = repository.arrayCandidatos.first()
        def vaga = repository.arrayVagas.first()
        def empresa = vaga.empresa

        when:
        def match = sistema.empresaCurteCandidato(empresa, candidato, vaga)

        then:
        match == null
        sistema.allMatches.empty
    }

    def "nao deve duplicar match quando metodo for chamado novamente para o mesmo conjunto"() {
        given:
        def repository = new Repository()
        def sistema = new SistemaCurtidas()
        def candidato = repository.arrayCandidatos.first()
        def vaga = repository.arrayVagas.first()
        def empresa = vaga.empresa
        sistema.candidatoCurteVaga(candidato, vaga)
        sistema.empresaCurteCandidato(empresa, candidato, vaga)

        when:
        def segundaTentativa = null
        try {
            segundaTentativa = sistema.empresaCurteCandidato(empresa, candidato, vaga)
        } catch (IllegalStateException ignored) {
            // comportamento esperado da regra de duplicidade da empresa
        }

        then:
        segundaTentativa == null
        sistema.allMatches.size() == 1
    }

    def "listagem de curtidas do candidato deve executar sem excecao"() {
        given:
        def repository = new Repository()
        def sistema = new SistemaCurtidas()
        def candidato = repository.arrayCandidatos.first()
        def vaga = repository.arrayVagas.first()
        sistema.candidatoCurteVaga(candidato, vaga)

        when:
        sistema.listCurtidasCandidato(candidato.vagasCurtidas)

        then:
        noExceptionThrown()
    }
}
