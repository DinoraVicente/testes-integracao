package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UsuarioDao {

	private EntityManager em;

	@Autowired
	public UsuarioDao(EntityManager em) {
		this.em = em;
	}

	public Usuario buscarPorUsername(String username) {
		return em.createQuery("SELECT u FROM Usuario u WHERE u.nome = :username", Usuario.class)
				.setParameter("username", username)
				.getSingleResult();
	}

	public void deletar(Usuario usuario) {
		em.remove(usuario);
	}

}
