package dao

import entities.Empresa
import repositories.IEmpresaRepository

import java.sql.Connection
import java.sql.PreparedStatement

class EmpresaDao implements IEmpresaRepository {

    @Override
    Long inserir(String nome, String cnpj, String email, String descricao, String pais, String cep) {
        String sql = """\
INSERT INTO empresas (nome, cnpj, email, descricao, pais, cep)
VALUES (?, ?, ?, ?, ?, ?)
ON CONFLICT (cnpj) DO NOTHING
RETURNING id
"""
        Connection conn = ConexaoDB.obterConexao()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, nome)
            stmt.setString(2, cnpj)
            stmt.setString(3, email)
            stmt.setString(4, descricao)
            stmt.setString(5, pais)
            stmt.setString(6, cep)
            def rs = stmt.executeQuery()
            if (rs.next()) {
                return rs.getLong("id")
            }
            return null
        } finally {
            conn.close()
        }
    }

    @Override
    Empresa buscarPorCnpj(String cnpj) {
        String sql = "SELECT * FROM empresas WHERE cnpj = ?"
        Connection conn = ConexaoDB.obterConexao()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, cnpj)
            def rs = stmt.executeQuery()
            if (rs.next()) return construirEmpresa(rs)
            return null
        } finally {
            conn.close()
        }
    }

    @Override
    Empresa buscarPorId(Long id) {
        String sql = "SELECT * FROM empresas WHERE id = ?"
        Connection conn = ConexaoDB.obterConexao()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setLong(1, id)
            def rs = stmt.executeQuery()
            if (rs.next()) return construirEmpresa(rs)
            return null
        } finally {
            conn.close()
        }
    }

    @Override
    List<Empresa> listarTodos() {
        String sql = "SELECT * FROM empresas ORDER BY nome"
        List<Empresa> lista = []
        Connection conn = ConexaoDB.obterConexao()
        try {
            PreparedStatement stmt = conn.prepareStatement(sql)
            def rs = stmt.executeQuery()
            while (rs.next()) {
                lista.add(construirEmpresa(rs))
            }
            return lista
        } finally {
            conn.close()
        }
    }

    private static Empresa construirEmpresa(def rs) {
        return Empresa.builder()
                .id(rs.getLong("id"))
                .nome(rs.getString("nome"))
                .email(rs.getString("email"))
                .cnpj(rs.getString("cnpj"))
                .pais(rs.getString("pais"))
                .cep(rs.getString("cep"))
                .descricao(rs.getString("descricao"))
                .build()
    }
}
