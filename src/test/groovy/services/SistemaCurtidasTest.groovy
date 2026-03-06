package services

import repository.Repository
import spock.lang.Specification

class SistemaCurtidasTest extends Specification {

    def "deve criar match quando empresa curte candidato na mesma vaga curtida pelo candidato"() {
        given:
        def repository = new Repository()
        def sistemaCurtidas = new SistemaCurtidas()
        def candidato = repository.arrayCandidatos[0]
        def vaga = repository.arrayVagas[0]
        def empresa = vaga.empresa
        sistemaCurtidas.candidatoCurteVaga(candidato, vaga)

        when:
        def match = sistemaCurtidas.empresaCurteCandidato(empresa, candidato, vaga)

        then:
        match != null
        match.isMatch(empresa.candidatosCurtidos, sistemaCurtidas.allCurtidasCandidatos)
        sistemaCurtidas.allMatches.size() == 1
    }

    def "nao deve permitir curtida duplicada da empresa para mesmo candidato e vaga"() {
        given:
        def repository = new Repository()
        def sistemaCurtidas = new SistemaCurtidas()
        def candidato = repository.arrayCandidatos[0]
        def vaga = repository.arrayVagas[0]
        def empresa = vaga.empresa
        sistemaCurtidas.candidatoCurteVaga(candidato, vaga)
        sistemaCurtidas.empresaCurteCandidato(empresa, candidato, vaga)

        when:
        sistemaCurtidas.empresaCurteCandidato(empresa, candidato, vaga)

        then:
        thrown(IllegalStateException)
    }

    def "listagem de curtidas recebidas da empresa deve funcionar sem erro de metodo ausente"() {
        given:
        def repository = new Repository()
        def sistemaCurtidas = new SistemaCurtidas()
        def candidato = repository.arrayCandidatos[0]
        def vaga = repository.arrayVagas[0]
        sistemaCurtidas.candidatoCurteVaga(candidato, vaga)
        def curtidasRecebidas = sistemaCurtidas.allCurtidasCandidatos.findAll { it.vaga.empresa.cnpj == vaga.empresa.cnpj }

        when:
        sistemaCurtidas.listCurtidasRecebidasEmpresa(curtidasRecebidas)

        then:
        noExceptionThrown()
    }
}
