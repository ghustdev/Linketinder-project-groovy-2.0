package view

import spock.lang.Specification

class EditarEntradaCompetenciaTest extends Specification {

    def "retorna lista vazia para entrada nula"() {
        expect:
        EditarEntradaCompetencia.formatarCompetencias(null) == []
    }

    def "retorna lista vazia para entrada em branco"() {
        expect:
        EditarEntradaCompetencia.formatarCompetencias("   ") == []
    }

    def "separa competencias por virgula e remove espacos"() {
        expect:
        EditarEntradaCompetencia.formatarCompetencias("Java, Groovy, SQL") == ["Java", "Groovy", "SQL"]
    }

    def "ignora entradas vazias entre virgulas"() {
        expect:
        EditarEntradaCompetencia.formatarCompetencias("Java,,Groovy") == ["Java", "Groovy"]
    }

    def "retorna lista com um elemento para entrada simples"() {
        expect:
        EditarEntradaCompetencia.formatarCompetencias("Java") == ["Java"]
    }
}
