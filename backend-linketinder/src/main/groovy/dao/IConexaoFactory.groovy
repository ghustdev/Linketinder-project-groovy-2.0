package dao

import java.sql.Connection

interface IConexaoFactory {
    Connection criarConexao(String url, String usuario, String senha)
}
