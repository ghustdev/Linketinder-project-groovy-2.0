package dao

import entities.Competencia
import entities.Vaga
import repositories.IVagaRepository

import java.sql.Connection
import java.sql.PreparedStatement

class VagaDao implements IVagaRepository {
    EmpresaDao empresaDao

    VagaDao(EmpresaDao empresaDao) {
        this.empresaDao = empresaDao
    }

    @Override
    Long inserir(Vaga vaga) {
        String sql = """\
INSERT INTO vagas (empresa_id, nome, descricao, estado, cidade)
VALUES (?, ?, ?, ?, ?)
RETURNING id
"""
        Connection conn = ConexaoDB.obterConexao()
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            Long empresaId = vaga.empresaId ?: vaga.empresa?.id
            stmt.setLong(1, empresaId)
            stmt.setString(2, vaga.titulo)
            stmt.setString(3, vaga.descricao)
            stmt.setString(4, vaga.estado)
            stmt.setString(5, vaga.cidade)
            def rs = stmt.executeQuery()
            try {
                if (rs.next()) return rs.getLong("id")
                return null
            } finally {
                rs.close()
            }
        }
    }

    @Override
    void adicionarCompetencias(Long vagaId, List<Long> competenciaIds) {
        if (!vagaId || !competenciaIds) return
        String sql = """\
INSERT INTO vaga_competencia (vaga_id, competencia_id)
VALUES (?, ?)
ON CONFLICT DO NOTHING
"""
        Connection conn = ConexaoDB.obterConexao()
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            competenciaIds.each { id ->
                stmt.setLong(1, vagaId)
                stmt.setLong(2, id)
                stmt.executeUpdate()
            }
        }
    }

    @Override
    Vaga buscarPorId(Long id) {
        String sql = "SELECT * FROM vagas WHERE id = ?"
        Connection conn = ConexaoDB.obterConexao()
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id)
            def rs = stmt.executeQuery()
            try {
                if (rs.next()) {
                    Vaga vaga = construirVaga(rs)
                    vaga.competenciasRequeridas = retornarCompetenciasPorVagaId(vaga.id)
                    vaga.empresa = empresaDao.buscarPorId(vaga.empresaId)
                    return vaga
                }
                return null
            } finally {
                rs.close()
            }
        }
    }

    @Override
    Vaga buscarPorEmpresaENome(Long empresaId, String nome) {
        String sql = "SELECT * FROM vagas WHERE empresa_id = ? AND nome = ?"
        Connection conn = ConexaoDB.obterConexao()
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, empresaId)
            stmt.setString(2, nome)
            def rs = stmt.executeQuery()
            try {
                if (rs.next()) {
                    Vaga vaga = construirVaga(rs)
                    vaga.competenciasRequeridas = retornarCompetenciasPorVagaId(vaga.id)
                    vaga.empresa = empresaDao.buscarPorId(vaga.empresaId)
                    return vaga
                }
                return null
            } finally {
                rs.close()
            }
        }
    }

    @Override
    List<Vaga> listarTodos() {
        String sql = "SELECT * FROM vagas ORDER BY id"
        List<Vaga> lista = []
        Connection conn = ConexaoDB.obterConexao()
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            def rs = stmt.executeQuery()
            try {
                while (rs.next()) {
                    Vaga vaga = construirVaga(rs)
                    vaga.competenciasRequeridas = retornarCompetenciasPorVagaId(vaga.id)
                    vaga.empresa = empresaDao.buscarPorId(vaga.empresaId)
                    lista.add(vaga)
                }
                return lista
            } finally {
                rs.close()
            }
        }
    }

    private List<Competencia> retornarCompetenciasPorVagaId(Long vagaId) {
        String sql = """\
	SELECT c.id, c.nome
	FROM competencias c, vaga_competencia vc
	WHERE vc.vaga_id = ?
	  AND vc.competencia_id = c.id
	ORDER BY c.nome
	"""

        Connection conn = ConexaoDB.obterConexao()
        List<Competencia> competencias = []
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, vagaId)
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

    private static Vaga construirVaga(def rs) {
        return Vaga.builder()
                .id(rs.getLong("id"))
                .empresaId(rs.getLong("empresa_id"))
                .titulo(rs.getString("nome"))
                .descricao(rs.getString("descricao"))
                .estado(rs.getString("estado"))
                .cidade(rs.getString("cidade"))
                .build()
    }
}
