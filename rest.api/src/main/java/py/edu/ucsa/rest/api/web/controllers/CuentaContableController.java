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
import py.edu.ucsa.rest.api.core.model.CuentaContable;
import py.edu.ucsa.rest.api.core.services.CuentaContableService;
import py.edu.ucsa.rest.api.util.ErrorDTO;

@RestController
@RequestMapping("/CuentaContable")
public class CuentaContableController {
	public static final Logger logger = LoggerFactory.getLogger(CuentaContableController.class);
	// ESTE SERVICE HARÁ TODAS LAS TAREAS DE
	// RECUPERACIÓN Y MANIPULACIÓN DE DATOS
	@Autowired
	private CuentaContableService cuentaContableService;
	// ================ RECUPERAMOS TODOS LAS CCs ================

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> listarCuentasContables() {
		List<CuentaContable> ccs = cuentaContableService.listarTodos();
		if (ccs.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// podríamos retornar también HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<CuentaContable>>(ccs, HttpStatus.OK);
	}

	// ================ RECUPERAMOS UNA CC A PARTIR DE SU ID ================

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCuentaContable(@PathVariable("id") Long id) {
		logger.info("Vamos a obtener el CuentaContable con id {}.", id);
		Optional<CuentaContable> cc = cuentaContableService.getById(id);
		if (!cc.isPresent()) {
			logger.error("No se encontró ninguna CuentaContable con id {}.", id);
			return new ResponseEntity<ErrorDTO>(new ErrorDTO("No se encontró ninguna CuentaContable con id " + id),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CuentaContable>(cc.get(), HttpStatus.OK);
	}

	// ================ CREAMOS UNA CC ================

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> crearCuentaContable(@RequestBody CuentaContable cc, UriComponentsBuilder ucBuilder) {
		logger.info("Creando el CuentaContable : {}", cc);
		if (cuentaContableService.isExisteCuentaContable(cc)) {
			logger.error("Inserción fallida. Ya existe un registro con la CuentaContable {}", cc.getCodigo());
			return new ResponseEntity<ErrorDTO>(
					new ErrorDTO("Inserción Fallida. Ya existe un registro con la CuentaContable " + cc.getCodigo()),
					HttpStatus.CONFLICT);
		}
		cuentaContableService.guardarCuentaContable(cc);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/cuenta-contable/{id}").buildAndExpand(cc.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ================ ACTUALIZAMOS LOS DATOS DE UNA CC ================

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarCuentaContable(@PathVariable("id") Long id, @RequestBody CuentaContable cc) {
		logger.info("Actualizando la cuentaContable con id {}", id);
		Optional<CuentaContable> ccBD = cuentaContableService.getById(id);
		if (!ccBD.isPresent()) {
			logger.error("Actualización fallida. No existe la CuentaContable con el id {}.", id);
			return new ResponseEntity<ErrorDTO>(
					new ErrorDTO("Actualización fallida. No existe la CuentaContable con el id " + id),
					HttpStatus.NOT_FOUND);
		}
		CuentaContable ccBD_ = ccBD.get();
		ccBD_.setAsentable(cc.getAsentable());
		ccBD_.setCodigo(cc.getCodigo());
		//ccBD_.setCuentaPadre(cc.getCuentaPadre());
		ccBD_.setDescripcion(cc.getDescripcion());
		ccBD_.setNivel(cc.getNivel());
		ccBD_.setNumeroCuenta(cc.getNumeroCuenta());
		ccBD_.setTipoCuenta(cc.getTipoCuenta());
		cuentaContableService.guardarCuentaContable(ccBD_);
		return new ResponseEntity<CuentaContable>(ccBD_, HttpStatus.OK);
	}

	// ================ ELIMINAMOS UNA CC ================

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminarCuentaContable(@PathVariable("id") Long id) {
		logger.info("Obteniendo y eliminando la CuentaContable con id {}", id);
		Optional<CuentaContable> cc = cuentaContableService.getById(id);
		if (!cc.isPresent()) {
			logger.error("Eliminación fallida. No existe la CuentaContable con el id {}", id);
			return new ResponseEntity<ErrorDTO>(new ErrorDTO("No existe la CuentaContable con el id " + id),
					HttpStatus.NOT_FOUND);
		}
		cuentaContableService.eliminarCuentaContableById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
