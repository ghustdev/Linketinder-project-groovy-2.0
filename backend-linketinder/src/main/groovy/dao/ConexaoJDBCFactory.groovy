package dao

import org.postgresql.Driver

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ConexaoJDBCFactory implements IConexaoFactory {
    @Override
    Connection criarConexao(String url, String usuario, String senha) throws SQLException {
        try {
            Properties props = new Properties()
            props.setProperty("user", usuario)
            props.setProperty("password", senha)

            Driver driverPostgres = new Driver()

            return driverPostgres.connect(url, props)

        } catch (Exception e) {
            throw new SQLException("Falha ao obter conexão com o banco no ambiente Web: ${e.message}", e)
        }
//        return DriverManager.getConnection(url, usuario, senha)
    }

}
