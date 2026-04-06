package db

import java.sql.Connection
import java.sql.DriverManager

class Db {
    static Connection getConnection() {
        Properties props = new Properties()

        try (FileInputStream fis = new FileInputStream("linketinder.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Arquivo db.properties não encontrado! " + e.getMessage());
            return;
        }

        String url = props.getProperty("DB_URL")
        String user = props.getProperty("DB_USER")
        String password = props.getProperty("DB_PASSWORD")
        return DriverManager.getConnection(url, user, password)
    }
}
