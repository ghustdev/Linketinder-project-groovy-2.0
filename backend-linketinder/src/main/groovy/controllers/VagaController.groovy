package controllers

import entities.Candidato
import entities.Curtida
import entities.Vaga
import services.CurtidaService
import services.VagaService

class VagaController {
    private final VagaService vagaService
    private final CurtidaService curtidaService

    VagaController(VagaService vagaService, CurtidaService curtidaService) {
        this.vagaService = vagaService
        this.curtidaService = curtidaService
    }

    Vaga criarVaga(Vaga vaga) {
        return vagaService.criarVaga(vaga)
    }

    List<Vaga> listarVagas() {
        return vagaService.listarVagas()
    }

    Vaga buscarVagaPorId(Long id) {
        return vagaService.buscarVagaPorId(id)
    }

    Curtida candidatoCurteVaga(Candidato candidato, Vaga vaga) {
        return curtidaService.candidatoCurteVaga(candidato, vaga)
    }
}
