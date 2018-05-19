package py.edu.ucsa.rest.api.core.services;

import java.util.List;
import java.util.Optional;

import py.edu.ucsa.rest.api.core.model.Cobro;

public interface CobroService {

	void guardarCobro(Cobro cobro);
	
	boolean isExisteCobro(Cobro cobro);

	Optional<Cobro> listarById(long id);
	
	List<Cobro> listarTodos();

}
