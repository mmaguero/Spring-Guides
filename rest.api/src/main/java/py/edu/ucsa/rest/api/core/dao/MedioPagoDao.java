package py.edu.ucsa.rest.api.core.dao;

import java.util.Optional;

import py.edu.ucsa.rest.api.core.model.MedioPago;

public interface MedioPagoDao {
	
	Optional<MedioPago> getById(long id);

}
