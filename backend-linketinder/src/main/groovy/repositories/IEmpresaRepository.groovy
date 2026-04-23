package repositories

import entities.Empresa

interface IEmpresaRepository {
    Long inserir(String nome, String cnpj, String email, String descricao, String pais, String cep)

    Empresa buscarPorCnpj(String cnpj)

    Empresa buscarPorId(Long id)

    List<Empresa> listarTodos()
}
