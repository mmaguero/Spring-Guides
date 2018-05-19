package py.edu.ucsa.rest.api.core.dao.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import py.edu.ucsa.rest.api.core.dao.AbstractDao;
import py.edu.ucsa.rest.api.core.dao.ConceptoCobroDao;
import py.edu.ucsa.rest.api.core.model.ConceptoCobro;

@Repository("conceptoCobroDao")

public class ConceptoCobroDaoImpl extends AbstractDao<ConceptoCobro> implements ConceptoCobroDao {

	@Override
	public Optional<ConceptoCobro> getById(long id) {
		return super.getById(id);
	}

}
