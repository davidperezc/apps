package es.udc.fi.lbd.monuzz.id.apps.daos;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.fi.lbd.monuzz.id.apps.model.App;
import es.udc.fi.lbd.monuzz.id.apps.model.Categoria;
import es.udc.fi.lbd.monuzz.id.apps.model.Cliente;
import es.udc.fi.lbd.monuzz.id.apps.model.Programador;
import es.udc.fi.lbd.monuzz.id.apps.model.Usuario;

@Repository
public class AppDaoImpl implements AppDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(App miApp) {
		if (miApp.getIdApp()!=null){
			throw new RuntimeException("Alta app ya persistente");
		}
		Long id =(Long) sessionFactory.getCurrentSession().save(miApp);
		return id;
	}

	@Override
	public void update(App miApp) {
		sessionFactory.getCurrentSession().update(miApp);		
	}

	@Override
	public void remove(App miApp) {
		App app = (App) sessionFactory.getCurrentSession().get(App.class, miApp.getIdApp());
		sessionFactory.getCurrentSession().delete(app);
		
	}

	@Override
	public App findById(Long id) {
		App app = (App) sessionFactory.getCurrentSession().get(App.class, id);
		return app;
	}

	@Override
	public App findByTitulo(String miTitulo) {
		Query query = sessionFactory.getCurrentSession().createQuery("from App a where a.titulo=:iTitulo");
		query.setParameter("iTitulo", miTitulo);
		return (App) query.uniqueResult();
	}

	@Override
	public List<App> findAllByProgramador(Programador miProgramador) {
		Query query = sessionFactory.getCurrentSession().createQuery("from App a where a.autor=:iProgram");
		query.setParameter("iProgram", miProgramador.getIdUsuario());
		List<App> l = query.list();
		return l;
	}

	@Override
	public List<App> findAllByCliente(Cliente miCliente) {
		Query query = sessionFactory.getCurrentSession().createQuery("from CLIENTE_APP c where c.ID_APP=iID");
		query.setParameter("iID", miCliente.getIdUsuario());
		List<App> l = query.list();
		return l;
	}

	@Override
	public List<App> findAllByCategoria(Categoria miCategoria) {
		Query query = sessionFactory.getCurrentSession().createQuery("from App a where a.categoria=:iCat");
		query.setParameter("iCat", miCategoria.getIdCategoria());
		List<App> l = query.list();
		return l;
	}

	@Override
	public List<Cliente> findAllClientes(App miApp) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Cliente c join c.apps a where a.idApp=:iID");
		query.setParameter("iID", miApp.getIdApp());
		List<Cliente> l = query.list();
		return l;
	}

}
