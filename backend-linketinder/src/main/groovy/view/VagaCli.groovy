package view

import controllers.VagaController
import entities.Candidato
import entities.Curtida
import entities.Vaga
import exceptions.ExecucaoException

class VagaCli {
    private final IEntradaConsolePadrao io
    private final VagaController vagaController
    private final EmpresaCli empresaCli
    private final CandidatoCli candidatoCli

    VagaCli(IEntradaConsolePadrao io, VagaController vagaController, EmpresaCli empresaCli, CandidatoCli candidatoCli) {
        this.io = io
        this.vagaController = vagaController
        this.empresaCli = empresaCli
        this.candidatoCli = candidatoCli
    }

    void criarVaga() {
        println("+================================================+")
        println("|                 Cadastrar Vaga                 |")
        println("+================================================+")
        try {
            def empresa = empresaCli.selecionarEmpresaPorCnpj()
            if (empresa == null) return

            println("+================================================+")
            println("${empresa.nome}, preencha as informações da vaga: ")
            print("Título: "); String nome = io.lerLinha()
            print("Descrição da vaga: "); String descricao = io.lerLinha()
            print("Estado: "); String estado = io.lerLinha()
            print("Cidade: "); String cidade = io.lerLinha()
            print("Lista de competências requeridas (separado por ','): "); String entrada = io.lerLinha()
            List<String> competenciasRequeridas = EditarEntradaCompetencia.formatarCompetencias(entrada)

            Vaga vaga = vagaController.criarVaga(nome, descricao, estado, cidade, empresa, competenciasRequeridas)

            println("+================================================+")
            println("Vaga, ${vaga.titulo}, cadastrada com sucesso!")
            println("+================================================+")
            io.pausar()
        } catch (ExecucaoException e) {
            println("+================================================+")
            println("Falha ao cadastrar vaga. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        } catch (Exception e) {
            println("+================================================+")
            println("Falha ao cadastrar vaga. Tente novamente. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        }
    }

    void listarVagasFeed() {
        println("+================================================+")
        println("|             Feed: Visualizar Vagas             |")
        println("+================================================+")

        def candidato = candidatoCli.selecionarCandidatoPorCpf()
        if (candidato == null) return

        println("+================================================+")
        println("Seja bem vindo(a), ${candidato.nome}.")
        println("+================================================+")
        io.pausar()

        println("+================================================+")
        println("|             Feed: Visualizar Vagas             |")
        println("+================================================+")
        try {
            vagaController.listarVagas().each { vaga ->
                println "Id vaga: ${vaga.id}"
                println "Empresa: ${vaga.empresa?.nome}"
                println "CNPJ: ${vaga.empresa?.cnpj}"
                println "Título: ${vaga.titulo}"
                println "Descrição: ${vaga.descricao}"
                println "Estado: ${vaga.estado}"
                println "Cidade: ${vaga.cidade}"
                println "Requisitos:"
                vaga.competenciasRequeridas.each { println " - ${it.nome}" }
                println("+================================================+")
            }

            this.custirVagaCandidato(candidato)
        } catch (ExecucaoException e) {
            println("+================================================+")
            println("Falha ao listar vagas ou ao curtir. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        } catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar vagas ou ao curtir. Tente novamente. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        }
    }

    void custirVagaCandidato(Candidato candidato) {
        print("${candidato.nome}, deseja curtir alguma vaga? (s/n): ")
        def resposta = io.lerLinha().toLowerCase().trim()

        while (resposta == "s") {
            println("+================================================+")
            Integer idEntrada = io.lerInteiro("Escolha o Id da vaga que deseja curtir: ")
            if (idEntrada == null) {
                println("+================================================+")
                println("Id inválido.")
                println("+================================================+")
                io.pausar()
                return
            }

            Long id = idEntrada as Long
            def vaga = vagaController.buscarVagaPorId(id)

            if (vaga == null) {
                println("+================================================+")
                println("Essa vaga não existe!")
                println("+================================================+")
                io.pausar()
                return
            }

            Curtida curtida = vagaController.candidatoCurteVaga(candidato, vaga)

            println("+================================================+")
            println("'${curtida.candidato.nome}' curtiu a vaga '${curtida.vaga.titulo}' da Empresa '${curtida.empresa.nome}'. Vaga CURTIDA com sucesso!")
            println("+================================================+")

            print("Deseja curtir outra vaga? (s/n): ")
            resposta = io.lerLinha().toLowerCase().trim()
        }
    }
}
