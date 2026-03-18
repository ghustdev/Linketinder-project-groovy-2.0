package view

class CliListCandidatosAction {
    static void cliListCandidatos(Cli cli) {
        try {
            println("+================================================+")
            println("|              Listagem de candidatos             |")
            println("+================================================+")

            cli.pessoaServices.listCandidatos()

            cli.pause()
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar candidatos. Erro: ${e}")
            println("+================================================+")
            cli.pause()
        }
    }
}

