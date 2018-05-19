package py.edu.ucsa.rest.api.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="cobros_det_medio_pago")
@NamedQuery(name="CobroDetalleMedioPago.findAll", query="SELECT c FROM CobroDetalleMedioPago c")
public class CobroDetalleMedioPago {
	@Override
	public String toString() {
		return "CobroDetalleMedioPago [id=" + id + ", monto=" + monto + ", medioPago=" + medioPago + "]";
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn
	private Double monto;

	@ManyToOne
	@JoinColumn(name="id_medio_pago")
	private MedioPago medioPago;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	public MedioPago getMedioPago() {
		return medioPago;
	}
	
	public void setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}	
	
}
