package py.edu.ucsa.rest.api.web.controllers;

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
import py.edu.ucsa.rest.api.core.model.Asiento;
import py.edu.ucsa.rest.api.core.services.AsientoService;
import py.edu.ucsa.rest.api.util.ErrorDTO;

@RestController
@RequestMapping("/asiento")
public class AsientoController {
	public static final Logger logger = LoggerFactory.getLogger(AsientoController.class);
	// ESTE SERVICE HARÁ TODAS LAS TAREAS DE
	// RECUPERACIÓN Y MANIPULACIÓN DE DATOS
	@Autowired
	private AsientoService asientoService;

	// ================ CREAMOS UN asiento ================

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> crearAsiento(@RequestBody Asiento asiento, UriComponentsBuilder ucBuilder) {
		logger.info("Creando el  : {}", asiento);
		if (asientoService.isExisteAsiento(asiento)) {
			logger.error("Inserción fallida. Ya existe un registro con el  {}", asiento.getNroAsiento());
			return new ResponseEntity<ErrorDTO>(
					new ErrorDTO("Inserción Fallida. Ya existe un registro con el  " + asiento.getNroAsiento()),
					HttpStatus.CONFLICT);
		}
		asientoService.guardarAsiento(asiento);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/asiento/{id}").buildAndExpand(asiento.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ================ ACTUALIZAMOS LOS DATOS DE UN ASIENTO ================

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarCuentaContable(@PathVariable("nro-asiento") String nroAsiento,
			@RequestBody Asiento a) {
		logger.info("Actualizando la asiento con id {}", nroAsiento);
		Optional<Asiento> ccBD = asientoService.listarByCodigo(nroAsiento);
		if (!ccBD.isPresent()) {
			logger.error("Actualización fallida. No existe la Asiento con el nroAsiento {}.", nroAsiento);
			return new ResponseEntity<ErrorDTO>(
					new ErrorDTO("Actualización fallida. No existe la Asiento con el nroAsiento " + nroAsiento),
					HttpStatus.NOT_FOUND);
		}
		Asiento ccBD_ = ccBD.get();
		//ccBD_.addDetalleAsiento(a.getDetalles());
		ccBD_.setDescripcion(a.getDescripcion());
		ccBD_.setEstado(a.getEstado());
		ccBD_.setFechaAsiento(a.getFechaAsiento());
		ccBD_.setFechaRegistro(a.getFechaRegistro());
		ccBD_.setNroAsiento(a.getNroAsiento());
		asientoService.guardarAsiento(ccBD_);
		return new ResponseEntity<Asiento>(ccBD_, HttpStatus.OK);
	}

}
