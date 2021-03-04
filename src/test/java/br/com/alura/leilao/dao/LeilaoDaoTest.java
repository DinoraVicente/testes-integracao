package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LeilaoDaoTest {

    private LeilaoDao dao;
    private EntityManager em;

    @Before
    public void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new LeilaoDao(em);
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
    void deveriaCadastrarUmLeilao() {
        Usuario usuario = criarUsuario();
        Leilao leilao = new Leilao("Mochila", new BigDecimal("70"),
                LocalDate.now(), usuario);

        leilao = dao.salvar(leilao);

        Leilao salvo = dao.buscarPorId(leilao.getId());
        Assert.assertNotNull(salvo);
    }
}