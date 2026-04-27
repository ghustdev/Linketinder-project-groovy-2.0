package dao

import exceptions.OperacaoPersistenciaException

import java.sql.Connection
import java.sql.SQLException

class ConexaoDB {
    private static final String ARQUIVO_PROPRIEDADES = "linketinder.properties"

    private static Properties props
    private static Connection conexao
    private static IConexaoFactory conexaoFactory = new ConexaoJDBCFactory()

    static Connection obterConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) return conexao

            if (props == null) {
                props = new Properties()
                try (FileInputStream fis = new FileInputStream(ARQUIVO_PROPRIEDADES)) {
                    props.load(fis)
                } catch (IOException e) {
                    throw new OperacaoPersistenciaException("Arquivo ${ARQUIVO_PROPRIEDADES} não encontrado ou ilegível: ${e.message}")
                }
            }

            String url = props.getProperty("DB_URL")
            String usuario = props.getProperty("DB_USER")
            String senha = props.getProperty("DB_PASSWORD")

            if (!url) throw new OperacaoPersistenciaException("DB_URL não configurado em ${ARQUIVO_PROPRIEDADES}")
            if (!usuario) throw new OperacaoPersistenciaException("DB_USER não configurado em ${ARQUIVO_PROPRIEDADES}")
            if (senha == null) throw new OperacaoPersistenciaException("DB_PASSWORD não configurado em ${ARQUIVO_PROPRIEDADES}")

            conexao = conexaoFactory.criarConexao(url, usuario, senha)
            return conexao
        } catch (SQLException e) {
            throw new OperacaoPersistenciaException("Falha ao obter conexão com o banco: ${e.message}")
        }
    }

    static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close()
            }
        } catch (SQLException e) {
            throw new OperacaoPersistenciaException("Falha ao fechar conexão com o banco: ${e.message}")
        } finally {
            conexao = null
        }
    }
}
