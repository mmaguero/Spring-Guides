package py.edu.ucsa.rest.api.core.dao;

import java.util.List;
import java.util.Optional;

import py.edu.ucsa.rest.api.core.model.Cobro;

public interface CobroDao {
	
	List<Cobro> listarTodos();

	void registrarCobro(Cobro a);
	
	void actualizar(Cobro a);
	
	Optional<Cobro> getById(long long1);


}
