package view

class CliListEmpresasAction {
    static void cliListEmpresas(Cli cli) {
        try {
            println("+================================================+")
            println("|              Listagem de empresas              |")
            println("+================================================+")

            cli.pessoaServices.listEmpresas()

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
