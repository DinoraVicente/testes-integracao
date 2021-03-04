package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UsuarioDaoTest {

    private UsuarioDao dao;
    private EntityManager em;

    @Before
    public void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @After
    public void afterEach(){
        em.getTransaction().rollback();
    }

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345");
        em.persist(usuario);
        return usuario;
    }

    @Test
    public void testeBuscaDeUsuarioPeloUsername(){
        Usuario usuario = criarUsuario();
        Usuario usuarioEncontrado = this.dao.buscarPorUsername(usuario.getNome());
        Assert.assertNotNull(usuario);
    }

    @Test
    public void deveriaRemoverUmUsuario(){
        Usuario usuario = criarUsuario();
        dao.deletar(usuario);
        Assert.assertThrows(NoResultException.class,
                () -> this.dao.buscarPorUsername(usuario.getNome()));
    }

    @Test
    public void naoDeveriaEncontrarUsuarioNaoCadastrado() {
        criarUsuario();
        Assert.assertThrows(NoResultException.class,
                () -> this.dao.buscarPorUsername("beltrano"));
    }
}