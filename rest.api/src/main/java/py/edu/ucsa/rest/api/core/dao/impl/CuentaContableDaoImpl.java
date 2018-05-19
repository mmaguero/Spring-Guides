package py.edu.ucsa.rest.api.core.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import py.edu.ucsa.rest.api.core.dao.AbstractDao;
import py.edu.ucsa.rest.api.core.dao.CuentaContableDao;
import py.edu.ucsa.rest.api.core.model.CuentaContable;

@Repository("cuentaContableDao")
public class CuentaContableDaoImpl extends AbstractDao<CuentaContable> implements CuentaContableDao {
	@Override
	public Optional<CuentaContable> listarById(long id) {
		Optional<CuentaContable> u = super.getById(id);
		/*if (u.isPresent()) {
			initializeCollection(u.get().hijas);
		}*/
		return u;
	}

	@Override
	public void eliminarCuenta(long id) {
		CuentaContable u = (CuentaContable) getEntityManager()
				.createQuery("SELECT u FROM CuentaContable u WHERE u.id = :id").setParameter("id", id)
				.getSingleResult();
		eliminar(u);
	}

	@Override
	public Optional<CuentaContable> listarByCodigo(String codigo) {
		logger.debug("CuentaContable: " + codigo);
		try {
			Optional<CuentaContable> u = Optional.ofNullable((CuentaContable) getEntityManager()
					.createQuery("SELECT u FROM CuentaContable u WHERE u.codigo = :codigo")
					.setParameter("codigo", codigo).getSingleResult());
			/*if (u.isPresent()) {
				initializeCollection(u.get().getCuentasHijas());
			}*/
			return u;
		} catch (NoResultException ex) {
			return Optional.empty();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CuentaContable> listarTodos() {
		List<CuentaContable> usuarios = getEntityManager()
				.createQuery("SELECT u FROM CuentaContable u ORDER BY u.codigo ASC").getResultList();
		return usuarios;
	}

	@Override
	public void insertarCuenta(CuentaContable cc) {
		super.persistir(cc);
	}

	@Override
	public void actualizarCuenta(CuentaContable cc) {
		super.actualizar(cc);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CuentaContable> listarPorTipo(String tipo) {
		logger.debug("CuentaContable: " + tipo);
		try {
			List<CuentaContable> u = getEntityManager()
					.createQuery("SELECT u FROM CuentaContable u WHERE u.tipoCuenta = :tipoCuenta")
					.setParameter("tipoCuenta", tipo).getResultList();
			return u;
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CuentaContable> listarPorNroCuenta(String nroCuenta) {
		logger.debug("CuentaContable: " + nroCuenta);
		try {
			List<CuentaContable> u = getEntityManager()
					.createQuery("SELECT u FROM CuentaContable u WHERE u.numeroCuenta = :numeroCuenta")
					.setParameter("numeroCuenta", nroCuenta).getResultList();
			return u;
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CuentaContable> listarCuentasHijas(CuentaContable nroCuentaPadre) {
		logger.debug("CuentaContable: " + nroCuentaPadre);
		try {
			List<CuentaContable> u = getEntityManager()
					.createQuery("SELECT u FROM CuentaContable u WHERE u.cuentaPadre = :cuentaPadre")
					.setParameter("cuentaPadre", nroCuentaPadre.getNumeroCuenta()).getResultList();
			return u;
		} catch (NoResultException ex) {
			return null;//new ArrayList<>();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CuentaContable> listarAsentables() {
		List<CuentaContable> usuarios = getEntityManager()
				.createQuery("SELECT u FROM CuentaContable u WHERE u.asentable = TRUE ORDER BY u.codigo ASC")
				.getResultList();
		return usuarios;
	}

	@Override
	public void eliminarCuentaPorCodigo(String codigo) {
		CuentaContable u = (CuentaContable) getEntityManager()
				.createQuery("SELECT u FROM CuentaContable u WHERE u.codigo = :codigo").setParameter("codigo", codigo)
				.getSingleResult();
		eliminar(u);

	}

	// Una alternativa a Hibernate.initialize()
	protected void initializeCollection(Collection<?> collection) {
		if (collection == null) {
			return;
		}
		collection.iterator().hasNext();
	}
}
