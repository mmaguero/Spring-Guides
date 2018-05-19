package py.edu.ucsa.rest.api.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Clase de persistencia para la cabecera de asientos
 * 
 * @author pablo
 *
 */

@Entity
@Table(name = "asientos_cab")
@NamedQuery(name = "Asiento.findAll", query = "SELECT a FROM Asiento a order by a.fechaAsiento desc")
public class Asiento implements Serializable {

	private static final long serialVersionUID = 6622258084459752669L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nro_asiento")
	private String nroAsiento;

	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "fecha_asiento")
	private Date fechaAsiento;

	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "fecha_registro")
	private Date fechaRegistro;

	@Column(length = 2000)
	private String descripcion;

	@Column(length = 3)
	private String estado;

	@OneToMany(mappedBy = "asiento", cascade = CascadeType.PERSIST)
	private List<DetalleAsiento> detalles = new ArrayList<DetalleAsiento>();

	public List<DetalleAsiento> getDetalles() {
		return new ArrayList<DetalleAsiento>(detalles);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNroAsiento() {
		return nroAsiento;
	}

	public void setNroAsiento(String nroAsiento) {
		this.nroAsiento = nroAsiento;
	}

	public Date getFechaAsiento() {
		return fechaAsiento;
	}

	public void setFechaAsiento(Date fechaAsiento) {
		this.fechaAsiento = fechaAsiento;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Agrega un nuevo detalle al asiento.
	 * El método mantiene la consistencia de las
	 * relaciones: este asiento es asignado como el asiento del detalle
	 */
	public void addDetalleAsiento(DetalleAsiento detalle) {
		// previene el loop infinito
		if (detalles.contains(detalle))
			return;
		// crea un nuevo detalle
		detalles.add(detalle);
		// set este objeto como asiento al detalle
		detalle.setAsiento(this);
	}

	/**
	 * Remueve el detalle del asiento. El método mantiene la consistencia de la relación: 
	 * El objeto detalle del asiento ya no referencia a este asiento como su propietario
	 */
	public void removeDetalleAsiento(DetalleAsiento detalle) {
		// previene el loop infinito
		if (!detalles.contains(detalle))
			return;
		// remueve el detalle del asiento
		detalles.remove(detalle);
		// remueve este objeto del detalle
		detalle.setAsiento(null);
	}
}
