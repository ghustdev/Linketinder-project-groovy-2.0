package dao

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ConexaoJDBCFactory implements IConexaoFactory {
    @Override
    Connection criarConexao(String url, String usuario, String senha) throws SQLException {
        return DriverManager.getConnection(url, usuario, senha)
    }
}
