package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnuncioRepository {
    private Dao<Livro, Integer> livroDao;
    private Dao<AnuncioVenda, Integer> anuncioVendaDao;
    private Dao<AnuncioTroca, Integer> anuncioTrocaDao;

    public AnuncioRepository() {
        try {
            livroDao = DaoManager.createDao(Database.getConnectionSource(), Livro.class);
            anuncioVendaDao = DaoManager.createDao(Database.getConnectionSource(), AnuncioVenda.class);
            anuncioTrocaDao = DaoManager.createDao(Database.getConnectionSource(), AnuncioTroca.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean cadastrarAnuncioVenda(AnuncioVenda anuncio) {
        try {
            if (anuncio.getLivro() != null) {
                livroDao.create(anuncio.getLivro());
            }
            int rows = anuncioVendaDao.create(anuncio);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cadastrarAnuncioTroca(AnuncioTroca anuncio) {
        try {
            if (anuncio.getLivro() != null) {
                livroDao.create(anuncio.getLivro());
            }
            int rows = anuncioTrocaDao.create(anuncio);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Anuncio> listarTodos() {
        List<Anuncio> anuncios = new ArrayList<>();
        try {
            if (anuncioVendaDao != null) {
                anuncios.addAll(anuncioVendaDao.queryForAll());
            }
            if (anuncioTrocaDao != null) {
                anuncios.addAll(anuncioTrocaDao.queryForAll());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return anuncios;
    }

    public boolean atualizarAnuncio(Anuncio anuncio) {
        try {
            int rows = 0;
            if (anuncio instanceof AnuncioVenda) {
                rows = anuncioVendaDao.update((AnuncioVenda) anuncio);
            } else if (anuncio instanceof AnuncioTroca) {
                rows = anuncioTrocaDao.update((AnuncioTroca) anuncio);
            }
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removerAnuncio(Anuncio anuncio) {
        try {
            int rows = 0;
            if (anuncio instanceof AnuncioVenda) {
                rows = anuncioVendaDao.delete((AnuncioVenda) anuncio);
            } else if (anuncio instanceof AnuncioTroca) {
                rows = anuncioTrocaDao.delete((AnuncioTroca) anuncio);
            }
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public AnuncioVenda buscarVendaPorId(int id) {
        try {
            return anuncioVendaDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public AnuncioTroca buscarTrocaPorId(int id) {
        try {
            return anuncioTrocaDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
