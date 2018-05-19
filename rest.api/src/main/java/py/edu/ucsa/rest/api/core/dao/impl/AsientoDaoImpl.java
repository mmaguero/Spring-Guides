package py.edu.ucsa.rest.api.core.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import py.edu.ucsa.rest.api.core.dao.AbstractDao;
import py.edu.ucsa.rest.api.core.dao.AsientoDao;
import py.edu.ucsa.rest.api.core.model.Asiento;

@Repository("asientoDao")
public class AsientoDaoImpl extends AbstractDao<Asiento> implements AsientoDao {

	@Override
	public void registrarAsiento(Asiento a) {
		super.persistir(a);
	}

	@Override
	public Optional<Asiento> listarByCodigo(String nroAsiento) {
		logger.debug("Asiento: " + nroAsiento);
		try {
			Optional<Asiento> u = Optional.ofNullable((Asiento) getEntityManager()
					.createQuery("SELECT u FROM Asiento u WHERE u.numeroAsiento = :nroAsiento")
					.setParameter("nroAsiento", nroAsiento).getSingleResult());
			if (u.isPresent() /* != null */) {
				initializeCollection(u.get().getDetalles());
			}
			return u;
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public void actualizar(Asiento a) {
		super.actualizar(a);
	}

	// Una alternativa a Hibernate.initialize()
	protected void initializeCollection(Collection<?> collection) {
		if (collection == null) {
			return;
		}
		collection.iterator().hasNext();
	}

	@Override
	public List<Asiento> listarTodos() {
		@SuppressWarnings("unchecked")
		List<Asiento> asientos = getEntityManager().createNamedQuery("Asiento.findAll").getResultList();
		return asientos;
	}

	@Override
	public Optional<Asiento> getById(long id) {
		return super.getById(id);
	}
}
