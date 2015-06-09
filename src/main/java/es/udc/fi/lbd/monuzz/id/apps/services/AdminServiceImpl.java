package es.udc.fi.lbd.monuzz.id.apps.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.lbd.monuzz.id.apps.daos.AppDAO;
import es.udc.fi.lbd.monuzz.id.apps.daos.CategoriaDAO;
import es.udc.fi.lbd.monuzz.id.apps.daos.TipoAppDAO;
import es.udc.fi.lbd.monuzz.id.apps.model.App;
import es.udc.fi.lbd.monuzz.id.apps.model.Categoria;
import es.udc.fi.lbd.monuzz.id.apps.model.TipoApp;
import es.udc.fi.lbd.monuzz.id.apps.model.Usuario;

@Service
public class AdminServiceImpl implements AdminService{
	
	static Logger log = Logger.getLogger("apps");

	@Autowired
	private TipoAppDAO tipoApp;
	@Autowired
	private CategoriaDAO categoria;
	
	@Transactional(value="miTransactionManager")
	public void registrarNuevoTipoApp(TipoApp miTipo) {
		try{
			tipoApp.create(miTipo);
			log.info("Alta tipo app: " + tipoApp.toString());
		}catch (DataAccessException e){
			log.error("Error alta tipo app: " + tipoApp.toString());
			throw e;
		}
	}

	@Transactional(value="miTransactionManager")
	public void borrarTipoApp(TipoApp miTipo) {
		try{
			tipoApp.remove(miTipo);
		}catch(DataAccessException e){
			throw e;
		}
	}

	@Transactional(value="miTransactionManager")
	public TipoApp buscarTipoAppPorId(Long id) {
		TipoApp t=tipoApp.findById(id);
		if (t!=null)
			log.info("Find tipo app por id: " + t.toString());
		return t;
	}

	@Transactional(value="miTransactionManager")
	public TipoApp buscarTipoAppPorNombre(String nombre) {
		TipoApp t=tipoApp.findByNombre(nombre);
		if (t!=null)
			log.info("Find tipo app por nombre: " + t.toString());
		return t;
	}

	@Transactional(value="miTransactionManager")
	public List<TipoApp> buscarTodosTipoApp() {
		 List<TipoApp> t = tipoApp.findAll();
		return t;
	}

	@Transactional(value="miTransactionManager")
	public void registrarNuevaCategoria(Categoria miCategoria) {
		try{
			categoria.create(miCategoria);
			log.info("Alta categoria: " + categoria.toString());
		}catch (DataAccessException e){
			log.error("Error alta categoria: " + categoria.toString());
			throw e;
		}
	}

	@Transactional(value="miTransactionManager")
	public void modificarCategoria(Categoria miCategoria) {
		try{
			categoria.update(miCategoria);
			log.info("Update categoria: " + miCategoria.toString());
		}catch (DataAccessException e){
			log.error("Error update categoria: " + miCategoria.toString());
			throw e;
		}
	}

	@Transactional(value="miTransactionManager")
	public void borrarCategoria(Categoria miCategoria) {
		try{
			categoria.remove(miCategoria);
			log.info("Remove categoria: " + miCategoria.toString());
		}catch(DataAccessException e){
			log.error("Error Remove categoria: " + miCategoria.toString());
			throw e;
		}
	}

	@Transactional(value="miTransactionManager")
	public Categoria buscarCategoriaPorId(Long id) {
		Categoria c = categoria.findById(id);
		if(c!=null)
			log.info("Find categoria por id: " + c.toString());
		return c;
	}

	@Transactional(value="miTransactionManager")
	public Categoria buscarCategoriaPorNombre(String nombre) {
		Categoria c = categoria.findByNombre(nombre);
		if(c!=null)
			log.info("Find categoria por id: " + c.toString());
		return c;
	}

	@Transactional(value="miTransactionManager")
	public List<Categoria> buscarCategoriasPrincipales() {
		List<Categoria> c = categoria.findFirstLevel();
		return c;
	}

	@Transactional(value="miTransactionManager")
	public List<Categoria> buscarSubcategorias(Categoria miCategoria) {
		List<Categoria> c = categoria.findSubcategories(miCategoria);
		return c;
	}

	@Transactional(value="miTransactionManager")
	public Long calcularNumAppsCategoria(Categoria miCategoria) {
		Long l=categoria.getNumApps(miCategoria);
		return l;
	}

	@Transactional(value="miTransactionManager")
	public List<App> buscarAppsCategoria(Categoria miCategoria) {
		List<App> l = categoria.getApps(miCategoria);
		return l;
	}

}
