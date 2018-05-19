package py.edu.ucsa.rest.api.core.services;

/*import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import py.edu.ucsa.rest.web.dto.PerfilDTO;*/

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.ucsa.rest.api.core.dao.PerfilDAO;
import py.edu.ucsa.rest.api.core.model.Perfil;

@Transactional
@Service("perfilService")
public class PerfilServiceImpl implements PerfilService {

	@Autowired
	private PerfilDAO dao;

	public List<Perfil> listarTodos() {
		return dao.listarTodos();
	}

	public Optional<Perfil> getById(long id) {
		return dao.getById(id);
	}

	public Optional<Perfil> getByPerfil(String perfil) {
		return dao.getByPerfil(perfil);
	}

	public void guardarPerfil(Perfil perfil) {
		if (perfil.getId() != 0L)
			dao.insertar(perfil);
		else
			dao.actualizar(perfil);
	}

	public void eliminarPerfilById(long id) {
	}

	public void eliminarTodos() {
	}

	@Override
	public boolean isExistePerfil(Perfil perfil) {
		return getByPerfil(perfil.toString()) != null;
	}

}