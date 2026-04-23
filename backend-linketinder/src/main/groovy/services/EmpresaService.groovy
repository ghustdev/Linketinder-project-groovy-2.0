package services

import exceptions.RecursoDuplicadoException
import exceptions.OperacaoPersistenciaException
import entities.Empresa
import repositories.ICompetenciaRepository
import repositories.IEmpresaRepository

class EmpresaService {
    IEmpresaRepository empresaDao
    ICompetenciaRepository competenciaDao

    EmpresaService (IEmpresaRepository empresaDao, ICompetenciaRepository competenciaDao) {
        this.empresaDao = empresaDao
        this.competenciaDao = competenciaDao
    }

    Empresa criarEmpresa(String nome, String email, String cnpj, String pais, String cep, String descricao) {
        if (empresaDao.buscarPorCnpj(cnpj) != null) {
            throw new RecursoDuplicadoException("CNPJ já cadastrado.")
        }

        Long empresaId = empresaDao.inserir(nome, cnpj, email, descricao, pais, cep)
        if (empresaId == null && empresaDao.buscarPorCnpj(cnpj) == null) {
            throw new OperacaoPersistenciaException("Não foi possível criar a empresa.")
        }

        return empresaDao.buscarPorCnpj(cnpj)
    }

    List<Empresa> listarEmpresas() {
        return empresaDao.listarTodos()
    }

    Empresa buscarEmpresa(String cnpj) {
        return empresaDao.buscarPorCnpj(cnpj)
    }
}
