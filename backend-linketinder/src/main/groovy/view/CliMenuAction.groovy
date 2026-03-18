package view

class CliMenuAction {
    static void cliMenu(Cli cli) {
        while (true) {
            try {
                println("+================================================+")
                println("|                   Linketinder                  |")
                println("+================================================+")
                println("[1] - Adicionar empresa")
                println("[2] - Adicionar candidato")
                println("[3] - Listar empresas")
                println("[4] - Listar candidatos")
                println("[5] - [Empresa] Cadastrar vaga")
                println("[6] - [Empresa] Visualizar curtidas (curtir Candidato)")
                println("[7] - [Candidato] Visualizar feed de vagas (curtir Vaga)")
                println("[8] - [Candidato] Visualizar curtidas")
                println("[9] - Listar Matches")
                println("[10] - Listar Matches do Candidato")
                println("[11] - Listar Matches da Empresa")
                println("[0] - Encerrar programa")
                println("+================================================+")
                // Implementação futura: separar cada sessão em Empresa, Candidato e Admin, simulando um login
                /* println("[1] - Empresa")
                println("[2] - Candidato")
                println("[3] - Admin")
                println("+================================================+")*/
                Integer optionMenu = cli.readInt("Selecione uma opção: ")
                if (optionMenu == null) {
                    println("+================================================+")
                    println("Adicione a entrada correta (números de 0 - 11).")
                    println("+================================================+")
                    cli.pause()
                    continue
                }

                if (optionMenu == 1) {
                    cli.cliCreateEmpresa()
                } else if (optionMenu == 2) {
                    cli.cliCreateCandidato()
                } else if (optionMenu == 3) {
                    cli.cliListEmpresas()
                } else if (optionMenu == 4) {
                    cli.cliListCandidatos()
                } else if (optionMenu == 5) {
                    cli.cliCreateVaga()
                } else if (optionMenu == 6) {
                    cli.cliVerifyLikesEmpresa()
                } else if (optionMenu == 7) {
                    cli.cliListVagas()
                } else if (optionMenu == 8) {
                    cli.cliListLikesCandidato()
                } else if (optionMenu == 9) {
                    cli.cliListMatches()
                } else if (optionMenu == 10) {
                    cli.cliListMatchesCandidato()
                } else if (optionMenu == 11) {
                    cli.cliListMatchesEmpresa()
                } else if (optionMenu == 0) {
                    cli.scanner.close()
                    break
                } else {
                    println("+================================================+")
                    println("Insira uma opção válida (números de 0 - 11). Erro: opção fora do limite")
                    println("+================================================+")
                    cli.pause()
                }
            } catch (Exception e) {
                println("+================================================+")
                println("Adicione a entrada correta (números de 0 - 11). Erro: ${e}")
                println("+================================================+")
                cli.pause()
            }
        }
    }
}
