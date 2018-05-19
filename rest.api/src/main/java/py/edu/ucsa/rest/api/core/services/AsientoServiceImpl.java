package py.edu.ucsa.rest.api.core.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.ucsa.rest.api.core.dao.AsientoDao;
import py.edu.ucsa.rest.api.core.model.Asiento;

@Transactional
@Service("asientoService")
public class AsientoServiceImpl implements AsientoService {

	@Autowired
	private AsientoDao dao;

	public void guardarAsiento(Asiento a) {
		if (a.getId() != 0L)
			dao.registrarAsiento(a);
		else
			dao.actualizar(a);
	}
	
	@Override
	public boolean isExisteAsiento(Asiento a) {
		return listarByCodigo(a.getNroAsiento()) != null;
	}
	
	@Override
	public Optional<Asiento> listarByCodigo(String string) {
		return dao.listarByCodigo(string);
	}

}