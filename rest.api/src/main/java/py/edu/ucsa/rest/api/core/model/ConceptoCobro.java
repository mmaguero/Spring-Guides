package py.edu.ucsa.rest.api.core.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * La clase de persistencia para la tabla conceptos_cobro 
 * 
 */
@Entity
@Table(name="conceptos_cobro")
@NamedQuery(name="ConceptoCobro.findAll", query="SELECT cc FROM ConceptoCobro cc")
public class ConceptoCobro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String codigo;

	private String descripcion;
	
	@ManyToOne
	@JoinColumn(name="id_cuenta_contable")
	private CuentaContable cuentaContable;

	public ConceptoCobro() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ConceptoCobro [id=" + id + ", codigo=" + codigo + ", descripcion=" + descripcion + ", cuentaContable="
				+ cuentaContable.toString() + "]";
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CuentaContable getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(CuentaContable cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

}