package dao

import db.Db
import model.Company

import java.sql.Connection
import java.sql.PreparedStatement

class CompanyDao {

    // Inserir Empresa no DB
    Long insert(String name,
                String cnpj,
                String email,
                String description,
                String country,
                String cep,
                String passwordHash) {
        String sql = """\
INSERT INTO empresas (nome, cnpj, email, descricao, pais, cep, senha_hash)
VALUES (?, ?, ?, ?, ?, ?, ?)
ON CONFLICT (cnpj) DO NOTHING
RETURNING id
"""
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, name)
            stmt.setString(2, cnpj)
            stmt.setString(3, email)
            stmt.setString(4, description)
            stmt.setString(5, country)
            stmt.setString(6, cep)
            stmt.setString(7, passwordHash)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                return rs.getLong("id")
            }
            return null
        } finally {
            conn.close()
        }
    }

    // Encontrar empresa pelo CNPJ para validações e retornos
    Company findByCnpj(String cnpj) {
        String sql = "SELECT * FROM empresas WHERE cnpj = ?"
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, cnpj)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                return mapCompany(rs)
            }
            return null
        } finally {
            conn.close()
        }
    }

    Company findByEmail(String email) {
        String sql = "SELECT * FROM empresas WHERE email = ?"
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, email)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                return mapCompany(rs)
            }
            return null
        } finally {
            conn.close()
        }
    }

    Company findById(Long id) {
        String sql = "SELECT * FROM empresas WHERE id = ?"
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setLong(1, id)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                return mapCompany(rs)
            }
            return null
        } finally {
            conn.close()
        }
    }

    List<Company> listAll() {
        String sql = "SELECT * FROM empresas ORDER BY nome"
        List<Company> list = []
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            def rs = stmt.executeQuery()
            while (rs.next()) {
                list.add(mapCompany(rs))
            }
            return list
        } finally {
            conn.close()
        }
    }

    private static Company mapCompany(def rs) {
        return Company.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("nome"))
                .email(rs.getString("email"))
                .cnpj(rs.getString("cnpj"))
                .country(rs.getString("pais"))
                .cep(rs.getString("cep"))
                .description(rs.getString("descricao"))
                .password(rs.getString("senha_hash"))
                .build()
    }
}
