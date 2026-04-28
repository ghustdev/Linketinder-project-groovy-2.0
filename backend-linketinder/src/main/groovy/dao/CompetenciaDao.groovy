package dao

import repositories.ICompetenciaRepository
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class CompetenciaDao implements ICompetenciaRepository {

    @Override
    Long inserir(String competencia) {
        String nome = competencia?.trim()
        if (!nome) return null

        String sql = """\
INSERT INTO competencias (nome)
VALUES (?)
ON CONFLICT (nome) DO UPDATE SET nome = EXCLUDED.nome
RETURNING id
        """

        Connection conn = ConexaoDB.obterConexao()
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome)
            ResultSet rs = stmt.executeQuery()
            try {
                if (rs.next()) return rs.getLong("id")
                return null
            } finally {
                rs.close()
            }
        }
    }
}
