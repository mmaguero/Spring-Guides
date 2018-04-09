package py.edu.ucsa.rest.api.core.services;
import java.util.List;
import py.edu.ucsa.rest.api.core.model.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO getById(long id);
    UsuarioDTO getByUsuario(String name);
    void crearUsuario(UsuarioDTO user);
    void actualizarUsuario(UsuarioDTO user);
    void eliminarUsuarioById(long id);
    List<UsuarioDTO> listarTodos();
    void eliminarTodos();
    boolean isExisteUsuario(UsuarioDTO user);
}
