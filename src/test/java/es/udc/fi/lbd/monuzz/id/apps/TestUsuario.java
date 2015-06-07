package es.udc.fi.lbd.monuzz.id.apps;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.fi.lbd.monuzz.id.apps.model.*;
import es.udc.fi.lbd.monuzz.id.apps.services.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class TestUsuario {
	
	private Logger log = Logger.getLogger("blogs");

	@Autowired
	private TestUtils testUtils;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AdminService adminService;


	@Before
	public void setUp() throws Exception { 
		log.info ("Inicializando datos para caso de prueba: " + this.getClass().getName());
		testUtils.creaSetDatosPrueba();
		log.info ("Datos inicializados con éxito");
	}

	@After
	public void tearDown() throws Exception {
		log.info ("Eliminando datos para caso de prueba: " + this.getClass().getName());
		testUtils.eliminaSetDatosPrueba();  
		log.info ("Datos eliminados con éxito");
	}
	
	@Test
	public void testBasicoUsuario() {
		
		// T1. Crear usuario y registrarlo 
		Programador miProgramador = new Programador("programadorTest", "test13", "Nikito", "Nipongo", "Camacho", "Nikito Nipongo");
		usuarioService.registrarNuevoUsuario(miProgramador);		
		assertNotNull(miProgramador.getIdUsuario());
		assertEquals(miProgramador, (Programador) usuarioService.buscarUsuarioPorId(miProgramador.getIdUsuario()));
		assertEquals(miProgramador, (Programador) usuarioService.buscarUsuarioPorLogin(miProgramador.getNombreDeUsuario()));
				
		Cliente miCliente = new Cliente ("clienteTest", "test13", "Nokito", "Nada", "Nada", "Nokito Nada", "Premium", new Float(5000));
		usuarioService.registrarNuevoUsuario(miCliente);		
		assertNotNull(miCliente.getIdUsuario());
		assertNotNull(miCliente.getIdUsuario());
		assertEquals(miCliente, (Cliente) usuarioService.buscarUsuarioPorId(miCliente.getIdUsuario()));
		assertEquals(miCliente, (Cliente) usuarioService.buscarUsuarioPorLogin(miCliente.getNombreDeUsuario()));

		
		// T2. Registrar usuario duplicado
		/*
		Boolean duplicado=false;
		try {usuarioService.registrarNuevoUsuario(miProgramador);} 
			catch (DataIntegrityViolationException e) {duplicado=true;}
		assertTrue(duplicado);

		duplicado=false;
		try {usuarioService.registrarNuevoUsuario(miCliente);} 
			catch (DataIntegrityViolationException e) {duplicado=true;}
		assertTrue(duplicado);
		*/
		
		// T3. Comprobamos que la autenticación funciona
		
		Cliente miClienteAutenticado = (Cliente) usuarioService.autenticarUsuario(miCliente.getNombreDeUsuario(), miCliente.getPassword());
		assertNotNull(miClienteAutenticado);
		assertEquals(miCliente, miClienteAutenticado);
		
		Programador miProgramadorAutenticado = (Programador) usuarioService.autenticarUsuario(miProgramador.getNombreDeUsuario(), miProgramador.getPassword());
		assertNotNull(miProgramadorAutenticado);
		assertEquals(miProgramador, miProgramadorAutenticado);

		// T4. Probar autenticación erronea
		
		Usuario miFallidoUsuarioAutenticado = usuarioService.autenticarUsuario(miCliente.getNombreDeUsuario(), "patata");
		assertNull(miFallidoUsuarioAutenticado);
		
		miFallidoUsuarioAutenticado = usuarioService.autenticarUsuario(miProgramador.getNombreDeUsuario(), "patata");
		assertNull(miFallidoUsuarioAutenticado);
		
		
		// T5 Probar cambio datos cliente y programador
		
		miClienteAutenticado.setPassword("passwordnuevecito");
		miClienteAutenticado.setSaldo(new Float(12345)); 
		usuarioService.actualizarUsuario(miClienteAutenticado);
		assertEquals (miClienteAutenticado.getPassword(), (usuarioService.buscarUsuarioPorLogin(miClienteAutenticado.getNombreDeUsuario())).getPassword());
		assertEquals (miClienteAutenticado.getSaldo(), ((Cliente)(usuarioService.buscarUsuarioPorLogin(miClienteAutenticado.getNombreDeUsuario()))).getSaldo());

		miProgramadorAutenticado.setPassword("passwordnuevecito");
		miProgramadorAutenticado.setNumVotos(new Long(99)); 
		usuarioService.actualizarUsuario(miProgramadorAutenticado);
		assertEquals (miProgramadorAutenticado.getPassword(), (usuarioService.buscarUsuarioPorLogin(miProgramadorAutenticado.getNombreDeUsuario())).getPassword());
		assertEquals (miProgramadorAutenticado.getNumVotos(), ((Programador)usuarioService.buscarUsuarioPorLogin(miProgramadorAutenticado.getNombreDeUsuario())).getNumVotos());

		// T6. Probar baja de cliente y programador (sin apps asociadas)
		
		usuarioService.borrarUsuario(miClienteAutenticado);
		assertNull(usuarioService.buscarUsuarioPorId(miClienteAutenticado.getIdUsuario()));
		assertNull(usuarioService.buscarUsuarioPorLogin(miClienteAutenticado.getNombreDeUsuario()));
		
		usuarioService.borrarUsuario(miProgramadorAutenticado);
		assertNull(usuarioService.buscarUsuarioPorId(miProgramadorAutenticado.getIdUsuario()));
		assertNull(usuarioService.buscarUsuarioPorLogin(miProgramadorAutenticado.getNombreDeUsuario()));
		
		
		
		
		// T7 Probar listado de usuarios
		
		List<Usuario> miListaU = usuarioService.obtenerListaUsuarios();
		assertEquals(4, miListaU.size());
		assertEquals(testUtils.cliente1, miListaU.get(0));
		assertEquals(testUtils.programador1, miListaU.get(1));
		assertEquals(testUtils.cliente2, miListaU.get(2));
		assertEquals(testUtils.programador2, miListaU.get(3));
		miListaU.clear();
		
		List<Cliente> miListaC = usuarioService.obtenerListaClientes();
		assertEquals(2, miListaC.size());
		assertEquals(testUtils.cliente1, miListaC.get(0));
		assertEquals(testUtils.cliente2, miListaC.get(1));
		miListaC.clear();
	
		List<Programador> miListaP = usuarioService.obtenerListaProgramadores();
		assertEquals(2, miListaP.size());
		assertEquals(testUtils.programador1, miListaP.get(0));
		assertEquals(testUtils.programador2, miListaP.get(1));
		miListaP.clear();


	}


	@Test
	public void testProgramadorCliente() {
	
		// T1 Añadir nueva aplicación con su version inicial. Añadir segunda versión
		
		App miApp = new App("AppTest", new Timestamp(Calendar.getInstance().getTimeInMillis()), new Float(0), testUtils.programador1, testUtils.cat11, testUtils.tipoFreeware);
		Version miVersion = new Version("v1.0", new Timestamp(Calendar.getInstance().getTimeInMillis()), miApp);
		usuarioService.registrarApp(miApp);
		assertNotNull(miApp.getIdApp());
		assertEquals (miApp, usuarioService.buscarAppPorId(miApp.getIdApp()));
		assertEquals (miVersion, usuarioService.obtenerUltimaVersion(miApp));
		miVersion=usuarioService.obtenerUltimaVersion(miApp);
		
		Version miVersion2 = new Version("v2.0", new Timestamp(Calendar.getInstance().getTimeInMillis()), miApp);
		usuarioService.registrarNuevaVersion(miVersion2);
		assertNotNull(miVersion2.getIdVersion());
		assertEquals (miVersion2, usuarioService.obtenerUltimaVersion(miApp));
		
		
		// T2 Listar versiones (la más reciente primero)
		List<Version> miLista = usuarioService.obtenerListaVersiones(miApp);
		assertEquals(2, miLista.size());
		assertEquals (miVersion2, miLista.get(0));
		assertEquals (miVersion, miLista.get(1));
		miLista.clear();
		
		
		// T3 Actualizar App
		
		miApp.setTitulo("App con titulo nuevo");
		miApp.setVotos(miApp.getVotos()+1);
		usuarioService.actualizarApp(miApp);
		assertEquals(miApp.getTitulo(), usuarioService.buscarAppPorId(miApp.getIdApp()).getTitulo());
		assertEquals(miApp.getVotos(), usuarioService.buscarAppPorId(miApp.getIdApp()).getVotos());
		
		// T4 Dar de baja versión
		
		miApp.getVersiones().remove(miVersion2);
		usuarioService.borrarVersion(miVersion2);
		miLista = usuarioService.obtenerListaVersiones(miApp);
		assertEquals(1, miLista.size());
		assertEquals (miVersion, miLista.get(0));
		miLista.clear();
		
		// T5 Fallar al dar de baja la última versión
		
		Boolean ultimaVersion=false;
		try { usuarioService.borrarVersion(miVersion); }
			catch (Exception e) {ultimaVersion=true;}
		assertTrue(ultimaVersion);
		/*
		// T6 Fallar al dar de baja aplicacion con clientes
		
		testUtils.cliente1.getApps().add(miApp);
		usuarioService.actualizarUsuario(testUtils.cliente1);
		assertEquals (2, usuarioService.obtenerAppsCliente(testUtils.cliente1).size());
		
		Boolean appConClientes=false;
		try { usuarioService.borrarApp(miApp);}
		catch (DataIntegrityViolationException e) { appConClientes=true;}
		assertTrue (appConClientes);
       
		
		// T7 Fallar al dar de baja programador con apps
		
		Boolean progConApps=false;
		try {usuarioService.borrarUsuario(testUtils.programador1);}
		catch (DataIntegrityViolationException e) { progConApps=true;}
		assertTrue (progConApps);
		 */
		// T8 Probar baja de cliente con apps
		
		Cliente miCliente = new Cliente ("clienteTest", "test13", "Nokito", "Nada", "Nada", "Nokito Nada", "Premium", new Float(5000));
		usuarioService.registrarNuevoUsuario(miCliente);		
		miCliente.getApps().add(miApp);
		usuarioService.actualizarUsuario(miCliente);
		
		//assertEquals (2, usuarioService.obtenerClientesApp(miApp).size());
		usuarioService.borrarUsuario(miCliente);
		//assertEquals (1, usuarioService.obtenerClientesApp(miApp).size());
		
		
		// T9 Cancelar los clientes de una aplicación
		/*
		assertEquals (2, usuarioService.obtenerAppsCliente(testUtils.cliente1).size());
		assertEquals (1, usuarioService.obtenerClientesApp(miApp).size());
		usuarioService.cancelarClientes(miApp);
		assertEquals (1, usuarioService.obtenerAppsCliente(testUtils.cliente1).size());
		assertEquals (0, usuarioService.obtenerClientesApp(miApp).size());
		*/
		// T10 Eliminar app sin clientes (y sus versiones)
		
		usuarioService.borrarApp(miApp);
		assertNull(usuarioService.buscarAppPorId(miApp.getIdApp()));
		assertNull(usuarioService.buscarAppPorTitulo(miApp.getTitulo()));
		assertNull(usuarioService.BuscarVersionPorId(miVersion.getIdVersion()));
		
	}

		


}
