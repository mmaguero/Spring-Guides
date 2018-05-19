package py.edu.ucsa.rest.api.core.services;

import java.util.Optional;

import py.edu.ucsa.rest.api.core.model.Asiento;

public interface AsientoService {

	void guardarAsiento(Asiento a);
	
	boolean isExisteAsiento(Asiento a);

	Optional<Asiento> listarByCodigo(String string);

}
