package py.edu.ucsa.rest.api.core.dao;

import java.util.List;
import java.util.Optional;

import py.edu.ucsa.rest.api.core.model.CuentaContable;

public interface CuentaContableDao {

	List<CuentaContable> listarTodos();

	Optional<CuentaContable> listarById(long id);

	Optional<CuentaContable> listarByCodigo(String codigo);

	List<CuentaContable> listarPorTipo(String tipo);

	List<CuentaContable> listarPorNroCuenta(String nroCuenta);

	List<CuentaContable> listarCuentasHijas(CuentaContable nroCuentaPadre);

	List<CuentaContable> listarAsentables();

	void insertarCuenta(CuentaContable cc);

	void actualizarCuenta(CuentaContable cc);

	void eliminarCuenta(long id);

	void eliminarCuentaPorCodigo(String codigo);

}
