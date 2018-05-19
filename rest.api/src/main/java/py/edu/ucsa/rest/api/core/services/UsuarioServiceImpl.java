package py.edu.ucsa.rest.api.core.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.ucsa.rest.api.core.dao.UsuarioDao;
import py.edu.ucsa.rest.api.core.model.Usuario;

@Transactional
@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDao dao;

	public List<Usuario> listarTodos() {
		return dao.listarTodos();
	}

	public Optional<Usuario> getById(long id) {
		return dao.getById(id);
	}

	public Optional<Usuario> getByUsuario(String usuario) {
		return dao.getByUsuario(usuario);
	}

	public void guardarUsuario(Usuario usuario) {
		if (usuario.getId() != 0L)
			dao.insertar(usuario);
		else
			dao.actualizar(usuario);
	}

	public void eliminarUsuarioById(long id) {
		dao.borrarPorUsuario(id);
	}

	public void eliminarTodos() {
		dao.borrarTodos();
	}

	@Override
	public boolean isExisteUsuario(Usuario usuario) {
		return getByUsuario(usuario.getUsuario()) != null;
	}
}