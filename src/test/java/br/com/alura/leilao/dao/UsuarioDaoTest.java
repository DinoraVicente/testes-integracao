package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;

public class UsuarioDaoTest {

    private UsuarioDao dao;

    @Test
    public void testeBuscaDeUsuarioPeloUsername(){
        EntityManager em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345");
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();

        Usuario usuarioEncontrado = this.dao.buscarPorUsername(usuario.getNome());
        Assert.assertNotNull(usuario);
    }
}