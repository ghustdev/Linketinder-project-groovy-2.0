package view

class CliListMatchesAction {
    static void listMatches(MainCli cli) {
        println("+================================================+")
        println("|                 Todos os matches               |")
        println("+================================================+")

        if (cli.likeSystem.allMatches.isEmpty()) {
            println("Nenhum match registrado até agora.")
            println("+================================================+")
            cli.pause()
            return
        }

        cli.likeSystem.allMatches.eachWithIndex { match, index ->
            println("Match: ${index + 1}")
            println("Candidato: ${match.candidate.name} (${match.candidate.cpf})")
            println("Empresa: ${match.company.name} (${match.company.cnpj})")
            println("Vaga: ${match.vacancy.name} [id=${match.vacancy.id}]")
            println("Data: ${match.matchDate}")
            println("+================================================+")
        }
        cli.pause()
    }
}
