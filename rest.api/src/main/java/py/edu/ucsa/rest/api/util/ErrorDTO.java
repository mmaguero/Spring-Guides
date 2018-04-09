package py.edu.ucsa.rest.api.util;

public class ErrorDTO {
	private String mensajeError;

	public ErrorDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorDTO(String mensajeError) {
		super();
		this.mensajeError = mensajeError;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
}
