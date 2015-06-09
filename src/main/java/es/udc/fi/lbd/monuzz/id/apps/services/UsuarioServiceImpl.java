package es.udc.fi.lbd.monuzz.id.apps.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.lbd.monuzz.id.apps.daos.AppDAO;
import es.udc.fi.lbd.monuzz.id.apps.daos.UsuarioDAO;
import es.udc.fi.lbd.monuzz.id.apps.daos.VersionDAO;
import es.udc.fi.lbd.monuzz.id.apps.model.App;
import es.udc.fi.lbd.monuzz.id.apps.model.Cliente;
import es.udc.fi.lbd.monuzz.id.apps.model.Programador;
import es.udc.fi.lbd.monuzz.id.apps.model.Usuario;
import es.udc.fi.lbd.monuzz.id.apps.model.Version;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	static Logger log = Logger.getLogger("apps");
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	@Autowired
	private AppDAO appDAO;
	@Autowired
	private VersionDAO versionDAO;
	
	
	@Transactional(value="miTransactionManager")
	public void registrarNuevoUsuario(Usuario miUsuario) {
		try{
			usuarioDAO.create(miUsuario);
			log.info("Alta usuario: " + miUsuario.toString());
		}catch (DataAccessException e){
			log.error("Error alta usuario");
			throw e;
		}
	}

	@Transactional(value="miTransactionManager")
	public void actualizarUsuario(Usuario miUsuario) {
		try{
			usuarioDAO.update(miUsuario);
			log.info("Update usuario: " + miUsuario.toString());
		}catch (DataAccessException e){
			log.error("Error update usuario: " + miUsuario.toString());
			throw e;
		}
	}

	@Transactional(value="miTransactionManager")
	public void borrarUsuario(Usuario miUsuario) {
		List<Cliente> c = usuarioDAO.findAllClientes();
		if(c.contains(miUsuario)){
			try{
				usuarioDAO.remove(miUsuario);
			}catch(DataAccessException e){
				log.error("Borrar programador con apps asociadas");
				throw e;
			}
		}
		List<Programador> p = usuarioDAO.findAllProgramadores();
		if(p.contains(miUsuario)){
			try{
				usuarioDAO.remove(miUsuario);
			}catch(DataAccessException e){
				log.error("Borrar programador con apps asociadas");
				throw e;
			}
		}
	}

	@Transactional(value="miTransactionManager")
	public Usuario autenticarUsuario(String login, String password) {
		Usuario u=usuarioDAO.findByNombreDeUsuario(login);
		if(u.getPassword().equals(password)){
			log.info("Auth usuario: " + u.toString());
			return u;
		}else{
			log.info("Auth error usuario: " + u.toString());
			return null;
		}
	}

	@Transactional(value="miTransactionManager")
	public Usuario buscarUsuarioPorId(Long id) {
		try{
			Usuario u=usuarioDAO.findById(id);
			if (u!=null)
				log.info("Find usuario: " + u.toString());
			return u;
		}catch (DataAccessException e){
			log.error("Error Find usuario: " + id.toString());
			throw e;
		}
	}

	@Transactional(value="miTransactionManager")
	public Usuario buscarUsuarioPorLogin(String login) {
		try{
			Usuario u=usuarioDAO.findByNombreDeUsuario(login);
			if (u!=null)
				log.info("Find login usuario: " + u.toString());
			return u;
		}catch (DataAccessException e){
			log.error("Error Find login usuario: " + login.toString());
			throw e;
		}
	}

	@Transactional(value="miTransactionManager")
	public List<Usuario> obtenerListaUsuarios() {
		return usuarioDAO.findAll();
	}

	@Transactional(value="miTransactionManager")
	public List<Cliente> obtenerListaClientes() {
		return usuarioDAO.findAllClientes();
	}

	@Transactional(value="miTransactionManager")
	public List<Programador> obtenerListaProgramadores() {
		return usuarioDAO.findAllProgramadores();
	}

	@Transactional(value="miTransactionManager")
	public void registrarApp(App miApp) {
		try{
			appDAO.create(miApp);
			log.info("Alta app: " + miApp.getIdApp());
		}catch (DataAccessException e){
			log.error("Error alta app: " + miApp.toString());
			throw e;
		}
	}

	@Transactional(value="miTransactionManager")
	public void actualizarApp(App miApp) {
		try{
			log.info("Update app: " + miApp.getIdApp());
			appDAO.update(miApp);
		}catch (DataAccessException e){
			log.error("Error update app: " + miApp.toString());
			throw e;
		}
	}

	@Transactional(value="miTransactionManager")
	public void borrarApp(App miApp) {
		try{
			appDAO.remove(miApp);
		}catch(DataAccessException e){
			log.error("clientes ligados a la App");
			throw e;
		}
		
	}

	@Transactional(value="miTransactionManager")
	public App buscarAppPorId(Long id) {
		App a=appDAO.findById(id);
		return a;
	}

	@Transactional(value="miTransactionManager")
	public App buscarAppPorTitulo(String miTitulo) {
		App a=appDAO.findByTitulo(miTitulo);
		return a;
	}

	@Transactional(value="miTransactionManager")
	public List<App> obtenerAppsProgramador(Programador miProgramador) {
		return appDAO.findAllByProgramador(miProgramador);
	}

	@Transactional(value="miTransactionManager")
	public List<App> obtenerAppsCliente(Cliente miCliente) {
		return appDAO.findAllByCliente(miCliente);
	}

	@Transactional(value="miTransactionManager")
	public List<Cliente> obtenerClientesApp(App miApp) {
		return appDAO.findAllClientes(miApp);
	}

	@Transactional(value="miTransactionManager")
	public void cancelarClientes(App miApp) {
		List<Cliente> c = appDAO.findAllClientes(miApp);
		for(int i=0;i<c.size();i++){
			c.get(i).getApps().remove(miApp);
		}
	}

	@Transactional(value="miTransactionManager")
	public void registrarNuevaVersion(Version miVersion) {
		try{
			versionDAO.create(miVersion);
			log.info("Alta app: " + miVersion.getIdVersion());
		}catch (DataAccessException e){
			log.error("Error alta app: " + miVersion.getNumVersion());
			throw e;
		}
	}

	@Transactional(value="miTransactionManager")
	public void borrarVersion(Version miVersion) {
		try{
			versionDAO.remove(miVersion);
		}catch(DataAccessException e){
			log.error("Error borrar version. Ultima version");
			throw e;
		}
	}

	@Transactional(value="miTransactionManager")
	public Version BuscarVersionPorId(Long Id) {
		return versionDAO.findById(Id);
	}

	@Transactional(value="miTransactionManager")
	public List<Version> obtenerListaVersiones(App miApp) {
		return versionDAO.findAllByApp(miApp);
	}

	@Transactional(value="miTransactionManager")
	public Version obtenerUltimaVersion(App miApp) {
		List<Version> v = versionDAO.findAllByApp(miApp);
		return v.get(0);
	}

}
