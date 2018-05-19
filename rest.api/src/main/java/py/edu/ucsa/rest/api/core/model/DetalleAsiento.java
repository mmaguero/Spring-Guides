package py.edu.ucsa.rest.api.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Clase de persistencia para los detalles de un asiento
 * @author pablo
 *
 */
@Entity
@Table(name = "asientos_det")
@NamedQueries({
		@NamedQuery(name = "DetalleAsiento.findAll", query = "SELECT a FROM DetalleAsiento a")
})
public class DetalleAsiento implements Serializable {

	private static final long serialVersionUID = -7515598207514670771L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_asiento", nullable = false)
	private Asiento asiento;

	@ManyToOne
	@JoinColumn(name = "id_cuenta_contable", nullable = false)
	private CuentaContable cuentaContable;

	public Asiento getAsiento() {
		return asiento;
	}

	public void setAsiento(Asiento asiento) {
		// evita el loop infinito
		if (mismoAsiento(asiento)) {
			return;
		}
		// settea el nuevo asiento
		Asiento asientoAnterior = this.asiento;
		this.asiento = asiento;
		// elimina este detalle del asiento anterior
		if (asientoAnterior != null) {
			asientoAnterior.removeDetalleAsiento(this);			
		}
		// agrega este detalle al asiento
		if (asiento != null) {
			asiento.addDetalleAsiento(this);			
		}
	}

	private boolean mismoAsiento(Asiento asientoNuevo) {
		return asiento == null ? asientoNuevo == null : asiento.equals(asientoNuevo);
	}

	@Column(name = "monto_debe")
	private Double montoDebe;

	@Column(name = "monto_haber")
	private Double montoHaber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CuentaContable getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(CuentaContable cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public Double getMontoDebe() {
		return montoDebe;
	}

	public void setMontoDebe(Double montoDebe) {
		this.montoDebe = montoDebe;
	}

	public Double getMontoHaber() {
		return montoHaber;
	}

	public void setMontoHaber(Double montoHaber) {
		this.montoHaber = montoHaber;
	}

}
