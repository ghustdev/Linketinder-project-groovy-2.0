package dao

import db.Db

import java.sql.Connection
import java.sql.PreparedStatement

class SkillDao {

    // Inserir as competencias no DB
    void insert(List<String> skills) {
        if (skills == null || skills.isEmpty()) {
            return
        }
        String sql = "INSERT INTO competencias (nome) VALUES (?) ON CONFLICT DO NOTHING"
        Connection conn = Db.getConnection()
        try {
            // PreparedStatement ou ResultSet client = conn.createStatement().executeQuery(sql);
            PreparedStatement stmt = conn.prepareStatement(sql)
            skills.each { s ->
                String skill = s?.trim()
                if (skill) {
                    stmt.setString(1, skill)
                    stmt.executeUpdate()
                }
            }
            stmt.close()
        } finally {
            conn.close()
        }
    }

    // Retornar IDs das competencias para adicionar na tabela candidato_competencia / vaga_competencia
    Map<String, Long> findIdsByNames(List<String> skills) {
        Map<String, Long> ids = [:]
        if (skills == null || skills.isEmpty()) {
            return ids
        }
        String sql = "SELECT id FROM competencias WHERE nome = ?"
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            skills.each { s ->
                String skill = s?.trim()
                if (skill) {
                    stmt.setString(1, skill)
                    def rs = stmt.executeQuery()
                    if (rs.next()) {
                        ids[skill] = rs.getLong("id")
                    }
                    rs.close()
                }
            }
            stmt.close()
        } finally {
            conn.close()
        }
        return ids
    }
}
