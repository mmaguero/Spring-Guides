package py.edu.ucsa.rest.api.core.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractDao<T> {
	private final Class<T> persistentClass;
	protected Logger logger = null;

	@SuppressWarnings("unchecked")
	public AbstractDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		this.logger = LoggerFactory.getLogger(persistentClass);
	}

	@PersistenceContext
	EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	protected Optional<T> getById(long id) {
		// return (T) entityManager.find(persistentClass, id);
		return Optional.ofNullable(entityManager.find(persistentClass, id));
	}

	protected void persistir(T entity) {
		entityManager.persist(entity);
	}

	protected void actualizar(T entity) {
		entityManager.merge(entity);
	}

	protected void eliminar(T entity) {
		entityManager.remove(entity);
	}
}
