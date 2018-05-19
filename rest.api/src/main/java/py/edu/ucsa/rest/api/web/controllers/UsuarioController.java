package py.edu.ucsa.rest.api.web.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import py.edu.ucsa.rest.api.core.model.Usuario;
import py.edu.ucsa.rest.api.core.services.UsuarioService;
import py.edu.ucsa.rest.api.util.ErrorDTO;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	public static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	// ESTE SERVICE HARÁ TODAS LAS TAREAS DE
	// RECUPERACIÓN Y MANIPULACIÓN DE DATOS
	@Autowired
	private UsuarioService usuarioService;
	// ================ RECUPERAMOS TODOS LOS USUARIOS ================

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> listarUsuarios() {
		List<Usuario> usuarios = usuarioService.listarTodos();
		if (usuarios.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// podríamos retornar también HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}

	// ================ RECUPERAMOS UN USUARIO A PARTIR DE SU ID ================

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUsuario(@PathVariable("id") long id) {
		logger.info("Vamos a obtener el Usuario con id {}.", id);
		Optional<Usuario> usuario = usuarioService.getById(id);
		if (!usuario.isPresent()) {
			logger.error("No se encontró ningún usuario con id {}.", id);
			return new ResponseEntity<ErrorDTO>(new ErrorDTO("No se encontró ningún Usuario con id " + id),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	}

	// ================ CREAMOS UN USUARIO ================

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, UriComponentsBuilder ucBuilder) {
		logger.info("Creando el Usuario : {}", usuario);
		if (usuarioService.isExisteUsuario(usuario)) {
			logger.error("Inserción fallida. Ya existe un registro con el usuario {}", usuario.getUsuario());
			return new ResponseEntity<ErrorDTO>(
					new ErrorDTO("Inserción Fallida. Ya existe un registro con el usuario " + usuario.getUsuario()),
					HttpStatus.CONFLICT);
		}
		usuarioService.guardarUsuario(usuario);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ================ ACTUALIZAMOS LOS DATOS DE UN USUARIO ================

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarUsuario(@PathVariable("id") long id, @RequestBody Usuario usuario) {
		logger.info("Actualizando el Usuario con id {}", id);
		Optional<Usuario> usuarioBD = usuarioService.getById(id);
		if (!usuarioBD.isPresent()) {
			logger.error("Actualización fallida. No existe el usuario con el id {}.", id);
			return new ResponseEntity<ErrorDTO>(
					new ErrorDTO("Actualización fallida. No existe el usuario con el id " + id), HttpStatus.NOT_FOUND);
		}
		Usuario usuarioBD_ = usuarioBD.get();
		usuarioBD_.setApellido(usuario.getApellido());
		usuarioBD_.setEmail(usuario.getEmail());
		usuarioBD_.setUsuario(usuario.getUsuario());
		usuarioBD_.setClave(usuario.getClave());
		usuarioBD_.setNombre(usuario.getNombre());
		usuarioService.guardarUsuario(usuarioBD_);
		return new ResponseEntity<Usuario>(usuarioBD_, HttpStatus.OK);
	}

	// ================ ELIMINAMOS UN USUARIO ================

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminarUsuario(@PathVariable("id") long id) {
		logger.info("Obteniendo y eliminando el Usuario con id {}", id);
		Optional<Usuario> usuario = usuarioService.getById(id);
		if (!usuario.isPresent()) {
			logger.error("Eliminación fallida. No existe el usuario con el id {}", id);
			return new ResponseEntity<ErrorDTO>(new ErrorDTO("No existe el usuario con el id " + id),
					HttpStatus.NOT_FOUND);
		}
		usuarioService.eliminarUsuarioById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// ================ ELIMINAMOS TODOS LOS USUARIOS================

	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminarUsuarios() {
		logger.info("Borrando todos los usuarios");
		usuarioService.eliminarTodos();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
