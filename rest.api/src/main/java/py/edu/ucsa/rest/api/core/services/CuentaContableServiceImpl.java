package py.edu.ucsa.rest.api.core.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import py.edu.ucsa.rest.api.core.dao.CuentaContableDao;
import py.edu.ucsa.rest.api.core.model.CuentaContable;

@Transactional
@Service("CuentaContableService")
public class CuentaContableServiceImpl implements CuentaContableService {

	@Autowired
	private CuentaContableDao dao;

	public List<CuentaContable> listarTodos() {
		return dao.listarTodos();
	}

	public Optional<CuentaContable> getById(Long id) {
		return dao.listarById(id);
	}

	public void guardarCuentaContable(CuentaContable cc) {
		if (cc.getId() != null)//0L
			dao.insertarCuenta(cc);
		else
			dao.actualizarCuenta(cc);
	}

	public void eliminarCuentaContableById(Long id) {
		dao.eliminarCuenta(id);
	}

	@Override
	public boolean isExisteCuentaContable(CuentaContable cc) {
		return listarByCodigo(cc.getCodigo()) != null;
	}

	@Override
	public Optional<CuentaContable> listarByCodigo(String codigo) {
		return dao.listarByCodigo(codigo);
	}

}