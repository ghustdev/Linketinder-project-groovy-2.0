package view

class CliListMatchesAction {
    static void cliListMatches(Cli cli) {
        println("+================================================+")
        println("|                 Todos os matches               |")
        println("+================================================+")

        if (cli.sistemaCurtidas.allMatches.isEmpty()) {
            println("Nenhum match registrado até agora.")
            println("+================================================+")
            cli.pause()
            return
        }

        cli.sistemaCurtidas.allMatches.eachWithIndex { m, index ->
            println("Match: ${index + 1}")
            println("Candidato: ${m.candidato.name} (${m.candidato.cpf})")
            println("Empresa: ${m.empresa.name} (${m.empresa.cnpj})")
            println("Vaga: ${m.vaga.title} [id=${m.vaga.id}]")
            println("Data: ${m.dateMatch}")
            println("+================================================+")
        }
        cli.pause()
    }
}

