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
import py.edu.ucsa.rest.api.core.model.Perfil;
import py.edu.ucsa.rest.api.core.services.PerfilService;
import py.edu.ucsa.rest.api.util.ErrorDTO;

@RestController
@RequestMapping("/perfil")
public class PerfilController {
	public static final Logger logger = LoggerFactory.getLogger(PerfilController.class);
	// ESTE SERVICE HARÁ TODAS LAS TAREAS DE
	// RECUPERACIÓN Y MANIPULACIÓN DE DATOS
	@Autowired
	private PerfilService perfilService;
	// ================ RECUPERAMOS TODOS LOS USUARIOS ================

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> listarPerfils() {
		List<Perfil> perfils = perfilService.listarTodos();
		if (perfils.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// podríamos retornar también HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Perfil>>(perfils, HttpStatus.OK);
	}

	// ================ RECUPERAMOS UN USUARIO A PARTIR DE SU ID ================

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getPerfil(@PathVariable("id") long id) {
		logger.info("Vamos a obtener el Perfil con id {}.", id);
		Optional<Perfil> perfil = perfilService.getById(id);
		if (!perfil.isPresent()) {
			logger.error("No se encontró ningún perfil con id {}.", id);
			return new ResponseEntity<ErrorDTO>(new ErrorDTO("No se encontró ningún Perfil con id " + id),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Perfil>(perfil.get(), HttpStatus.OK);
	}

	// ================ CREAMOS UN USUARIO ================

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> crearPerfil(@RequestBody Perfil perfil, UriComponentsBuilder ucBuilder) {
		logger.info("Creando el Perfil : {}", perfil);
		if (perfilService.isExistePerfil(perfil)) {
			logger.error("Inserción fallida. Ya existe un registro con el perfil {}", perfil.toString());
			return new ResponseEntity<ErrorDTO>(
					new ErrorDTO("Inserción Fallida. Ya existe un registro con el perfil " + perfil.toString()),
					HttpStatus.CONFLICT);
		}
		perfilService.guardarPerfil(perfil);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/perfil/{id}").buildAndExpand(perfil.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ================ ACTUALIZAMOS LOS DATOS DE UN USUARIO ================

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarPerfil(@PathVariable("id") long id, @RequestBody Perfil perfil) {
		logger.info("Actualizando el Perfil con id {}", id);
		Optional<Perfil> perfilBD = perfilService.getById(id);
		if (!perfilBD.isPresent()) {
			logger.error("Actualización fallida. No existe el perfil con el id {}.", id);
			return new ResponseEntity<ErrorDTO>(
					new ErrorDTO("Actualización fallida. No existe el perfil con el id " + id), HttpStatus.NOT_FOUND);
		}
		Perfil perfilBD_ = perfilBD.get();
		perfilBD_.setCodigo(perfil.getCodigo());
		perfilBD_.setDescripcion(perfil.getDescripcion());
		perfilService.guardarPerfil(perfilBD_);
		return new ResponseEntity<Perfil>(perfilBD_, HttpStatus.OK);
	}

	// ================ ELIMINAMOS UN USUARIO ================

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminarPerfil(@PathVariable("id") long id) {
		logger.info("Obteniendo y eliminando el Perfil con id {}", id);
		Optional<Perfil> perfil = perfilService.getById(id);
		if (!perfil.isPresent()) {
			logger.error("Eliminación fallida. No existe el perfil con el id {}", id);
			return new ResponseEntity<ErrorDTO>(new ErrorDTO("No existe el perfil con el id " + id),
					HttpStatus.NOT_FOUND);
		}
		perfilService.eliminarPerfilById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// ================ ELIMINAMOS TODOS LOS USUARIOS================

	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminarPerfils() {
		logger.info("Borrando todos los perfils");
		perfilService.eliminarTodos();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
