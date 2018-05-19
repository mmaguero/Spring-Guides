package py.edu.ucsa.rest.api.core.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.edu.ucsa.rest.api.core.dao.AsientoDao;
import py.edu.ucsa.rest.api.core.model.Asiento;
import py.edu.ucsa.rest.api.core.model.DetalleAsiento;
import py.edu.ucsa.rest.api.core.model.MedioPago;
import py.edu.ucsa.rest.api.core.model.CobroDetalleConcepto;
import py.edu.ucsa.rest.api.core.model.CobroDetalleMedioPago;
import py.edu.ucsa.rest.api.core.model.ConceptoCobro;
import py.edu.ucsa.rest.api.core.dao.CobroDao;
import py.edu.ucsa.rest.api.core.dao.ConceptoCobroDao;
import py.edu.ucsa.rest.api.core.dao.MedioPagoDao;
import py.edu.ucsa.rest.api.core.model.Cobro;

@Transactional
@Service("cobroService")

public class CobroServiceImpl implements CobroService {

	@Autowired
	private CobroDao cobroDao;
	@Autowired
	private AsientoDao asientoDao;
	@Autowired
	private ConceptoCobroDao conceptoCobroDao;
	@Autowired
	private MedioPagoDao medioPagoDao;

	public void guardarCobro(Cobro cobro) {
		if (cobro.getId() == 0L) {
			cobroDao.registrarCobro(cobro);
			Asiento a = new Asiento();
			a.setDescripcion(cobro.getConceptosCobro().toString() + "|" + cobro.getCliente().getNroDocumento());
			a.setEstado("P");
			a.setFechaAsiento(cobro.getFechaCobro());
			a.setFechaRegistro(new Date());
			a.setNroAsiento("0001/2018");
			DetalleAsiento det = null;

			for (CobroDetalleConcepto detalleConceptoCobro : cobro.getConceptosCobro()) {
				det = new DetalleAsiento();
				if (detalleConceptoCobro.getConcepto().getCuentaContable() == null) {
					Optional<ConceptoCobro> concepto = conceptoCobroDao
							.getById(detalleConceptoCobro.getConcepto().getId());
					if (concepto.isPresent()) {
						detalleConceptoCobro.setConcepto(concepto.get());
					}
				}
				if ("D".equals(detalleConceptoCobro.getConcepto().getCuentaContable().getTipoCuenta())) {
					det.setMontoDebe(detalleConceptoCobro.getMonto());
				} else {
					det.setMontoHaber(detalleConceptoCobro.getMonto());
				}
				det.setCuentaContable(detalleConceptoCobro.getConcepto().getCuentaContable());
				det.setAsiento(a);
				a.getDetalles().add(det);
			}

			for (CobroDetalleMedioPago mp : cobro.getMediosPago()) {
				det = new DetalleAsiento();
				if (mp.getMedioPago().getCuentaContable() == null) {
					Optional<MedioPago> medio = medioPagoDao.getById(mp.getMedioPago().getId());
					if (medio.isPresent())
						mp.setMedioPago(medio.get());

				}
				if ("D".equals(mp.getMedioPago().getCuentaContable().getTipoCuenta())) {
					det.setMontoDebe(mp.getMonto());
				} else if ("A".equals(mp.getMedioPago().getCuentaContable().getTipoCuenta())) {
					det.setMontoHaber(mp.getMonto());
				}
				det.setCuentaContable(mp.getMedioPago().getCuentaContable());
				det.setAsiento(a);
				a.getDetalles().add(det);
			}
			asientoDao.registrarAsiento(a);
		} else {
			cobroDao.actualizar(cobro);
		}
	}

	@Override
	public Optional<Cobro> listarById(long id) {
		return cobroDao.getById(id);
	}

	@Override
	public boolean isExisteCobro(Cobro cobro) {
			return listarById(cobro.getId()).isPresent();
	}

	@Override
	public List<Cobro> listarTodos() {
		return cobroDao.listarTodos();
	}
}
