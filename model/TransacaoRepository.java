package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;

public class TransacaoRepository {
    private Dao<Transacao, Integer> transacaoDao;

    public TransacaoRepository() {
        try {
            transacaoDao = DaoManager.createDao(Database.getConnectionSource(), Transacao.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registrar(Transacao transacao) {
        try {
            if (transacao == null) return false;
            int rows = transacaoDao.create(transacao);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
