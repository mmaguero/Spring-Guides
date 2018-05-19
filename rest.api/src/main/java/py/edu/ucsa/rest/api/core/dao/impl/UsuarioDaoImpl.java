package py.edu.ucsa.rest.api.core.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import py.edu.ucsa.rest.api.core.dao.AbstractDao;
import py.edu.ucsa.rest.api.core.dao.UsuarioDao;
import py.edu.ucsa.rest.api.core.model.Usuario;

@Repository("usuarioDao")
public class UsuarioDaoImpl extends AbstractDao<Usuario> implements UsuarioDao {
	@Override
	public Optional<Usuario> getById(long id) {
		Optional<Usuario> u = super.getById(id);
		if (u.isPresent()/* != null */) {
			initializeCollection(u.get().getPerfiles());
		}
		return u;
	}

	@Override
	public void borrarPorUsuario(String usuario) {
		Usuario u = (Usuario) getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario")
				.setParameter("usuario", usuario).getSingleResult();
		eliminar(u);
	}

	@Override
	public void borrarPorUsuario(long id) {
		Usuario u = (Usuario) getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.id = :id")
				.setParameter("id", id).getSingleResult();
		eliminar(u);
	}

	@Override
	public void borrarTodos() {
		Usuario u = (Usuario) getEntityManager().createQuery("SELECT u FROM Usuario u").getResultList();
		eliminar(u);
	}

	@Override
	public Optional<Usuario> getByUsuario(String usuario) {
		logger.debug("Usuario: " + usuario);
		try {
			Optional<Usuario> u = Optional.ofNullable(
					(Usuario) getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario")
							.setParameter("usuario", usuario).getSingleResult());
			if (u.isPresent() /* != null */) {
				initializeCollection(u.get().getPerfiles());
			}
			return u;
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> listarTodos() {
		List<Usuario> usuarios = getEntityManager().createQuery("SELECT u FROM Usuario u ORDER BY u.nombre ASC")
				.getResultList();
		return usuarios;
	}

	@Override
	public void insertar(Usuario usuario) {
		super.persistir(usuario);
	}

	@Override
	public void actualizar(Usuario usuario) {
		super.actualizar(usuario);
	}

	// Una alternativa a Hibernate.initialize()
	protected void initializeCollection(Collection<?> collection) {
		if (collection == null) {
			return;
		}
		collection.iterator().hasNext();
	}
}
