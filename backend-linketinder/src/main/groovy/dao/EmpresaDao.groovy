package dao

import db.Db
import model.Empresa

import java.sql.Connection
import java.sql.PreparedStatement

class EmpresaDao {

    // Inserir Empresa no DB
    Long insert(String nome,
                String cnpj,
                String email,
                String descricao,
                String pais,
                String cep,
                String senhaHash) {
        String sql = """\
INSERT INTO empresas (nome, cnpj, email, descricao, pais, cep, senha_hash)
VALUES (?, ?, ?, ?, ?, ?, ?)
ON CONFLICT (cnpj) DO NOTHING
RETURNING id
"""
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, nome)
            stmt.setString(2, cnpj)
            stmt.setString(3, email)
            stmt.setString(4, descricao)
            stmt.setString(5, pais)
            stmt.setString(6, cep)
            stmt.setString(7, senhaHash)
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
    Empresa findByCnpj(String cnpj) {
        String sql = "SELECT * FROM empresas WHERE cnpj = ?"
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, cnpj)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                return mapEmpresa(rs)
            }
            return null
        } finally {
            conn.close()
        }
    }

    Empresa findByEmail(String email) {
        String sql = "SELECT * FROM empresas WHERE email = ?"
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, email)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                return mapEmpresa(rs)
            }
            return null
        } finally {
            conn.close()
        }
    }

    Empresa findById(Long id) {
        String sql = "SELECT * FROM empresas WHERE id = ?"
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setLong(1, id)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                return mapEmpresa(rs)
            }
            return null
        } finally {
            conn.close()
        }
    }

    List<Empresa> listAll() {
        String sql = "SELECT * FROM empresas ORDER BY nome"
        List<Empresa> list = []
        Connection conn = Db.getConnection()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            def rs = stmt.executeQuery()
            while (rs.next()) {
                list.add(mapEmpresa(rs))
            }
            return list
        } finally {
            conn.close()
        }
    }

    private static Empresa mapEmpresa(def rs) {
        return Empresa.builder()
                .id(rs.getLong("id"))
                .nome(rs.getString("nome"))
                .email(rs.getString("email"))
                .cnpj(rs.getString("cnpj"))
                .pais(rs.getString("pais"))
                .cep(rs.getString("cep"))
                .descricao(rs.getString("descricao"))
                .senha_hash(rs.getString("senha_hash"))
                .build()
    }
}
