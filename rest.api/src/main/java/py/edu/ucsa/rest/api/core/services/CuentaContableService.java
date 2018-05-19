package py.edu.ucsa.rest.api.core.services;

import java.util.List;
import java.util.Optional;

import py.edu.ucsa.rest.api.core.model.CuentaContable;

public interface CuentaContableService {

	Optional<CuentaContable> getById(Long id);

	Optional<CuentaContable> listarByCodigo(String codigo);

	void guardarCuentaContable(CuentaContable cc);

	void eliminarCuentaContableById(Long id);

	List<CuentaContable> listarTodos();

	boolean isExisteCuentaContable(CuentaContable cc);

}
