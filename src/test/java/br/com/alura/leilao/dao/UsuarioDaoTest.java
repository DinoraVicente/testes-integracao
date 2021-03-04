package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.UsuarioBuilder;
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
    public void before() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @After
    public void after(){
        em.getTransaction().rollback();
    }

    @Test
    public void testeBuscaDeUsuarioPeloUsername(){
        Usuario usuario =  new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("fulano@email.com")
                .comSenha("123456")
                .criar();
        em.persist(usuario);
        Usuario usuarioEncontrado = this.dao.buscarPorUsername(usuario.getNome());
        Assert.assertNotNull(usuario);
    }

    @Test
    public void deveriaRemoverUmUsuario(){
        Usuario usuario =  new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("fulano@email.com")
                .comSenha("123456")
                .criar();
        em.persist(usuario);

        dao.deletar(usuario);
        Assert.assertThrows(NoResultException.class,
                () -> this.dao.buscarPorUsername(usuario.getNome()));
    }

    @Test
    public void naoDeveriaEncontrarUsuarioNaoCadastrado() {
        Usuario usuario =  new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("fulano@email.com")
                .comSenha("123456")
                .criar();
        em.persist(usuario);
        Assert.assertThrows(NoResultException.class,
                () -> this.dao.buscarPorUsername("beltrano"));
    }
}