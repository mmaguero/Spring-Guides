package py.edu.ucsa.rest.api.core.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import py.edu.ucsa.rest.api.core.dao.AbstractDao;
import py.edu.ucsa.rest.api.core.dao.CobroDao;
import py.edu.ucsa.rest.api.core.model.Cobro;

@Repository("cobroDao")
public class CobroDaoImpl extends AbstractDao<Cobro> implements CobroDao {

	@Override
	public void registrarCobro(Cobro a) {
		super.persistir(a);
	}

	@Override
	public void actualizar(Cobro a) {
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
	public List<Cobro> listarTodos() {
		@SuppressWarnings("unchecked")
		List<Cobro> cobros = getEntityManager().createNamedQuery("Cobro.findAll").getResultList();
		return cobros;
	}

	@Override
	public Optional<Cobro> getById(long id) {
		return super.getById(id);
	}
}
