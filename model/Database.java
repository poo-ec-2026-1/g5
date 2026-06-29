package model;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:tradelibrary.db";
    private static ConnectionSource connectionSource;

    public static synchronized ConnectionSource getConnectionSource() throws SQLException {
        if (connectionSource == null) {
            connectionSource = new JdbcConnectionSource(DB_URL);
            // Cria a tabela de usuários se ela não existir
            TableUtils.createTableIfNotExists(connectionSource, Usuario.class);
            // Cria as tabelas de livros e anúncios
            TableUtils.createTableIfNotExists(connectionSource, Livro.class);
            TableUtils.createTableIfNotExists(connectionSource, AnuncioVenda.class);
            TableUtils.createTableIfNotExists(connectionSource, AnuncioTroca.class);
            // Cria a tabela de itens do carrinho
            TableUtils.createTableIfNotExists(connectionSource, CartItem.class);
        }
        return connectionSource;
    }

    public static synchronized void close() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connectionSource = null;
            }
        }
    }
}
