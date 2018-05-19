package py.edu.ucsa.rest.api.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the cobros database table.
 * 
 */
@Entity
@Table(name="cobros")
@NamedQuery(name="Cobro.findAll", query="SELECT c FROM Cobro c")
public class Cobro implements Serializable {

	private static final long serialVersionUID = 7427767958734105384L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name="fecha_cobro")
	private Date fechaCobro;
	
	@Column(name="monto_total")
	private Double montoTotal;

	@OneToMany(cascade=CascadeType.PERSIST)
	@NotEmpty
	@JoinColumn(name="id_cobro")
	private List<CobroDetalleConcepto> conceptosCobro;
	

	@OneToMany(cascade=CascadeType.PERSIST)
	@NotEmpty
	@JoinColumn(name="id_cobro")
	private List<CobroDetalleMedioPago> mediosPago;

	public Cobro() {
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getFechaCobro() {
		return fechaCobro;
	}

	public void setFechaCobro(Date fechaCobro) {
		this.fechaCobro = fechaCobro;
	}

	public Double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(Double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public List<CobroDetalleConcepto> getConceptosCobro() {
		return conceptosCobro;
	}

	public void setConceptosCobro(List<CobroDetalleConcepto> conceptosCobro) {
		this.conceptosCobro = conceptosCobro;
	}

	public List<CobroDetalleMedioPago> getMediosPago() {
		return mediosPago;
	}

	public void setMediosPago(List<CobroDetalleMedioPago> mediosPago) {
		this.mediosPago = mediosPago;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Cobro [id=" + id + ", cliente=" + cliente.toString() + ", fechaCobro=" + fechaCobro + ", montoTotal=" + montoTotal
				+ ", conceptosCobro=" + conceptosCobro.toString() + ", mediosPago=" + mediosPago.toString() + "]";
	}

}