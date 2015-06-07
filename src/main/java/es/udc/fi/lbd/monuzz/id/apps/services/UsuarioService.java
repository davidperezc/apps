package es.udc.fi.lbd.monuzz.id.apps.services;
import java.util.List;






import es.udc.fi.lbd.monuzz.id.apps.model.App;
import es.udc.fi.lbd.monuzz.id.apps.model.Cliente;
import es.udc.fi.lbd.monuzz.id.apps.model.Programador;
import es.udc.fi.lbd.monuzz.id.apps.model.Usuario;
import es.udc.fi.lbd.monuzz.id.apps.model.Version;

public interface UsuarioService {
		
	void registrarNuevoUsuario (Usuario miUsuario);
	void actualizarUsuario (Usuario miUsuario);
	void borrarUsuario (Usuario miUsuario);		// Salta excepcion si aún hay apps ligadas a un programador. Para los clientes, cancela sus subscripciones y borra los clientes
	Usuario autenticarUsuario (String login, String password);
	Usuario buscarUsuarioPorId(Long id);
	Usuario buscarUsuarioPorLogin(String login);
	List<Usuario> obtenerListaUsuarios();
	List<Cliente> obtenerListaClientes();
	List<Programador> obtenerListaProgramadores();
	
	void registrarApp (App miApp);
	void actualizarApp (App miApp);
	void borrarApp(App miApp);					// Salta excepcion si aun hay clientes ligados a la App
	App buscarAppPorId(Long id);
	App buscarAppPorTitulo(String miTitulo);
	List<App> obtenerAppsProgramador (Programador miProgramador);
	List<App> obtenerAppsCliente (Cliente miCliente);
	List<Cliente> obtenerClientesApp (App miApp);
	void cancelarClientes (App miApp);	
	
	void registrarNuevaVersion (Version miVersion);
	void borrarVersion (Version miVersion);		// Salta excepcion si es la última versión de una app: no se debería borrar
	Version BuscarVersionPorId (Long Id);
	List<Version> obtenerListaVersiones(App miApp);
	Version obtenerUltimaVersion (App miApp);

	
	
	
}
