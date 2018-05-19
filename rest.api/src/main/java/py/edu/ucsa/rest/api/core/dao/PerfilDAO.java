package py.edu.ucsa.rest.api.core.dao;

import java.util.List;
import java.util.Optional;

import py.edu.ucsa.rest.api.core.model.Perfil;

public interface PerfilDAO {

	Optional<Perfil> getById(long id);

	void insertar(Perfil perfil);

	void actualizar(Perfil perfil);

	void borrarPorPerfil(String perfil);

	List<Perfil> listarTodos();

	Optional<Perfil> getByPerfil(String perfil);

}
