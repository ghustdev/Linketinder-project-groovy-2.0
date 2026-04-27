package view

import controllers.MatchController

class MatchCli {
    private final IEntradaConsolePadrao io
    private final MatchController matchController

    MatchCli(IEntradaConsolePadrao io, MatchController matchController) {
        this.io = io
        this.matchController = matchController
    }

    void listarMatches() {
        println("+================================================+")
        println("|                 Todos os matches               |")
        println("+================================================+")

        if (matchController.listarTodosMatches().isEmpty()) {
            println("Nenhum match registrado até agora.")
            println("+================================================+")
            io.pausar()
            return
        }

        matchController.listarTodosMatches().eachWithIndex { match, index ->
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
