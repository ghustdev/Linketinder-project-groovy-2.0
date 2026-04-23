package dao

import java.sql.Connection
import java.sql.DriverManager

class ConexaoDB {
    static Connection obterConexao() {
        Properties props = new Properties()
        try (FileInputStream fis = new FileInputStream("linketinder.properties")) {
            props.load(fis)
        } catch (IOException e) {
            System.err.println("Arquivo linketinder.properties não encontrado! " + e.getMessage())
            return null
        }
        String url = props.getProperty("DB_URL")
        String usuario = props.getProperty("DB_USER")
        String senha = props.getProperty("DB_PASSWORD")
        return DriverManager.getConnection(url, usuario, senha)
    }
}
