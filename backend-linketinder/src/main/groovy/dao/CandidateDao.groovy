package dao

import db.Db
import model.Candidate
import model.Skill

import java.sql.Connection
import java.sql.Date
import java.sql.PreparedStatement

class CandidateDao {

    // Função de inserir candidato ao DB
    Long insert(String firstName,
                String lastName,
                Date birthDate,
                String email,
                String cpf,
                String country,
                String cep,
                String description,
                String passwordHash) {
        String sql = """\
INSERT INTO candidatos (nome, sobrenome, data_nascimento, email, cpf, pais, cep, descricao, senha_hash)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
ON CONFLICT (cpf) DO NOTHING
RETURNING id
"""
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, firstName)
            stmt.setString(2, lastName)
            stmt.setDate(3, birthDate)
            stmt.setString(4, email)
            stmt.setString(5, cpf)
            stmt.setString(6, country)
            stmt.setString(7, cep)
            stmt.setString(8, description)
            stmt.setString(9, passwordHash)
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
    void addSkills(Long candidateId, List<Long> skillIds) {
        if (candidateId == null || skillIds == null || skillIds.isEmpty()) {
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
            skillIds.each { id ->
                stmt.setLong(1, candidateId)
                stmt.setLong(2, id)
                stmt.executeUpdate()
            }
            stmt.close()
        } finally {
            conn.close()
        }
    }

    // Procurar Candidato pelo CPF para validações e retorno
    Candidate findByCpf(String cpf) {
        String sql = "SELECT * FROM candidatos WHERE cpf = ?"
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, cpf)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                Candidate candidate = mapCandidate(rs)
                candidate.skills = listSkillsByCandidateId(conn, candidate.id)
                return candidate
            }
            return null
        } finally {
            conn.close()
        }
    }

    // Listar todos os candidatos
    List<Candidate> listAll() {
        String sql = "SELECT * FROM candidatos ORDER BY nome"
        List<Candidate> list = []
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            def rs = stmt.executeQuery()
            while (rs.next()) {
                Candidate candidate = mapCandidate(rs)
                candidate.skills = listSkillsByCandidateId(conn, candidate.id)
                list.add(candidate)
            }
            return list
        } finally {
            conn.close()
        }
    }

    private List<Skill> listSkillsByCandidateId(Connection conn, Long candidateId) {
        String sql = """\
SELECT comp.id, comp.nome
FROM competencias comp, candidato_competencia cc
WHERE cc.candidato_id = ?
  AND cc.competencia_id = comp.id
ORDER BY comp.nome
"""
        List<Skill> skills = []
        PreparedStatement stmt = conn.prepareStatement(sql)
        stmt.setLong(1, candidateId)
        def rs = stmt.executeQuery()
        while (rs.next()) {
            skills.add(Skill.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("nome"))
                    .build())
        }
        rs.close()
        stmt.close()
        return skills
    }

    // Criação antiga usando Builder class
    private static Candidate mapCandidate(def rs) {
        return Candidate.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("nome"))
                .lastName(rs.getString("sobrenome"))
                .email(rs.getString("email"))
                .cpf(rs.getString("cpf"))
                .country(rs.getString("pais"))
                .cep(rs.getString("cep"))
                .description(rs.getString("descricao"))
                .password(rs.getString("senha_hash"))
                .birth(rs.getDate("data_nascimento"))
                .build()
    }
}
