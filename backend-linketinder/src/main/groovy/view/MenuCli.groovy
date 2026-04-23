package view

import exceptions.ExecucaoException

class MenuCli {
    private final IEntradaConsolePadrao io
    private final CandidatoCli candidatoCli
    private final EmpresaCli empresaCli
    private final MatchCli matchCli
    private final VagaCli vagaCli

    MenuCli(IEntradaConsolePadrao io, CandidatoCli candidatoCli, EmpresaCli empresaCli, MatchCli matchCli, VagaCli vagaCli) {
        this.io = io
        this.candidatoCli = candidatoCli
        this.empresaCli = empresaCli
        this.matchCli = matchCli
        this.vagaCli = vagaCli
    }

    void menuPrincipal() {
        while (true) {
            try {
                println("+================================================+")
                println("|                  Linketinder                   |")
                println("+================================================+")
                println("[0] - Encerrar programa")
                println("[1] - Adicionar empresa (com vaga)")
                println("[2] - Listar empresas")
                println("[3] - Adicionar candidato")
                println("[4] - Listar candidatos")
                println("[5] - [Empresa] Cadastrar vaga")
                println("[6] - [Empresa] Visualizar curtidas recebidas")
                println("[7] - [Candidato] Visualizar feed de vagas")
                println("[8] - [Candidato] Visualizar curtidas efetuadas")
                println("+================================================+")
                println("[9] - Listar todos os Matches")
                println("[10] - Listar Matches do Candidato")
                println("[11] - Listar Matches da Empresa")
                println("+================================================+")

                Integer opcaoMenu = io.lerInteiro("Selecione uma opção: ")
                if (opcaoMenu == null) {
                    println("+================================================+")
                    println("| Adicione a entrada correta (números de 0 - 11).")
                    println("+================================================+")
                    io.pausar()
                    continue
                }

                switch (opcaoMenu) {
                    case 0: return
                    case 1: empresaCli.criarEmpresa(); break
                    case 2: empresaCli.listarEmpresas(); break
                    case 3: candidatoCli.criarCandidato(); break
                    case 4: candidatoCli.listarCandidatos(); break
                    case 5: vagaCli.criarVaga(); break
                    case 6: empresaCli.curtidasRecebidasEmpresa(); break
                    case 7: vagaCli.listarVagasFeed(); break
                    case 8: candidatoCli.listarCurtidasCandidato(); break
                    case 9: matchCli.listarMatches(); break
                    case 10: candidatoCli.listarMatchesCandidato(); break
                    case 11: empresaCli.listarMatchesEmpresa(); break
                    default:
                        println("+================================================+")
                        println("| Insira uma opção válida (números de 0 - 11).")
                        println("+================================================+")
                        io.pausar()
                }
            } catch (ExecucaoException e) {
                println("+================================================+")
                println("| Operação inválida. Erro: ${e.message}")
                println("+================================================+")
                io.pausar()
            } catch (Exception e) {
                println("+================================================+")
                println("| Erro inesperado. Tente novamente. Erro: ${e.message}")
                println("+================================================+")
                io.pausar()
            }
        }
    }
}
