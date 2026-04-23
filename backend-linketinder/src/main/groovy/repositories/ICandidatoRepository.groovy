package repositories

import entities.Candidato

import java.sql.Date

interface ICandidatoRepository {
    Long inserir(String nome, String sobrenome, Date nascimento, String email, String cpf, String pais, String cep, String descricao, String formacao, String linkedin)

    void adicionarCompetencias(Long candidatoId, List<Long> competenciaIds)

    Candidato buscarPorCpf(String cpf)

    List<Candidato> listarTodos()
}
