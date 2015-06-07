package es.udc.fi.lbd.monuzz.id.apps.daos;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.fi.lbd.monuzz.id.apps.model.Programador;
import es.udc.fi.lbd.monuzz.id.apps.model.TipoApp;

@Repository
public class TipoAppDaoImpl implements TipoAppDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(TipoApp miTipo) {
		if (miTipo.getIdTipoApp()!=null){
			throw new RuntimeException("Alta tipoapp ya persistente");
		}
		Long id =(Long) sessionFactory.getCurrentSession().save(miTipo);
		return id;
	}

	@Override
	public void remove(TipoApp miTipo) {
		if (miTipo.getIdTipoApp()==null){
			throw new RuntimeException("Baja tipoapp inexistente");
		}
		Query query = sessionFactory.getCurrentSession().createQuery("select count(a) from App a where a.tipoApp=:iTipo");
		query.setParameter("iTipo", miTipo);
		Long t=(Long) query.uniqueResult();
		if (t==0){
			sessionFactory.getCurrentSession().delete(miTipo);
		}else{
			throw new RuntimeException("Baja tipo de app con apps");
		}
	}

	@Override
	public TipoApp findById(Long Id) {
		TipoApp tipo = (TipoApp) sessionFactory.getCurrentSession().get(TipoApp.class, Id);
		return tipo;
	}

	@Override
	public TipoApp findByNombre(String nombre) {
		Query query = sessionFactory.getCurrentSession().createQuery("from TipoApp t where t.nombre=:iNombre");
		query.setParameter("iNombre", nombre);
		TipoApp t = (TipoApp) query.uniqueResult();
		return t;
	}

	@Override
	public List<TipoApp> findAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("from TipoApp t order by t.nombre");
		List<TipoApp> l = query.list();
		return l;
	}

}
