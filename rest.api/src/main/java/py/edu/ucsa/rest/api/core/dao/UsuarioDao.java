package py.edu.ucsa.rest.api.core.dao;

import java.util.List;
import java.util.Optional;

import py.edu.ucsa.rest.api.core.model.Usuario;

public interface UsuarioDao {

	Optional<Usuario> getById(long id);

	Optional<Usuario> getByUsuario(String usuario);

	void insertar(Usuario usuario);

	void actualizar(Usuario usuario);

	void borrarPorUsuario(String usuario);

	List<Usuario> listarTodos();

	void borrarPorUsuario(long id);
	
	void borrarTodos();

}
