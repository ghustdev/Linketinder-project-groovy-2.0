package services

import model.Curtidas
import model.Candidato
import model.Empresa
import model.Vaga
import services.SistemaMatch

class SistemaCurtidas {

    private List<Curtidas>        todasCurtidas = []
    private List<SistemaMatch> matches       = []

    // Registra que um candidato curtiu uma vaga
    Curtidas candidatoCurteVaga(Candidato candidato, Vaga vaga) {
        Curtidas curtida = candidato.curtirVaga(vaga)
        todasCurtidas << curtida
        return curtida
    }

    // Registra que uma empresa curtiu um candidato e verifica match
    SistemaMatch empresaCurteCandidato(Empresa empresa, Candidato candidato) {
        Curtidas curtidaMatch = empresa.curtirCandidato(candidato, todasCurtidas)

        if (curtidaMatch) {
            SistemaMatch resultado = SistemaMatch.fromCurtida(curtidaMatch)
            matches << resultado
            return resultado
        }

        return null
    }

    // Retorna todos os matches confirmados
    List<SistemaMatch> getMatches() {
        return Collections.unmodifiableList(matches)
    }

    // Retorna apenas curtidas ainda sem match
    List<Curtidas> getCurtidasPendentes() {
        return todasCurtidas.findAll { !it.isMatch() }.asImmutable()
    }

    // Retorna todos os matches de um candidato específico
    List<SistemaMatch> matchesDoCandidato(Candidato candidato) {
        return matches.findAll { it.candidato == candidato }
    }

    // Retorna todos os matches de uma empresa específica
    List<SistemaMatch> matchesDaEmpresa(Empresa empresa) {
        return matches.findAll { it.empresa == empresa }
    }

    // Exibe resumo geral do sistema no console
    void exibirResumo() {
        println "\n╔═══════════════════════════════════════════════╗"
        println   "║        RESUMO DO SISTEMA LINKETINDER          ║"
        println   "╠═══════════════════════════════════════════════╣"
        println   "║  Total de curtidas  : ${todasCurtidas.size()}"
        println   "║  Curtidas pendentes : ${getCurtidasPendentes().size()}"
        println   "║  Matches gerados    : ${matches.size()}"
        println   "╠═══════════════════════════════════════════════╣"

        if (matches.isEmpty()) {
            println "║  Nenhum match até o momento."
        } else {
            println "║  MATCHES:"
            matches.eachWithIndex { m, i ->
                println "║  ${i + 1}. ${m.candidato.nome} ↔ ${m.empresa.nome}"
                println "║     Vaga: ${m.vaga.titulo}"
            }
        }
        println "╚═══════════════════════════════════════════════╝\n"
    }
}
