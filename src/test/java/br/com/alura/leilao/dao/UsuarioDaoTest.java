package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.UsuarioBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UsuarioDaoTest {

    private UsuarioDao dao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach(){
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
        Assertions.assertNotNull(usuario);
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
        Assertions.assertThrows(NoResultException.class,
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
        Assertions.assertThrows(NoResultException.class,
                () -> this.dao.buscarPorUsername("beltrano"));
    }
}