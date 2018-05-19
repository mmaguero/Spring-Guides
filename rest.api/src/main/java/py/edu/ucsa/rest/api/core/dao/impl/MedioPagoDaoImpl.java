package py.edu.ucsa.rest.api.core.dao.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import py.edu.ucsa.rest.api.core.dao.AbstractDao;
import py.edu.ucsa.rest.api.core.dao.MedioPagoDao;
import py.edu.ucsa.rest.api.core.model.MedioPago;

@Repository("medioPagoDao")

public class MedioPagoDaoImpl extends AbstractDao<MedioPago> implements MedioPagoDao {

	@Override
	public Optional<MedioPago> getById(long id) {
		return super.getById(id);
	}

}
