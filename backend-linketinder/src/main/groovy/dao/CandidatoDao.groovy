package dao

import db.Db
import model.Candidato

import java.sql.Connection
import java.sql.Date
import java.sql.PreparedStatement

class CandidatoDao {

    // Função de inserir candidato ao DB
    Long insert(String nome,
                String sobrenome,
                Date dataNascimento,
                String email,
                String cpf,
                String pais,
                String cep,
                String descricao,
                String senhaHash) {
        String sql = """\
INSERT INTO candidatos (nome, sobrenome, data_nascimento, email, cpf, pais, cep, descricao, senha_hash)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
ON CONFLICT (cpf) DO NOTHING
RETURNING id
"""
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, nome)
            stmt.setString(2, sobrenome)
            stmt.setDate(3, dataNascimento)
            stmt.setString(4, email)
            stmt.setString(5, cpf)
            stmt.setString(6, pais)
            stmt.setString(7, cep)
            stmt.setString(8, descricao)
            stmt.setString(9, senhaHash)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                return rs.getLong("id")
            }
            return null
        } finally {
            conn.close()
        }
    }

    // Adicionar competencias na tebala candidato_competencia N:N
    void addCompetencias(Long candidatoId, List<Long> competenciaIds) {
        if (candidatoId == null || competenciaIds == null || competenciaIds.isEmpty()) {
            return
        }
        String sql = """\
INSERT INTO candidato_competencia (candidato_id, competencia_id)
VALUES (?, ?)
ON CONFLICT DO NOTHING
"""
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            competenciaIds.each { id ->
                stmt.setLong(1, candidatoId)
                stmt.setLong(2, id)
                stmt.executeUpdate()
            }
            stmt.close()
        } finally {
            conn.close()
        }
    }

    // Procurar Candidato pelo CPF para validações e retorno
    Candidato findByCpf(String cpf) {
        String sql = "SELECT * FROM candidatos WHERE cpf = ?"
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, cpf)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                Candidato c = mapCandidato(rs)
                c.skills = listSkillsByCandidatoId(conn, c.id)
                return c
            }
            return null
        } finally {
            conn.close()
        }
    }

    // Listar todos os candidatos
    List<Candidato> listAll() {
        String sql = "SELECT * FROM candidatos ORDER BY nome"
        List<Candidato> list = []
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            def rs = stmt.executeQuery()
            while (rs.next()) {
                Candidato c = mapCandidato(rs)
                c.skills = listSkillsByCandidatoId(conn, c.id)
                list.add(c)
            }
            return list
        } finally {
            conn.close()
        }
    }

    private List<String> listSkillsByCandidatoId(Connection conn, Long candidatoId) {
        String sql = """\
SELECT comp.nome
FROM competencias comp, candidato_competencia cc
WHERE cc.candidato_id = ?
  AND cc.competencia_id = comp.id
ORDER BY comp.nome
"""
        List<String> skills = []
        PreparedStatement stmt = conn.prepareStatement(sql)
        stmt.setLong(1, candidatoId)
        def rs = stmt.executeQuery()
        while (rs.next()) {
            skills.add(rs.getString("nome"))
        }
        rs.close()
        stmt.close()
        return skills
    }

    // Criação antiga usando Builder class
    private static Candidato mapCandidato(def rs) {
        return Candidato.builder()
                .id(rs.getLong("id"))
                .nome(rs.getString("nome"))
                .sobrenome(rs.getString("sobrenome"))
                .email(rs.getString("email"))
                .cpf(rs.getString("cpf"))
                .pais(rs.getString("pais"))
                .cep(rs.getString("cep"))
                .descricao(rs.getString("descricao"))
                .senha_hash(rs.getString("senha_hash"))
                .data_nascimento(rs.getDate("data_nascimento"))
                .build()
    }
}
