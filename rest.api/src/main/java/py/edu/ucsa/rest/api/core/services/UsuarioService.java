package py.edu.ucsa.rest.api.core.services;

import java.util.List;
import java.util.Optional;

import py.edu.ucsa.rest.api.core.model.Usuario;
//import py.edu.ucsa.rest.web.dto.UsuarioDTO;

public interface UsuarioService {
	/*
	 * UsuarioDTO getById(long id);
	 * 
	 * UsuarioDTO getByUsuario(String name);
	 * 
	 * void crearUsuario(UsuarioDTO user);
	 * 
	 * void actualizarUsuario(UsuarioDTO user);
	 * 
	 * void eliminarUsuarioById(long id);
	 * 
	 * List<UsuarioDTO> listarTodos();
	 * 
	 * void eliminarTodos();
	 * 
	 * boolean isExisteUsuario(UsuarioDTO user);
	 */

	Optional<Usuario> getById(long id);

	Optional<Usuario> getByUsuario(String usuario);

	void guardarUsuario(Usuario usuario);

	void eliminarUsuarioById(long id);

	List<Usuario> listarTodos();

	void eliminarTodos();

	boolean isExisteUsuario(Usuario usuario);

}
