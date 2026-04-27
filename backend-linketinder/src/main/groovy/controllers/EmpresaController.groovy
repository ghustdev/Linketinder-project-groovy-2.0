package controllers

import entities.Candidato
import entities.Curtida
import entities.Empresa
import entities.Match
import entities.Vaga
import services.CurtidaService
import services.EmpresaService

class EmpresaController {
    private final EmpresaService empresaService
    private final CurtidaService curtidaService

    EmpresaController(EmpresaService empresaService, CurtidaService curtidaService) {
        this.empresaService = empresaService
        this.curtidaService = curtidaService
    }

    Empresa criarEmpresa(Empresa empresa) {
        return empresaService.criarEmpresa(empresa)
    }

    List<Empresa> listarEmpresas() {
        return empresaService.listarEmpresas()
    }

    Empresa buscarEmpresaPorCnpj(String cnpj) {
        return empresaService.buscarEmpresa(cnpj)
    }

    List<Curtida> listarCurtidasRecebidas(Empresa empresa) {
        return curtidaService.listarCurtidasRecebidasEmpresa(empresa)
    }

    Match empresaCurteCandidato(Empresa empresa, Candidato candidato, Vaga vaga) {
        return curtidaService.empresaCurteCandidato(empresa, candidato, vaga)
    }

    List<Match> listarMatchesDaEmpresa(Empresa empresa) {
        return curtidaService.obterTodosMatches().findAll { it.empresa?.cnpj == empresa?.cnpj }
    }
}
