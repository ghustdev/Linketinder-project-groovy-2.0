package repositories

import entities.Empresa

interface IEmpresaRepository {
    Long inserir(Empresa empresa)

    Empresa buscarPorCnpj(String cnpj)

    Empresa buscarPorId(Long id)

    List<Empresa> listarTodos()
}
