package view

class CliListMatchesCandidatoAction {
    static void cliListMatchesCandidato(Cli cli) {
        println("+================================================+")
        println("|            Matches do candidato                |")
        println("+================================================+")

        def candidato = cli.cliChoiceCandidato()
        if (candidato == null) return

        def matchesCandidato = cli.sistemaCurtidas.allMatches.findAll { it.candidato?.cpf == candidato.cpf }

        if (matchesCandidato.isEmpty()) {
            println("Nenhum match para ${candidato.nome}.")
            println("+================================================+")
            cli.pause()
            return
        }

        matchesCandidato.eachWithIndex { m, index ->
            println("Match: ${index + 1}")
            println("Empresa: ${m.empresa.nome} (${m.empresa.cnpj})")
            println("Vaga: ${m.vaga.nome} [id=${m.vaga.id}]")
            println("Data: ${m.dateMatch}")
            println("+================================================+")
        }
        cli.pause()
    }
}

