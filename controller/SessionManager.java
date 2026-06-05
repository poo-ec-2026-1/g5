package controller;

import model.Usuario;
import model.UsuarioRepository;

public class SessionManager {
    private static SessionManager instance;
    private Usuario usuarioLogado;
    private final UsuarioRepository usuarioRepository;

    private SessionManager() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

    public boolean login(String email, String senha) {
        if (usuarioRepository.validarLogin(email, senha)) {
            usuarioLogado = usuarioRepository.buscarPorEmail(email);
            return true;
        }
        return false;
    }

    public void logout() {
        usuarioLogado = null;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public boolean isLoggedIn() {
        return usuarioLogado != null;
    }
}
