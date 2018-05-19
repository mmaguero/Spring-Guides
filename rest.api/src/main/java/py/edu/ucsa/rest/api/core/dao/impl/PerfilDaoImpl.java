package py.edu.ucsa.rest.api.core.dao.impl;

//import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import py.edu.ucsa.rest.api.core.dao.AbstractDao;
import py.edu.ucsa.rest.api.core.dao.PerfilDAO;
import py.edu.ucsa.rest.api.core.model.Perfil;

@Repository("PerfilDAO")
public class PerfilDaoImpl extends AbstractDao<Perfil> implements PerfilDAO {
	@Override
	public Optional<Perfil> getById(long id) {
		Optional<Perfil> u = super.getById(id);
		/*
		 * if (u.isPresent()) { initializeCollection(u.get().getPerfiles()); }
		 */
		return u;
	}

	@Override
	public void borrarPorPerfil(String perfil) {
		Perfil u = (Perfil) getEntityManager().createQuery("SELECT u FROM Perfil u WHERE u.codigo = :codigo")
				.setParameter("perfil", perfil).getSingleResult();
		eliminar(u);
	}

	@Override
	public Optional<Perfil> getByPerfil(String perfil) {
		logger.debug("Perfil: " + perfil);
		try {
			Optional<Perfil> u = Optional.ofNullable(
					(Perfil) getEntityManager().createQuery("SELECT u FROM Perfil u WHERE u.codigo = :codigo")
							.setParameter("codigo", perfil).getSingleResult());
			/*
			 * if (u.isPresent()) { initializeCollection(u.get().getPerfiles()); }
			 */
			return u;
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Perfil> listarTodos() {
		List<Perfil> perfils = getEntityManager().createQuery("SELECT u FROM Perfil u ORDER BY u.descripcion ASC")
				.getResultList();
		return perfils;
	}

	@Override
	public void insertar(Perfil perfil) {
		super.persistir(perfil);
	}

	@Override
	public void actualizar(Perfil perfil) {
		super.actualizar(perfil);
	}

	// Una alternativa a Hibernate.initialize()
	/*
	 * protected void initializeCollection(Collection<?> collection) { if
	 * (collection == null) { return; } collection.iterator().hasNext(); }
	 */
}
