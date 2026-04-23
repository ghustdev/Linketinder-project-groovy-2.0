package view

import services.CurtidaService

class MatchCli {
    private final IEntradaConsolePadrao io
    private final CurtidaService curtidaServico

    MatchCli(IEntradaConsolePadrao io, CurtidaService curtidaServico) {
        this.io = io
        this.curtidaServico = curtidaServico
    }

    void listarMatches() {
        println("+================================================+")
        println("|                 Todos os matches               |")
        println("+================================================+")

        if (curtidaServico.obterTodosMatches().isEmpty()) {
            println("Nenhum match registrado até agora.")
            println("+================================================+")
            io.pausar()
            return
        }

        curtidaServico.obterTodosMatches().eachWithIndex { match, index ->
            println("Match: ${index + 1}")
            println("Candidato: ${match.candidato.nome} (${match.candidato.cpf})")
            println("Empresa: ${match.empresa.nome} (${match.empresa.cnpj})")
            println("Vaga: ${match.vaga.titulo} [id=${match.vaga.id}]")
            println("Data: ${match.dataMatch}")
            println("+================================================+")
        }
        io.pausar()
    }
}
