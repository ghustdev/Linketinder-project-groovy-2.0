package dao

import db.Db
import model.Vaga

import java.sql.Connection
import java.sql.PreparedStatement

class VagaDao {
    EmpresaDao empresaDao

    VagaDao(EmpresaDao empresaDao) {
        this.empresaDao = empresaDao
    }

    Long insert(Long empresaId,
                String nome,
                String descricao,
                String estado,
                String cidade) {
        String sql = """\
INSERT INTO vagas (empresa_id, nome, descricao, estado, cidade)
VALUES (?, ?, ?, ?, ?)
RETURNING id
"""
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setLong(1, empresaId)
            stmt.setString(2, nome)
            stmt.setString(3, descricao)
            stmt.setString(4, estado)
            stmt.setString(5, cidade)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                return rs.getLong("id")
            }
            return null
        } finally {
            conn.close()
        }
    }

    void addCompetencias(Long vagaId, List<Long> competenciaIds) {
        if (vagaId == null || competenciaIds == null || competenciaIds.isEmpty()) {
            return
        }
        String sql = """\
INSERT INTO vaga_competencia (vaga_id, competencia_id)
VALUES (?, ?)
ON CONFLICT DO NOTHING
"""
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            competenciaIds.each { id ->
                stmt.setLong(1, vagaId)
                stmt.setLong(2, id)
                stmt.executeUpdate()
            }
            stmt.close()
        } finally {
            conn.close()
        }
    }

    Vaga findById(Long id) {
        String sql = "SELECT * FROM vagas WHERE id = ?"
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setLong(1, id)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                Vaga v = mapVaga(rs)
                v.skillsRequests = listSkillsByVagaId(conn, v.id)
                v.empresa = empresaDao.findById(v.empresa_id)
                return v
            }
            return null
        } finally {
            conn.close()
        }
    }

    Vaga findByEmpresaAndNome(Long empresaId, String nome) {
        String sql = "SELECT * FROM vagas WHERE empresa_id = ? AND nome = ?"
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setLong(1, empresaId)
            stmt.setString(2, nome)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                Vaga v = mapVaga(rs)
                v.skillsRequests = listSkillsByVagaId(conn, v.id)
                v.empresa = empresaDao.findById(v.empresa_id)
                return v
            }
            return null
        } finally {
            conn.close()
        }
    }

    List<Vaga> listAll() {
        String sql = "SELECT * FROM vagas ORDER BY id"
        List<Vaga> list = []
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            def rs = stmt.executeQuery()
            while (rs.next()) {
                Vaga v = mapVaga(rs)
                v.skillsRequests = listSkillsByVagaId(conn, v.id)
                v.empresa = empresaDao.findById(v.empresa_id)
                list.add(v)
            }
            return list
        } finally {
            conn.close()
        }
    }

    private List<String> listSkillsByVagaId(Connection conn, Long vagaId) {
        String sql = """\
SELECT comp.nome
FROM competencias comp, vaga_competencia vc
WHERE vc.vaga_id = ?
  AND vc.competencia_id = comp.id
ORDER BY comp.nome
"""
        List<String> skills = []
        PreparedStatement stmt = conn.prepareStatement(sql)
        stmt.setLong(1, vagaId)
        def rs = stmt.executeQuery()
        while (rs.next()) {
            skills.add(rs.getString("nome"))
        }
        rs.close()
        stmt.close()
        return skills
    }

    private static Vaga mapVaga(def rs) {
        return Vaga.builder()
                .id(rs.getLong("id"))
                .empresa_id(rs.getLong("empresa_id"))
                .nome(rs.getString("nome"))
                .descricao(rs.getString("descricao"))
                .estado(rs.getString("estado"))
                .cidade(rs.getString("cidade"))
                .build()
    }
}
