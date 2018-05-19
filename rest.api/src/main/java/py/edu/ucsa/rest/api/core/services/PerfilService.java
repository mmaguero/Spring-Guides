package py.edu.ucsa.rest.api.core.services;

import java.util.List;
import java.util.Optional;

import py.edu.ucsa.rest.api.core.model.Perfil;
//import py.edu.ucsa.rest.web.dto.PerfilDTO;

public interface PerfilService {

	Optional<Perfil> getById(long id);

	Optional<Perfil> getByPerfil(String perfil);

	void guardarPerfil(Perfil perfil);

	void eliminarPerfilById(long id);

	List<Perfil> listarTodos();

	void eliminarTodos();

	boolean isExistePerfil(Perfil perfil);

}
