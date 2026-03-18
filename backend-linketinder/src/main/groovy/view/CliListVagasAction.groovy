package view

class CliListVagasAction {
    static void cliListVagas(Cli cli) {
        println("+================================================+")
        println("|                Visualizar vagas                |")
        println("+================================================+")
        def candidato = cli.cliChoiceCandidato()
        if (candidato == null) return

        println("+================================================+")
        println("Seja bem vindo(a), ${candidato.name}.")
        println("+================================================+")
        cli.pause()

        try {
            cli.vagaServices.listVagas()

            print("${candidato.name}, deseja curtir alguma vaga? (s/n): ")
            def answer = cli.scanner.nextLine().toLowerCase().trim()

            while (answer == "s") {
                println("+================================================+")
                Integer id = cli.readInt("Escolha o Id da vaga que deseja curtir: ")
                if (id == null) {
                    println("+================================================+")
                    println("Id inválido.")
                    println("+================================================+")
                    cli.pause()
                    return
                }

                def vaga = cli.vagaServices.searchIdVaga(id)

                if (vaga == null) {
                    println("+================================================+")
                    println("Essa vaga não existe!")
                    println("+================================================+")
                    cli.pause()
                    return
                }

                cli.sistemaCurtidas.candidatoCurteVaga(candidato, vaga)

                println("+================================================+")
                println("${candidato.name} curtiu a vaga '${vaga.title}' da Empresa ${vaga.empresa.name}. Vaga CURTIDA com sucesso!")
                println("+================================================+")

                print("Deseja curtir outra vaga? (s/n): ")
                answer = cli.scanner.nextLine().toLowerCase().trim()
            }
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar vagas ou ao curtir. Erro: ${e}")
            println("+================================================+")
            cli.pause()
        }
    }
}
