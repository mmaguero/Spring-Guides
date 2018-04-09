package py.edu.ucsa.rest.api.web.controllers;

import java.util.List;
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
import py.edu.ucsa.rest.api.core.model.UsuarioDTO;
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
		List<UsuarioDTO> usuarios = usuarioService.listarTodos();
		if (usuarios.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// podríamos retornar también HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<UsuarioDTO>>(usuarios, HttpStatus.OK);
	}
	// ================ RECUPERAMOS UN USUARIO A PARTIR DE SU ID ================

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUsuario(@PathVariable("id") long id) {
		logger.info("Vamos a obtener el Usuario con id {}.", id);
		UsuarioDTO usuario = usuarioService.getById(id);
		if (usuario == null) {
			logger.error("No se encontró ningún usuario con id {}.", id);
			return new ResponseEntity<ErrorDTO>(new ErrorDTO("No se encontró ningún Usuario con id " + id),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UsuarioDTO>(usuario, HttpStatus.OK);
	}

	// ================ CREAMOS UN USUARIO ================
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDTO usuario, UriComponentsBuilder ucBuilder) {
		logger.info("Creando el Usuario : {}", usuario);
		if (usuarioService.isExisteUsuario(usuario)) {
			logger.error("Inserción fallida. Ya existe un registro con el usuario {}", usuario.getUsuario());
			return new ResponseEntity<ErrorDTO>(
					new ErrorDTO("Inserción Fallida. Ya existe un registro con el usuario " + usuario.getUsuario()),
					HttpStatus.CONFLICT);
		}
		usuarioService.crearUsuario(usuario);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ================ ACTUALIZAMOS LOS DATOS DE UN USUARIO ================
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarUsuario(@PathVariable("id") long id, @RequestBody UsuarioDTO usuario) {
		logger.info("Actualizando el Usuario con id {}", id);
		UsuarioDTO usuarioBD = usuarioService.getById(id);
		if (usuarioBD == null) {
			logger.error("Actualización fallida. No existe el usuario con el id {}.", id);
			return new ResponseEntity<ErrorDTO>(
					new ErrorDTO("Actualización fallida. No existe el usuario con el id " + id), HttpStatus.NOT_FOUND);
		}
		usuarioBD.setApellido(usuario.getApellido());
		usuarioBD.setDireccion(usuario.getDireccion());
		usuarioBD.setEdad(usuario.getEdad());
		usuarioBD.setNombre(usuario.getNombre());
		usuarioService.actualizarUsuario(usuarioBD);
		return new ResponseEntity<UsuarioDTO>(usuarioBD, HttpStatus.OK);
	}

	// ================ ELIMINAMOS UN USUARIO ================
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminarUsuario(@PathVariable("id") long id) {
		logger.info("Obteniendo y eliminando el Usuario con id {}", id);
		UsuarioDTO usuario = usuarioService.getById(id);
		if (usuario == null) {
			logger.error("Eliminación fallida. No existe el usuario con el id {}", id);
			return new ResponseEntity<ErrorDTO>(new ErrorDTO("No existe el usuario con el id " + id),
					HttpStatus.NOT_FOUND);
		}
		usuarioService.eliminarUsuarioById(id);
		return new ResponseEntity<UsuarioDTO>(HttpStatus.NO_CONTENT);
	}
	// ================ ELIMINAMOS TODOS LOS USUARIOS ================

	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public ResponseEntity<UsuarioDTO> eliminarUsuarios() {
		logger.info("Borrando todos los usuarios");
		usuarioService.eliminarTodos();
		return new ResponseEntity<UsuarioDTO>(HttpStatus.NO_CONTENT);
	}

}
