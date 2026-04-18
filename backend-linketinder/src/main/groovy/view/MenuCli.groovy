package view

class MenuCli {
    static void cliMainMenu(MainCli cli) {
        while (true) {
            try {
                println("+================================================+")
                println("|                   Linketinder                  |")
                println("+================================================+")
                println("[1] - Empresa")
                println("[2] - Candidato")
                println("[3] - Admin")
                println("[0] - Encerrar programa")
                println("+================================================+")

                Integer optionMenu = cli.readInt("Selecione uma opção: ")
                if (optionMenu == null) {
                    println("+================================================+")
                    println("| Adicione a entrada correta (números de 0 - 3).")
                    println("+================================================+")
                    cli.pause()
                    continue
                }

                if (optionMenu == 1) {
                    cliEnterpriseMenu(cli)
                } else if (optionMenu == 2) {
                    cliCandidateMenu(cli)
                } else if (optionMenu == 3) {
                    cliAdminMenu(cli)
                } else if (optionMenu == 0) {
                    cli.scanner.close()
                    break
                } else {
                    println("+================================================+")
                    println("| Adicione a entrada correta (números de 0 - 3).")
                    println("+================================================+")
                    cli.pause()
                }
            } catch (Exception e) {
                println("+================================================+")
                println("| Adicione a entrada correta (números de 0 - 3) - ${e.printStackTrace()}.")
                println("+================================================+")
                cli.pause()
            }
        }
    }

    static void cliAdminMenu(MainCli cli) {
        while (true) {
            try {
                println("+================================================+")
                println("|               Linketinder Admin                |")
                println("+================================================+")
                println("[1] - Adicionar empresa (com vaga)")
                println("[2] - Adicionar candidato")
                println("[3] - Listar empresas")
                println("[4] - Listar candidatos")
                println("[5] - [Empresa] Cadastrar vaga (empresa já cadastrada)")
                println("[6] - [Empresa] Visualizar curtidas (curtir Candidato)")
                println("[7] - [Candidato] Visualizar feed de vagas (curtir Vaga)")
                println("[8] - [Candidato] Visualizar curtidas")
                println("[9] - Listar todos os Matches")
                println("[10] - Listar Matches do Candidato")
                println("[11] - Listar Matches da Empresa")
                println("[0] - Encerrar programa")
                println("+================================================+")

                Integer optionMenu = cli.readInt("Selecione uma opção: ")
                if (optionMenu == null) {
                    println("+================================================+")
                    println("| Adicione a entrada correta (números de 0 - 11).")
                    println("+================================================+")
                    cli.pause()
                    continue
                }

                if (optionMenu == 1) {
                    cli.createCompany()
                } else if (optionMenu == 2) {
                    cli.createCandidate()
                } else if (optionMenu == 3) {
                    cli.listCompanies()
                } else if (optionMenu == 4) {
                    cli.listCandidates()
                } else if (optionMenu == 5) {
                    cli.createVacancy()
                } else if (optionMenu == 6) {
                    cli.reviewCompanyLikes()
                } else if (optionMenu == 7) {
                    cli.listVacancies()
                } else if (optionMenu == 8) {
                    cli.listCandidateLikes()
                } else if (optionMenu == 9) {
                    cli.listMatches()
                } else if (optionMenu == 10) {
                    cli.listCandidateMatches()
                } else if (optionMenu == 11) {
                    cli.listCompanyMatches()
                } else if (optionMenu == 0) {
                    cli.scanner.close()
                    break
                } else {
                    println("+================================================+")
                    println("| Insira uma opção válida (números de 0 - 11).")
                    println("+================================================+")
                    cli.pause()
                }
            } catch (Exception e) {
                println("+================================================+")
                println("| Adicione a entrada correta (números de 0 - 11) - ${e.printStackTrace()}")
                println("+================================================+")
                cli.pause()
            }
        }
    }

    static void cliCandidateMenu(MainCli cli) {
        while (true) {
            try {
                println("+================================================+")
                println("|             Linketinder Candidato              |")
                println("+================================================+")
                println("[1] - Feed de Vagas e curtir Vagas")
                println("[2] - Visualizar curtidas")
                println("[3] - Listar Matches do Candidato")
                println("[0] - Encerrar programa")
                println("+================================================+")

                Integer optionMenu = cli.readInt("Selecione uma opção: ")
                if (optionMenu == null) {
                    println("+================================================+")
                    println("| Adicione a entrada correta (números de 0 - 3).")
                    println("+================================================+")
                    cli.pause()
                    continue
                }

                if (optionMenu == 1) {
                    cli.createCompany()
                } else if (optionMenu == 2) {
                    cli.createCandidate()
                } else if (optionMenu == 3) {
                    cli.listCompanies()
                } else if (optionMenu == 0) {
                    cli.scanner.close()
                    break
                } else {
                    println("+================================================+")
                    println("| Insira uma opção válida (números de 0 - 3).")
                    println("+================================================+")
                    cli.pause()
                }
            } catch (Exception e) {
                println("+================================================+")
                println("| Adicione a entrada correta (números de 0 - 3) - ${e.printStackTrace()}")
                println("+================================================+")
                cli.pause()
            }
        }
    }

    static void cliEnterpriseMenu(MainCli cli) {
        while (true) {
            try {
                println("+================================================+")
                println("|              Linketinder Empresa               |")
                println("+================================================+")
                println("[1] - Cadastrar Vaga")
                println("[2] - Visualizar curtidas e curtir Candidatos")
                println("[3] - Listar Matches da Empresa")
                println("+================================================+")

                Integer optionMenu = cli.readInt("Selecione uma opção: ")
                if (optionMenu == null) {
                    println("+================================================+")
                    println("| Adicione a entrada correta (números de 0 - 3).")
                    println("+================================================+")
                    cli.pause()
                    continue
                }

                if (optionMenu == 1) {
                    cli.createCompany()
                } else if (optionMenu == 2) {
                    cli.createCandidate()
                } else if (optionMenu == 3) {
                    cli.listCompanies()
                } else if (optionMenu == 0) {
                    cli.scanner.close()
                    break
                } else {
                    println("+================================================+")
                    println("| Insira uma opção válida (números de 0 - 3).")
                    println("+================================================+")
                    cli.pause()
                }
            } catch (Exception e) {
                println("+================================================+")
                println("| Adicione a entrada correta (números de 0 - 3) - ${e.printStackTrace()}")
                println("+================================================+")
                cli.pause()
            }
        }
    }
}
