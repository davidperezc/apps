package es.udc.fi.lbd.monuzz.id.apps.daos;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.fi.lbd.monuzz.id.apps.model.App;
import es.udc.fi.lbd.monuzz.id.apps.model.Cliente;
import es.udc.fi.lbd.monuzz.id.apps.model.Programador;
import es.udc.fi.lbd.monuzz.id.apps.model.Usuario;

@Repository
public class UsuarioDaoImpl implements UsuarioDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(Usuario miUsuario) {
		Long id =(Long) sessionFactory.getCurrentSession().save(miUsuario);
		return id;
	}

	@Override
	public void remove(Usuario miUsuario) {
		Cliente user = (Cliente) sessionFactory.getCurrentSession().get(Cliente.class, miUsuario.getIdUsuario());
		if(user!=null){
			user.setApps(null);
			sessionFactory.getCurrentSession().delete(user);
			return;
		}
		Programador user2 = (Programador) sessionFactory.getCurrentSession().get(Programador.class, miUsuario.getIdUsuario());
		if(user2!=null){
			sessionFactory.getCurrentSession().delete(user2);
		}
	}

	@Override
	public void update(Usuario miUsuario) {
		sessionFactory.getCurrentSession().update(miUsuario);
	}

	@Override
	public Usuario findById(Long id) {
		Usuario user = (Usuario) sessionFactory.getCurrentSession().get(Usuario.class, id);
		return user;
	}

	@Override
	public Usuario findByNombreDeUsuario(String nombreDeUsuario) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Usuario u where u.nombreDeUsuario=:iUsuario");
		query.setParameter("iUsuario", nombreDeUsuario);
		return (Usuario) query.uniqueResult();
	}

	@Override
	public List<Usuario> findAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("from Usuario u order by u.password desc");
		List<Usuario> l = query.list();
		return l;
	}

	@Override
	public List<Cliente> findAllClientes() {
		Query query = sessionFactory.getCurrentSession().createQuery("from Cliente");
		List<Cliente>  l = query.list();
		return l;
	}

	@Override
	public List<Programador> findAllProgramadores() {
		Query query = sessionFactory.getCurrentSession().createQuery("from Programador");
		List<Programador> l = query.list();
		return l;
	}

}
