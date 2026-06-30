package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import java.util.List;

public class PropostaRepository {
    private Dao<Proposta, Integer> propostaDao;

    public PropostaRepository() {
        try {
            propostaDao = DaoManager.createDao(Database.getConnectionSource(), Proposta.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean salvar(Proposta proposta) {
        try {
            if (proposta == null) return false;
            int rows = propostaDao.create(proposta);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarStatus(Proposta proposta, String status) {
        try {
            if (proposta == null) return false;
            proposta.setStatus(status);
            int rows = propostaDao.update(proposta);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Proposta> buscarRecebidasPorUsuario(Usuario usuario) {
        try {
            if (usuario == null) return List.of();
            
            Dao<AnuncioTroca, Integer> anuncioDao = DaoManager.createDao(Database.getConnectionSource(), AnuncioTroca.class);
            List<AnuncioTroca> meusAnuncios = anuncioDao.queryBuilder()
                .where()
                .eq("vendedor_email", usuario.getEmail())
                .query();
                
            if (meusAnuncios.isEmpty()) {
                return List.of();
            }
            
            List<Integer> ids = meusAnuncios.stream().map(AnuncioTroca::getId).toList();
            
            return propostaDao.queryBuilder()
                .where()
                .in("anuncio_id", ids)
                .and()
                .eq("status", "PENDENTE")
                .query();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void rejeitarOutrasParaAnuncio(AnuncioTroca anuncio, Proposta propostaAceita) {
        try {
            if (anuncio == null || propostaAceita == null) return;
            List<Proposta> outras = propostaDao.queryBuilder()
                .where()
                .eq("anuncio_id", anuncio.getId())
                .and()
                .ne("id", propostaAceita.getId())
                .and()
                .eq("status", "PENDENTE")
                .query();
            
            for (Proposta p : outras) {
                p.setStatus("REJEITADA");
                propostaDao.update(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
