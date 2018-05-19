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
import py.edu.ucsa.rest.api.core.model.Cobro;
import py.edu.ucsa.rest.api.core.services.CobroService;
import py.edu.ucsa.rest.api.util.ErrorDTO;

@RestController
@RequestMapping("/cobro")
public class CobroController {
	public static final Logger logger = LoggerFactory.getLogger(CobroController.class);
	// ESTE SERVICE HARÁ TODAS LAS TAREAS DE
	// RECUPERACIÓN Y MANIPULACIÓN DE DATOS
	@Autowired
	private CobroService cobroService;
	// ================ RECUPERAMOS TODOS LAS CCs ================

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> listarCobros() {
		List<Cobro> ccs = cobroService.listarTodos();
		if (ccs.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// podríamos retornar también HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Cobro>>(ccs, HttpStatus.OK);
	}

	// ================ RECUPERAMOS UNA CC A PARTIR DE SU ID ================

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCobro(@PathVariable("id") long id) {
		logger.info("Vamos a obtener el Cobro con id {}.", id);
		Optional<Cobro> cc = cobroService.listarById(id);
		if (!cc.isPresent()) {
			logger.error("No se encontró ninguna Cobro con id {}.", id);
			return new ResponseEntity<ErrorDTO>(new ErrorDTO("No se encontró ningun Cobro con id " + id),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cobro>(cc.get(), HttpStatus.OK);
	}

	// ================ CREAMOS UNA CC ================

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> crearCobro(@RequestBody Cobro cc, UriComponentsBuilder ucBuilder) {
		logger.info("Creando el Cobro : {}", cc);
		if (cobroService.isExisteCobro(cc)) {
			logger.error("Inserción fallida. Ya existe un registro con Cobro {}", cc.toString());
			return new ResponseEntity<ErrorDTO>(
					new ErrorDTO("Inserción Fallida. Ya existe un registro con Cobro " + cc.toString()),
					HttpStatus.CONFLICT);
		}
		cobroService.guardarCobro(cc);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/cobro/{id}").buildAndExpand(cc.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ================ ACTUALIZAMOS LOS DATOS DE UNA CC ================

	/*@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarCobro(@PathVariable("id") Integer id, @RequestBody Cobro cc) {
		logger.info("Actualizando la cobro con id {}", id);
		Optional<Cobro> ccBD = cobroService.getById(id);
		if (!ccBD.isPresent()) {
			logger.error("Actualización fallida. No existe la Cobro con el id {}.", id);
			return new ResponseEntity<ErrorDTO>(
					new ErrorDTO("Actualización fallida. No existe la Cobro con el id " + id),
					HttpStatus.NOT_FOUND);
		}
		Cobro ccBD_ = ccBD.get();
		ccBD_.setAsentable(cc.getAsentable());
		ccBD_.setCodigo(cc.getCodigo());
		//ccBD_.setCuentaPadre(cc.getCuentaPadre());
		ccBD_.setDescripcion(cc.getDescripcion());
		ccBD_.setNivel(cc.getNivel());
		ccBD_.setNumeroCuenta(cc.getNumeroCuenta());
		ccBD_.setTipoCuenta(cc.getTipoCuenta());
		cobroService.guardarCobro(ccBD_);
		return new ResponseEntity<Cobro>(ccBD_, HttpStatus.OK);
	}*/

	// ================ ELIMINAMOS UNA CC ================

	/*@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminarCobro(@PathVariable("id") Integer id) {
		logger.info("Obteniendo y eliminando la Cobro con id {}", id);
		Optional<Cobro> cc = cobroService.getById(id);
		if (!cc.isPresent()) {
			logger.error("Eliminación fallida. No existe la Cobro con el id {}", id);
			return new ResponseEntity<ErrorDTO>(new ErrorDTO("No existe la Cobro con el id " + id),
					HttpStatus.NOT_FOUND);
		}
		cobroService.eliminarCobroById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}*/

}
