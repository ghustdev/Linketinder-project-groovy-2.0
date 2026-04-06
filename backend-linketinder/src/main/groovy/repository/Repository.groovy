package repository

import dao.CandidatoDao
import dao.EmpresaDao
import dao.VagaDao
import model.Candidato
import model.Curtida
import model.Empresa
import model.Match
import model.Vaga

class Repository {
    List<Candidato> arrayCandidatos = []
    List<Empresa> arrayEmpresas = []
    List<Vaga> arrayVagas = []
    List<Curtida> allCurtidasCandidatos = []
    List<Match> allMatches = []

    Repository() {
    }

    void loadFromDb(CandidatoDao candidatoDao, EmpresaDao empresaDao, VagaDao vagaDao) {
        arrayCandidatos = candidatoDao.listAll()
        arrayEmpresas = empresaDao.listAll()
        arrayVagas = vagaDao.listAll()
    }
}
