package py.edu.ucsa.rest.api.core.dao;

import java.util.Optional;

import py.edu.ucsa.rest.api.core.model.ConceptoCobro;

public interface ConceptoCobroDao {
	
	Optional<ConceptoCobro> getById(long id);

}
