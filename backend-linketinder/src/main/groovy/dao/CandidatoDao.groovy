package dao

import entities.Candidato
import entities.Competencia
import repositories.ICandidatoRepository

import java.sql.Connection
import java.sql.PreparedStatement

class CandidatoDao implements ICandidatoRepository {

    @Override
    Long inserir(Candidato candidato) {
        String sql = """\
INSERT INTO candidatos (nome, sobrenome, data_nascimento, email, cpf, pais, cep, descricao, formacao, linkedin)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
ON CONFLICT (cpf) DO NOTHING
RETURNING id
"""
        Connection conn = ConexaoDB.obterConexao()
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, candidato.nome)
            stmt.setString(2, candidato.sobrenome)
            stmt.setDate(3, candidato.nascimento)
            stmt.setString(4, candidato.email)
            stmt.setString(5, candidato.cpf)
            stmt.setString(6, candidato.pais)
            stmt.setString(7, candidato.cep)
            stmt.setString(8, candidato.descricao)
            stmt.setString(9, candidato.formacao)
            stmt.setString(10, candidato.linkedin)
            def rs = stmt.executeQuery()
            try {
                if (rs.next()) {
                    return rs.getLong("id")
                }
                return null
            } finally {
                rs.close()
            }
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
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            competenciaIds.each { competenciaId ->
                stmt.setLong(1, candidatoId)
                stmt.setLong(2, competenciaId)
                stmt.executeUpdate()
            }
        }
    }

    @Override
    Candidato buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM candidatos WHERE cpf = ?"
        Connection conn = ConexaoDB.obterConexao()
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf)
            def rs = stmt.executeQuery()
            try {
                if (rs.next()) {
                    Candidato candidato = construirCandidato(rs)
                    candidato.competencias = retornarCompetenciasPorCandidatoId(candidato.id)
                    return candidato
                }
                return null
            } finally {
                rs.close()
            }
        }
    }

    @Override
    List<Candidato> listarTodos() {
        String sql = "SELECT * FROM candidatos ORDER BY nome"
        List<Candidato> listaCandidatos = []
        Connection conn = ConexaoDB.obterConexao()
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            def rs = stmt.executeQuery()
            try {
                while (rs.next()) {
                    Candidato candidato = construirCandidato(rs)
                    candidato.competencias = retornarCompetenciasPorCandidatoId(candidato.id)
                    listaCandidatos.add(candidato)
                }
                return listaCandidatos
            } finally {
                rs.close()
            }
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
        List<Competencia> competencias = []
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, candidatoId)
            def rs = stmt.executeQuery()
            try {
                while (rs.next()) {
                    competencias.add(Competencia.builder()
                            .id(rs.getLong("id"))
                            .nome(rs.getString("nome"))
                            .build())
                }
                return competencias
            } finally {
                rs.close()
            }
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
