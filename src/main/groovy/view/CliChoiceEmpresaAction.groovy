package view

import model.Empresa

class CliChoiceEmpresaAction {
    static Empresa cliChoiceEmpresa(Cli cli) {
        Empresa empresa = null

        try {
            cli.pessoaServices.listEmpresas()

            print("Escolha uma empresa (pelo CNPJ): ")
            def cnpj = cli.scanner.nextLine()

            empresa = cli.vagaServices.searchEmpresa(cnpj)

            if (empresa == null) {
                println("+================================================+")
                println("Essa empresa não existe!")
                println("+================================================+")
                cli.pause()
                return empresa
            }
        }
        catch (Exception e) {
            println("+================================================+")
            println("Falha ao confirmar empresa. Erro: ${e}")
            println("+================================================+")
            cli.pause()
        }
        return empresa
    }
}

