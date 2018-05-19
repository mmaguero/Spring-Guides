package py.edu.ucsa.rest.api.core.dao;

import java.util.List;
import java.util.Optional;

import py.edu.ucsa.rest.api.core.model.Asiento;

public interface AsientoDao {
	
	List<Asiento> listarTodos();

	void registrarAsiento(Asiento a);

	Optional<Asiento> listarByCodigo(String string);
	
	void actualizar(Asiento a);
	
	Optional<Asiento> getById(long id);

}
