package dao

import entities.Candidato
import entities.Competencia
import repositories.ICandidatoRepository

import java.sql.Connection
import java.sql.Date
import java.sql.PreparedStatement

class CandidatoDao implements ICandidatoRepository {

    @Override
    Long inserir(String nome, String sobrenome, Date nascimento, String email, String cpf, String pais, String cep, String descricao, String formacao, String linkedin) {
        String sql = """\
INSERT INTO candidatos (nome, sobrenome, data_nascimento, email, cpf, pais, cep, descricao, formacao, linkedin)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
ON CONFLICT (cpf) DO NOTHING
RETURNING id
"""
        Connection conn = ConexaoDB.obterConexao()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, nome)
            stmt.setString(2, sobrenome)
            stmt.setDate(3, nascimento)
            stmt.setString(4, email)
            stmt.setString(5, cpf)
            stmt.setString(6, pais)
            stmt.setString(7, cep)
            stmt.setString(8, descricao)
            stmt.setString(9, formacao)
            stmt.setString(10, linkedin)
            def result = stmt.executeQuery()
            if (result.next()) {
                return result.getLong("id")
            }
            return null
        } finally {
            conn.close()
        }
    }

    @Override
    void adicionarCompetencias(Long candidatoId, List<Long> competenciaIds) {
        if (!candidatoId || !competenciaIds) return
        String sql = """\
INSERT INTO candidato_competencia (candidato_id, competencia_id)
VALUES (?, ?)
ON CONFLICT DO NOTHING
"""
        Connection conn = ConexaoDB.obterConexao()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            competenciaIds.each { competenciaId ->
                stmt.setLong(1, candidatoId)
                stmt.setLong(2, competenciaId)
                stmt.executeUpdate()
            }
            stmt.close()
        } finally {
            conn.close()
        }
    }

    @Override
    Candidato buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM candidatos WHERE cpf = ?"
        Connection conn = ConexaoDB.obterConexao()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, cpf)
            def result = stmt.executeQuery()
            if (result.next()) {
                Candidato candidato = construirCandidato(result)
                candidato.competencias = retornarCompetenciasPorCandidatoId(candidato.id)
                return candidato
            }
            return null
        } finally {
            conn.close()
        }
    }

    @Override
    List<Candidato> listarTodos() {
        String sql = "SELECT * FROM candidatos ORDER BY nome"
        List<Candidato> listaCandidatos = []
        Connection conn = ConexaoDB.obterConexao()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            def result = stmt.executeQuery()
            while (result.next()) {
                Candidato candidato = construirCandidato(result)
                candidato.competencias = retornarCompetenciasPorCandidatoId(candidato.id)
                listaCandidatos.add(candidato)
            }
            return listaCandidatos
        } finally {
            conn.close()
        }
    }

    static private List<Competencia> retornarCompetenciasPorCandidatoId(Long candidatoId) {
        String sql = """\
SELECT c.id, c.nome
FROM competencias c, candidato_competencia cc
WHERE cc.candidato_id = ?
  AND cc.competencia_id = c.id
ORDER BY c.nome
"""
        Connection conn = ConexaoDB.obterConexao()
        try {
            List<Competencia> competencias = []
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setLong(1, candidatoId)
            def result = stmt.executeQuery()
            while (result.next()) {
                competencias.add(Competencia.builder()
                        .id(result.getLong("id"))
                        .nome(result.getString("nome"))
                        .build())
            }
            result.close()
            stmt.close()
            return competencias
        } finally {
            conn.close()
        }
    }

    private static Candidato construirCandidato(def result) {
        return Candidato.builder()
                .id(result.getLong("id"))
                .nome(result.getString("nome"))
                .sobrenome(result.getString("sobrenome"))
                .email(result.getString("email"))
                .cpf(result.getString("cpf"))
                .pais(result.getString("pais"))
                .cep(result.getString("cep"))
                .descricao(result.getString("descricao"))
                .formacao(result.getString("formacao"))
                .linkedin(result.getString("linkedin"))
                .nascimento(result.getDate("data_nascimento"))
                .build()
    }
}
