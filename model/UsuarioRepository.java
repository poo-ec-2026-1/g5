package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import java.util.List;

public class UsuarioRepository {
    private Dao<Usuario, String> usuarioDao;

    public UsuarioRepository() {
        try {
            usuarioDao = DaoManager.createDao(Database.getConnectionSource(), Usuario.class);
            inicializarDemoUser();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void inicializarDemoUser() {
        try {
            String demoEmail = "guilhermew@email.com";
            if (usuarioDao != null && usuarioDao.queryForId(demoEmail) == null) {
               Usuario demoUser = new Usuario("Guilherme William", demoEmail, "(00) 00000-0000", controller.SecurityUtils.hashSenha("senha123"));
                usuarioDao.create(demoUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean cadastrar(Usuario usuario) {
        try {
            if (emailExiste(usuario.getEmail())) {
                return false;
            }
            int rows = usuarioDao.create(usuario);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExiste(String email) {
        try {
            if (email == null) return false;
            return usuarioDao.queryForId(email) != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario buscarPorEmail(String email) {
        try {
            if (email == null) return null;
            return usuarioDao.queryForId(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Usuario> listarTodos() {
        try {
            return usuarioDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean atualizar(Usuario usuario) {
        try {
            int rows = usuarioDao.update(usuario);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remover(Usuario usuario) {
        try {
            int rows = usuarioDao.delete(usuario);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   public boolean validarLogin(String email, String senha) {
    Usuario usuario = buscarPorEmail(email);
    if (usuario == null) return false;

    String hashDigitado = controller.SecurityUtils.hashSenha(senha);
    return usuario.getSenha().equals(hashDigitado);
}
}
