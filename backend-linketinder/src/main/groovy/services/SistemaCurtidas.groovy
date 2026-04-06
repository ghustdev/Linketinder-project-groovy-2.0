package services


import model.Curtida
import model.Candidato
import model.Empresa
import model.Match
import model.Vaga

class SistemaCurtidas {

    List<Curtida> allCurtidasCandidatos = []
    List<Match> allMatches = []

    void listCurtidasCandidato(List<Curtida> vagasCurtidas) {
        vagasCurtidas.each { c ->
            println("Vaga: ${c.vaga.nome}")
            println("Candidato: ${c.candidato.nome}")
            println("CPF: ${c.candidato.cpf}")
            println("Empresa: ${c.empresa.nome}")
            println("CNPJ: ${c.empresa.cnpj}")
            println("+================================================+")
        }
    }

    void listCurtidasRecebidasEmpresa(List<Curtida> curtidasRecebidas) {
        curtidasRecebidas.each { curtida ->
            def c = curtida.candidato
            def status = "Pendente"
            if (curtida.empresa.jaCurtiuCandidato(c, curtida.vaga)) {
                def match = new Match(candidato: c, empresa: curtida.empresa, vaga: curtida.vaga)
                status = match.isMatch(curtida.empresa.candidatosCurtidos, allCurtidasCandidatos) ? "MATCH!" : "Pendente"
            }
            println("Candidato: ${c.nome}")
            println("CPF: ${c.cpf}")
            println("Id Vaga: ${curtida.vaga.id}")
            println("Skills: ${c.skills.join(', ')}")
            println("Vaga: ${curtida.vaga.nome}")
            println("--------------------------------------------------")
            println("Status: ${status}")
            println("+================================================+")
        }
    }

    Curtida candidatoCurteVaga(Candidato candidato, Vaga vaga) {
        Curtida curtida = candidato.curtirVaga(vaga)
        allCurtidasCandidatos.add(curtida)
        return curtida
    }

    Match empresaCurteCandidato(Empresa empresa, Candidato candidato, Vaga vaga) {
        def match = empresa.curtirCandidato(candidato, vaga, allCurtidasCandidatos)
        if (match != null && !allMatches.any {
            it.candidato.cpf == match.candidato.cpf &&
                    it.empresa.cnpj == match.empresa.cnpj &&
                    it.vaga?.id == match.vaga?.id
        }) {
            allMatches.add(match)
        }
        return match
    }
}
