package py.edu.ucsa.rest.api.core.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import py.edu.ucsa.rest.api.core.model.UsuarioDTO;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {
	private static final AtomicLong counter = new AtomicLong();
	private static List<UsuarioDTO> usuarios;
	static {
		usuarios = crearUsuariosEnMemoria();
	}

	public List<UsuarioDTO> listarTodos() {
		return usuarios;
	}

	public UsuarioDTO getById(long id) {
		for (UsuarioDTO user : usuarios) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public UsuarioDTO getByUsuario(String usuario) {
		for (UsuarioDTO u : usuarios) {
			if (u.getUsuario().equalsIgnoreCase(usuario)) {
				return u;
			}
		}
		return null;
	}

	public void crearUsuario(UsuarioDTO usuario) {
		usuario.setId(counter.incrementAndGet());
		usuarios.add(usuario);
	}

	public void actualizarUsuario(UsuarioDTO usuario) {
		int index = usuarios.indexOf(usuario);
		usuarios.set(index, usuario);
	}

	public void eliminarUsuarioById(long id) {
		for (Iterator<UsuarioDTO> iterator = usuarios.iterator(); iterator.hasNext();) {
			UsuarioDTO u = iterator.next();
			if (u.getId() == id) {
				iterator.remove();
			}
		}
	}

	public boolean isExisteUsuario(UsuarioDTO usuario) {
		return getByUsuario(usuario.getUsuario()) != null;
	}

	public void eliminarTodos() {
		usuarios.clear();
	}

	private static List<UsuarioDTO> crearUsuariosEnMemoria() {
		List<UsuarioDTO> usuarios = new ArrayList<>();
		usuarios.add(
				new UsuarioDTO(counter.incrementAndGet(), "JUAN", "SANCHEZ", "ASUNCION 234", 30, "jsanchez", "123"));
		usuarios.add(new UsuarioDTO(counter.incrementAndGet(), "ANDY", "SLACHEVSKY", "PITIANTUTA 203", 35, "andy ",
				"andy*"));
		usuarios.add(new UsuarioDTO(counter.incrementAndGet(), "JOSE", "CUBILLA", "ROQUE ALONSO 656", 36, "cubilla",
				"jose"));
		return usuarios;
	}
}