package es.udc.fi.lbd.monuzz.id.apps.daos;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.fi.lbd.monuzz.id.apps.model.App;
import es.udc.fi.lbd.monuzz.id.apps.model.Categoria;

@Repository
public class CategoriaDaoImpl implements CategoriaDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(Categoria miCategoria) {
		if (miCategoria.getIdCategoria()!=null){
			throw new RuntimeException("Alta categoria ya persistente");
		}
		Long id =(Long) sessionFactory.getCurrentSession().save(miCategoria);
		return id;
	}

	@Override
	public void remove(Categoria miCategoria) {
		if (miCategoria.getIdCategoria()==null){
			throw new RuntimeException("Baja categoria inexistente");
		}
		Query query = sessionFactory.getCurrentSession().createQuery("select count(a) from App a where a.categoria=:iCat");
		query.setParameter("iCat", miCategoria);
		Long r=(Long) query.uniqueResult();
		if (r==0){
			sessionFactory.getCurrentSession().delete(miCategoria);
		}else{
			throw new RuntimeException("Baja categoria con apps");
		}
	}

	@Override
	public void update(Categoria miCategoria) {
		sessionFactory.getCurrentSession().update(miCategoria);
	}

	@Override
	public Categoria findById(Long idCategoria) {
		Categoria categoria = (Categoria) sessionFactory.getCurrentSession().get(Categoria.class, idCategoria);
		return categoria;
	}

	@Override
	public Categoria findByNombre(String nombreCategoria) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Categoria c where c.nombre=:iNombre");
		query.setParameter("iNombre", nombreCategoria);
		return (Categoria) query.uniqueResult();
	}

	@Override
	public List<Categoria> findFirstLevel() {
		Query query = sessionFactory.getCurrentSession().createQuery("from Categoria c where c.madre is null order by c.nombre");
		List<Categoria> l = query.list();
		return l;
	}

	@Override
	public List<Categoria> findSubcategories(Categoria miCategoria) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Categoria c where c.madre=:iMadre order by c.nombre");
		query.setParameter("iMadre", miCategoria);
		List<Categoria> l = query.list();
		return l;
	}

	@Override
	public Long getNumApps(Categoria miCategoria) {
		Query query = sessionFactory.getCurrentSession().createQuery("select count(v) as num from Categoria v where v.madre=:iCat or v.idCategoria=:iID");
		query.setParameter("iCat", miCategoria);
		query.setParameter("iID", miCategoria.getIdCategoria());
		return (Long) query.uniqueResult();
	}

	@Override
	public List<App> getApps(Categoria miCategoria) {
		// TODO Auto-generated method stub
		return null;
	}

}
