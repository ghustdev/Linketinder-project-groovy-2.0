package view

class CliListLikesCandidatoAction {
    static void cliListLikesCandidato(Cli cli) {
        println("+================================================+")
        println("|              Visualizar curtidas               |")
        println("|                    Candidato                   |")
        println("+================================================+")

        def candidato = cli.cliChoiceCandidato()
        if (candidato == null) return

        try {
            def vagasCurtidas = candidato.listCurtidas()
            if (vagasCurtidas == null || vagasCurtidas.isEmpty()) {
                println("+================================================+")
                println("${candidato.name} não possui curtidas")
                println("+================================================+")
            } else {
                println("+================================================+")
                cli.sistemaCurtidas.listCurtidasCandidato(vagasCurtidas)
            }

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

