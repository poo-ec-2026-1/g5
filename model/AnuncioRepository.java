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
            inicializarDemoAnuncios();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void inicializarDemoAnuncios() {
        try {
            if (anuncioVendaDao != null && anuncioVendaDao.countOf() == 0 && 
                anuncioTrocaDao != null && anuncioTrocaDao.countOf() == 0) {
                
                Dao<Usuario, String> usuarioDao = DaoManager.createDao(Database.getConnectionSource(), Usuario.class);
                Usuario demoUser = usuarioDao.queryForId("guilhermew@email.com");
                if (demoUser == null) {
                    demoUser = new Usuario("Guilherme William", "guilhermew@email.com", "(00) 00000-0000", "senha123");
                    usuarioDao.create(demoUser);
                }

                Livro livro1 = new Livro("Código Limpo", "Robert C. Martin", "9788576082675", "Seminovo");
                livroDao.create(livro1);

                AnuncioVenda venda = new AnuncioVenda(livro1, demoUser, 85.00, "Livro essencial para desenvolvedores Java.");
                anuncioVendaDao.create(venda);

                Livro livro2 = new Livro("Design Patterns", "Erich Gamma", "9788573076103", "Usado");
                livroDao.create(livro2);

                AnuncioTroca troca = new AnuncioTroca(livro2, demoUser, "Refactoring por Martin Fowler", "Clássico em bom estado.");
                anuncioTrocaDao.create(troca);
            }
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
}
