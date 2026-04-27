package view

import controllers.EmpresaController
import controllers.VagaController
import exceptions.ExecucaoException
import entities.Empresa
import entities.Vaga

class EmpresaCli {
    private final IEntradaConsolePadrao io
    private final EmpresaController empresaController
    private final VagaController vagaController

    EmpresaCli(IEntradaConsolePadrao io, EmpresaController empresaController, VagaController vagaController) {
        this.io = io
        this.empresaController = empresaController
        this.vagaController = vagaController
    }

    void criarEmpresa() {
        try {
            println("+================================================+")
            println("|                Cadastrar Empresa               |")
            println("+================================================+")
            print("Nome: "); String nome = io.lerLinha()
            print("Email: "); String email = io.lerLinha()
            print("CNPJ: "); String cnpj = io.lerLinha()
            print("País: "); String pais = io.lerLinha()
            print("CEP: "); String cep = io.lerLinha()
            print("Descrição: "); String descricao = io.lerLinha()

            Empresa empresa = empresaController.criarEmpresa(nome, email, cnpj, pais, cep, descricao)

            println("+================================================+")
            println("Empresa, ${empresa.nome}, cadastrada com sucesso!")
            println()
            println("+================================================+")
            println("Agora cadastre uma vaga para a empresa ${empresa.nome}:")
            println("+================================================+")
            print("Título: "); String nomeVaga = io.lerLinha()
            print("Descrição da vaga: "); String descricaoVaga = io.lerLinha()
            print("Estado: "); String estado = io.lerLinha()
            print("Cidade: "); String cidade = io.lerLinha()
            print("Lista de competências requeridas (separado por ','): "); String entrada = io.lerLinha()

            List<String> competenciasRequeridas = EditarEntradaCompetencia.formatarCompetencias(entrada)

            Vaga vaga = vagaController.criarVaga(nomeVaga, descricaoVaga, estado, cidade, empresa, competenciasRequeridas)

            println("+================================================+")
            println("Vaga, ${vaga.titulo}, cadastrada com sucesso!")
            println("+================================================+")
            io.pausar()
        } catch (ExecucaoException e) {
            println("+================================================+")
            println("Falha ao cadastrar empresa. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        } catch (Exception e) {
            println("+================================================+")
            println("Falha ao cadastrar empresa. Tente novamente. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        }
    }

    void listarEmpresas() {
        try {
            println("+================================================+")
            println("|              Listagem de empresas              |")
            println("+================================================+")
            empresaController.listarEmpresas().each { empresa ->
                println "ID Empresa: ${empresa.id}"
                println "CNPJ: ${empresa.cnpj}"
                println "Nome: ${empresa.nome}"
                println "Email: ${empresa.email}"
                println "Descrição: ${empresa.descricao}"
                println "País: ${empresa.pais}"
                println "CEP: ${empresa.cep}"
                println("+================================================+")
            }
            io.pausar()
        } catch (ExecucaoException e) {
            println("+================================================+")
            println("Falha ao listar empresas. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        } catch (Exception e) {
            println("+================================================+")
            println("Falha ao listar empresas. Tente novamente. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        }
    }

    void curtidasRecebidasEmpresa() {
        println("+================================================+")
        println("|            Candidatos que curtiram a           |")
        println("|                    Empresa                     |")
        println("+================================================+")

        def empresa = selecionarEmpresaPorCnpj()
        if (empresa == null) return

        try {
            def curtidasRecebidas = empresaController.listarCurtidasRecebidas(empresa)

            if (curtidasRecebidas.isEmpty()) {
                println("+================================================+")
                println("Nenhum candidato curtiu suas vagas ainda.")
                println("+================================================+")
                io.pausar()
                return
            }

            println("+================================================+")
            curtidasRecebidas.each { curtida ->
                def jaTemMatch = empresaController.listarMatchesDaEmpresa(empresa).any {
                    it.candidato.cpf == curtida.candidato.cpf && it.vaga.id == curtida.vaga.id
                }
                println("Candidato: ${curtida.candidato.nome}")
                println("CPF: ${curtida.candidato.cpf}")
                println("Id Vaga: ${curtida.vaga.id}")
                println("Competências: ${curtida.candidato.competencias.collect { it.nome }.join(', ')}")
                println("Vaga: ${curtida.vaga.titulo}")
                println("Status: ${jaTemMatch ? 'MATCH!' : 'Pendente'}")
                println("+================================================+")
            }

            print("Deseja curtir algum candidato de volta? (s/n): ")
            def resposta = io.lerLinha().toLowerCase().trim()

            while (resposta == "s") {
                println("+================================================+")
                print("Digite o CPF do candidato da lista acima: ")
                String cpf = io.lerLinha().trim()

                Integer idVagaEntrada = io.lerInteiro("Digite o ID da vaga que o candidato curtiu: ")
                if (idVagaEntrada == null) {
                    println("+================================================+")
                    println("ID de vaga inválido.")
                    println("+================================================+")
                    io.pausar()
                    continue
                }

                Long vagaId = idVagaEntrada as Long
                def vaga = vagaController.buscarVagaPorId(vagaId)

                if (vaga == null) {
                    println("+================================================+")
                    println("Essa vaga não existe!")
                    println("+================================================+")
                    io.pausar()
                    return
                }

                def curtidaSelecionada = curtidasRecebidas.find { it.candidato.cpf == cpf && it.vaga.id == vagaId }
                if (curtidaSelecionada == null) {
                    println("+================================================+")
                    println("Candidato inválido. Use somente CPF e vaga exibidos na lista acima.")
                    println("+================================================+")
                    io.pausar()
                    continue
                }

                def candidato = curtidaSelecionada.candidato
                def resultadoMatch = empresaController.empresaCurteCandidato(empresa, candidato, vaga)
                if (resultadoMatch == null) {
                    println("+================================================+")
                    println("Não foi possível gerar match para essa seleção.")
                    println("+================================================+")
                    io.pausar()
                    continue
                }

                println("+================================================+")
                println("É UM MATCH! Você e ${candidato.nome} agora estão conectados.")
                println("${empresa.nome} curtiu o candidato ${candidato.nome} para a vaga: ${vaga.titulo}")
                println("+================================================+")

                print("Deseja curtir outro candidato? (s/n): ")
                resposta = io.lerLinha().toLowerCase().trim()
            }
            io.pausar()
        } catch (ExecucaoException e) {
            println("+================================================+")
            println("Erro ao processar curtidas da empresa. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        } catch (Exception e) {
            println("+================================================+")
            println("Erro ao processar curtidas da empresa. Tente novamente. Erro: ${e.message}")
            println("+================================================+")
            io.pausar()
        }
    }

    void listarMatchesEmpresa() {
        println("+================================================+")
        println("|             Matches da Empresa                 |")
        println("+================================================+")

        def empresa = selecionarEmpresaPorCnpj()
        if (empresa == null) return

        def matchesEmpresa = empresaController.listarMatchesDaEmpresa(empresa)

        if (matchesEmpresa.isEmpty()) {
            println("Nenhum match para ${empresa.nome}.")
            println("+================================================+")
            io.pausar()
            return
        }

        matchesEmpresa.eachWithIndex { match, index ->
            println("Match: ${index + 1}")
            println("Candidato: ${match.candidato.nome} (${match.candidato.cpf})")
            println("Vaga: ${match.vaga.titulo} [id=${match.vaga.id}]")
            println("Data: ${match.dataMatch}")
            println("+================================================+")
        }
        io.pausar()
    }

    Empresa selecionarEmpresaPorCnpj() {
        println("+================================================+")
        println("|            Entre com uma Empresa               |")
        println("+================================================+")
        try {
            this.listarEmpresas()

            println("+================================================+")
            print("Escolha uma empresa (pelo CNPJ): ")
            def cnpj = io.lerLinha()
            def empresa = empresaController.buscarEmpresaPorCnpj(cnpj)
            if (empresa == null) {
                println("+================================================+")
                println("Essa empresa não existe!")
                println("+================================================+")
                io.pausar()
                return null
            }
            return empresa
        } catch (Exception e) {
            println("+================================================+")
            println("Falha ao confirmar empresa. Erro: ${e}")
            println("+================================================+")
            io.pausar()
        }
        return null
    }
}
