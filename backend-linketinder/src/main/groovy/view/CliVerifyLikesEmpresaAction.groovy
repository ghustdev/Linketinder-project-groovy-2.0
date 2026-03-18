package view

class CliVerifyLikesEmpresaAction {
    static void cliVerifyLikesEmpresa(Cli cli) {
        println("+================================================+")
        println("|          Candidatos que curtiram você          |")
        println("|                     Empresa                    |")
        println("+================================================+")

        def empresa = cli.cliChoiceEmpresa()
        if (empresa == null) return

        try {
            def curtidasRecebidas = cli.sistemaCurtidas.allCurtidasCandidatos.findAll { it.vaga.empresa.cnpj == empresa.cnpj }

            if (curtidasRecebidas.isEmpty()) {
                println("+================================================+")
                println("Nenhum candidato curtiu suas vagas ainda.")
                println("+================================================+")
                cli.pause()
                return
            }

            println("+================================================+")
            cli.sistemaCurtidas.listCurtidasRecebidasEmpresa(curtidasRecebidas)

            print("Deseja curtir algum candidato de volta? (s/n): ")
            def answer = cli.scanner.nextLine().toLowerCase().trim()

            while (answer == "s") {
                println("+================================================+")
                print("Digite o CPF do candidato da lista acima: ")
                String cpf = cli.scanner.nextLine().trim()

                Integer idVaga = cli.readInt("Digite o ID da vaga que o candidato curtiu: ")

                def vagaSearch = cli.vagaServices.searchIdVaga(idVaga)

                if (idVaga == null) {
                    println("+================================================+")
                    println("ID de vaga inválido.")
                    println("+================================================+")
                    cli.pause()
                    continue
                }

                if (vagaSearch == null) {
                    println("+================================================+")
                    println("Essa vaga não existe!")
                    println("+================================================+")
                    cli.pause()
                    return
                }

                def curtidaSelecionada = curtidasRecebidas.find {
                    it.candidato.cpf == cpf && it.vaga.id == idVaga
                }
                if (curtidaSelecionada == null) {
                    println("+================================================+")
                    println("Candidato inválido. Use somente CPF e vaga exibidos na lista acima.")
                    println("+================================================+")
                    cli.pause()
                    continue
                }

                def candidato = curtidaSelecionada.candidato
                def vaga = curtidaSelecionada.vaga
                def resultadoMatch = cli.sistemaCurtidas.empresaCurteCandidato(empresa, candidato, vaga)
                if (resultadoMatch == null) {
                    println("+================================================+")
                    println("Não foi possível gerar match para essa seleção.")
                    println("+================================================+")
                    cli.pause()
                    continue
                }

                println("+================================================+")
                println("É UM MATCH! Você e ${candidato.name} agora estão conectados.")
                println("${empresa.name} curtiu o candidato ${candidato.name} para a vaga: ${vaga.title}")
                println("+================================================+")

                print("Deseja curtir outro candidato? (s/n): ")
                answer = cli.scanner.nextLine().toLowerCase().trim()
            }

            cli.pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Erro ao processar curtidas da empresa: ${e.message}")
            println("+================================================+")
            cli.pause()
        }
    }
}

