package es.udc.fi.lbd.monuzz.id.apps.daos;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.fi.lbd.monuzz.id.apps.model.App;
import es.udc.fi.lbd.monuzz.id.apps.model.Version;

@Repository
public class VersionDaoImpl implements VersionDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(Version miVersion) {
		if (miVersion.getIdVersion()!=null){
			throw new RuntimeException("Alta version ya persistente");
		}
		Long id =(Long) sessionFactory.getCurrentSession().save(miVersion);
		return id;
	}

	@Override
	public void update(Version miVersion) {
		sessionFactory.getCurrentSession().update(miVersion);
		
	}

	@Override
	public void remove(Version miVersion) {
		List<Version> v = findAllByApp(miVersion.getApp());
		Version ver = (Version) sessionFactory.getCurrentSession().get(Version.class, miVersion.getIdVersion());
		if (v.size()>1){
			sessionFactory.getCurrentSession().delete(ver);
		}else{
			throw new RuntimeException("Error borrar version");
		}
	}

	@Override
	public Version findById(Long id) {
		Version version = (Version) sessionFactory.getCurrentSession().get(Version.class, id);
		return version;
	}

	@Override
	public List<Version> findAllByApp(App miApp) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Version v where v.app.idApp=:iApp order by v.idVersion desc");
		query.setParameter("iApp", miApp.getIdApp());
		List<Version> lista = query.list();
		return lista;
	}

	@Override
	public long getNumVersiones(App miApp) {
		Query query = sessionFactory.getCurrentSession().createQuery("select count(v) from Version v where v.app.idApp=:iApp");
		Long version = (Long) query.setParameter("iApp", miApp.getIdApp()).uniqueResult();
		return version;
	}

}
