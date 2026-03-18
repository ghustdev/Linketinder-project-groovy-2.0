package view

class CliListMatchesEmpresaAction {
    static void cliListMatchesEmpresa(Cli cli) {
        println("+================================================+")
        println("|             Matches da empresa                 |")
        println("+================================================+")

        def empresa = cli.cliChoiceEmpresa()
        if (empresa == null) return

        def matchesEmpresa = cli.sistemaCurtidas.allMatches.findAll { it.empresa?.cnpj == empresa.cnpj }

        if (matchesEmpresa.isEmpty()) {
            println("Nenhum match para ${empresa.name}.")
            println("+================================================+")
            cli.pause()
            return
        }

        matchesEmpresa.eachWithIndex { m, index ->
            println("Match: ${index + 1}")
            println("Candidato: ${m.candidato.name} (${m.candidato.cpf})")
            println("Vaga: ${m.vaga.title} [id=${m.vaga.id}]")
            println("Data: ${m.dateMatch}")
            println("+================================================+")
        }
        cli.pause()
    }
}

