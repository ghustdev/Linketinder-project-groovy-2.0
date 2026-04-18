package dao

import db.Db
import model.Skill
import model.Vacancy

import java.sql.Connection
import java.sql.PreparedStatement

class VacancyDao {
    CompanyDao companyDao

    VacancyDao(CompanyDao companyDao) {
        this.companyDao = companyDao
    }

    // Inserir vaga baseado na empresa no DB
    Long insert(Long companyId,
                String name,
                String description,
                String state,
                String city) {
        String sql = """\
INSERT INTO vagas (empresa_id, nome, descricao, estado, cidade)
VALUES (?, ?, ?, ?, ?)
RETURNING id
"""
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setLong(1, companyId)
            stmt.setString(2, name)
            stmt.setString(3, description)
            stmt.setString(4, state)
            stmt.setString(5, city)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                return rs.getLong("id")
            }
            return null
        } finally {
            conn.close()
        }
    }

    // Inserir competencias exigidas para a vaga
    void addSkills(Long vacancyId, List<Long> skillIds) {
        if (vacancyId == null || skillIds == null || skillIds.isEmpty()) {
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
            skillIds.each { id ->
                stmt.setLong(1, vacancyId)
                stmt.setLong(2, id)
                stmt.executeUpdate()
            }
            stmt.close()
        } finally {
            conn.close()
        }
    }

    Vacancy findById(Long id) {
        String sql = "SELECT * FROM vagas WHERE id = ?"
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setLong(1, id)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                Vacancy vacancy = mapVacancy(rs)
                vacancy.requiredSkills = listSkillsByVacancyId(conn, vacancy.id)
                vacancy.company = companyDao.findById(vacancy.companyId)
                return vacancy
            }
            return null
        } finally {
            conn.close()
        }
    }

    // Evitar duplicidade
    Vacancy findByCompanyAndName(Long companyId, String name) {
        String sql = "SELECT * FROM vagas WHERE empresa_id = ? AND nome = ?"
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setLong(1, companyId)
            stmt.setString(2, name)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                Vacancy vacancy = mapVacancy(rs)
                vacancy.requiredSkills = listSkillsByVacancyId(conn, vacancy.id)
                vacancy.company = companyDao.findById(vacancy.companyId)
                return vacancy
            }
            return null
        } finally {
            conn.close()
        }
    }

    List<Vacancy> listAll() {
        String sql = "SELECT * FROM vagas ORDER BY id"
        List<Vacancy> list = []
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            def rs = stmt.executeQuery()
            while (rs.next()) {
                Vacancy vacancy = mapVacancy(rs)
                vacancy.requiredSkills = listSkillsByVacancyId(conn, vacancy.id)
                vacancy.company = companyDao.findById(vacancy.companyId)
                list.add(vacancy)
            }
            return list
        } finally {
            conn.close()
        }
    }

    // Função auxiliar
    private List<Skill> listSkillsByVacancyId(Connection conn, Long vacancyId) {
        String sql = """\
SELECT comp.id, comp.nome
FROM competencias comp, vaga_competencia vc
WHERE vc.vaga_id = ?
  AND vc.competencia_id = comp.id
ORDER BY comp.nome
"""
        List<Skill> skills = []
        PreparedStatement stmt = conn.prepareStatement(sql)
        stmt.setLong(1, vacancyId)
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

    private static Vacancy mapVacancy(def rs) {
        return Vacancy.builder()
                .id(rs.getLong("id"))
                .companyId(rs.getLong("empresa_id"))
                .name(rs.getString("nome"))
                .description(rs.getString("descricao"))
                .state(rs.getString("estado"))
                .city(rs.getString("cidade"))
                .build()
    }
}
