package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import java.util.List;

public class CartItemRepository {
    private Dao<CartItem, Integer> cartItemDao;

    public CartItemRepository() {
        try {
            cartItemDao = DaoManager.createDao(Database.getConnectionSource(), CartItem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean salvar(CartItem item) {
        try {
            if (item == null) return false;
            int rows = cartItemDao.create(item);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remover(Usuario usuario, int anuncioId, String tipoAnuncio) {
        try {
            if (usuario == null) return false;
            List<CartItem> items = cartItemDao.queryBuilder()
                .where()
                .eq("usuario_email", usuario.getEmail())
                .and()
                .eq("anuncio_id", anuncioId)
                .and()
                .eq("tipo_anuncio", tipoAnuncio)
                .query();
            
            int deletedCount = 0;
            for (CartItem item : items) {
                deletedCount += cartItemDao.delete(item);
            }
            return deletedCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean limparParaUsuario(Usuario usuario) {
        try {
            if (usuario == null) return false;
            List<CartItem> items = cartItemDao.queryBuilder()
                .where()
                .eq("usuario_email", usuario.getEmail())
                .query();
            
            for (CartItem item : items) {
                cartItemDao.delete(item);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CartItem> buscarPorUsuario(Usuario usuario) {
        try {
            if (usuario == null) return List.of();
            return cartItemDao.queryBuilder()
                .where()
                .eq("usuario_email", usuario.getEmail())
                .query();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
