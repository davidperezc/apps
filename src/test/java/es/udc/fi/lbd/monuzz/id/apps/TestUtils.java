package es.udc.fi.lbd.monuzz.id.apps;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import es.udc.fi.lbd.monuzz.id.apps.model.*;
import es.udc.fi.lbd.monuzz.id.apps.services.*;

public class TestUtils {

	@Autowired
	private AdminService adminService;
	@Autowired
	private UsuarioService usuarioService;
	
	
	public final long timeout = 50;
	
	public TipoApp tipoFreeware;
	public TipoApp tipoShareware;
	public TipoApp tipoPayware;

	public Categoria cat1;
	public Categoria cat11;
	public Categoria cat12;
	public Categoria cat2;
	public Categoria cat21;
	public Categoria cat22;
	public Categoria cat3;
	
	public Programador programador1;
	public Programador programador2;
	public Cliente cliente1;
	public Cliente cliente2;
	
	public App app1;
	public App app2;
	public App app3;
	public App app4;
	
	public Version version11;
	public Version version12;
	public Version version21;
	public Version version31;
	public Version version41;
	
	
	public void creaSetDatosPrueba() {

		/* Insertamos tipos de aplicación */

		tipoFreeware  = new TipoApp("Freeware", "Libre uso y distribucion", null);
		tipoShareware = new TipoApp("Shareware", "Prueba y después pago", null);
		tipoPayware   = new TipoApp("Payware", "De plago", null);
	
		adminService.registrarNuevoTipoApp(tipoFreeware);
		adminService.registrarNuevoTipoApp(tipoShareware);
		adminService.registrarNuevoTipoApp(tipoPayware);

		
		/* Insertamos categorias principales. Asumimos propagacion activada en mappings para el resto */

		cat1  = new Categoria("Categoria 1");
		cat11 = new Categoria("Categoria 1.1");
		cat12 = new Categoria("Categoria 1.2");
		cat2  = new Categoria("Categoria 2");
		cat21 = new Categoria("Categoria 2.1");
		cat22 = new Categoria("Categoria 2.2");
		
		cat1.addSubcategoria(cat11);
		cat1.addSubcategoria(cat12);
		cat2.addSubcategoria(cat21);
		cat2.addSubcategoria(cat22);
		
		
		adminService.registrarNuevaCategoria(cat1);
		adminService.registrarNuevaCategoria(cat2);
		
		/* Insertamos programadores, inicialmente sin aplicaciones */
	
		programador1 = new Programador("monuzz", "monuzz13", "Mon", "López", "Rodríguez", "Mon López");
		programador2 = new Programador("mi", "mi13", "Miguel", "Rodríguez", "Penabad", "Miguel Penabad");

		usuarioService.registrarNuevoUsuario(programador1);
		usuarioService.registrarNuevoUsuario(programador2);

		/* Insertamos aplicaciones con sus versiones iniciales. Solo se insertan las apps: asumimos propagación activada para las versiones */
		
		app1 = new App("App1", new Timestamp(Calendar.getInstance().getTimeInMillis()), new Float(0.95), programador1, cat11, tipoPayware);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		app2 = new App("App2", new Timestamp(Calendar.getInstance().getTimeInMillis()), new Float(0.55), programador1, cat11, tipoPayware);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		app3 = new App("App3", new Timestamp(Calendar.getInstance().getTimeInMillis()), new Float(0), programador2, cat12, tipoFreeware);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		app4 = new App("App4", new Timestamp(Calendar.getInstance().getTimeInMillis()), new Float(0), programador2, cat21, tipoFreeware);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}

		version11 = new Version("v1.1", new Timestamp(Calendar.getInstance().getTimeInMillis()), app1);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		version12 = new Version("v1.2", new Timestamp(Calendar.getInstance().getTimeInMillis()), app1);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		version21 = new Version("v2.1", new Timestamp(Calendar.getInstance().getTimeInMillis()), app2);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		version31 = new Version("v3.1", new Timestamp(Calendar.getInstance().getTimeInMillis()), app3);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		version41 = new Version("v4.1", new Timestamp(Calendar.getInstance().getTimeInMillis()), app4);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}

		app3.setVotos(new Long(1000));
		app1.setVotos(new Long(100));
		app2.setVotos(new Long(10));
		
		
		usuarioService.registrarApp(app1);
		usuarioService.registrarApp(app2);
		usuarioService.registrarApp(app3);
		usuarioService.registrarApp(app4);

		/* Registramos clientes */ 

		cliente1 = new Cliente ("cmonuzz", "monuzz13", "Climon", "López", "Rodríguez", "Climon López", "Premium", new Float(1000));
		cliente2 = new Cliente ("cmi", "mi13", "Climiguel", "Rodríguez", "Penabad", "Climiguel Penabad", "Normalillo", new Float(30));

		//cliente1.getApps().add(app1);
		
		usuarioService.registrarNuevoUsuario(cliente1);
		usuarioService.registrarNuevoUsuario(cliente2);
		
	}
	
	public void eliminaSetDatosPrueba() {


		

		usuarioService.cancelarClientes(app1);
		usuarioService.cancelarClientes(app2);
		usuarioService.cancelarClientes(app3);
		usuarioService.cancelarClientes(app4);

		// Borramos Apps. Asumimos versiones borradas por propagacion

		usuarioService.borrarApp(app1);
		usuarioService.borrarApp(app2);
		usuarioService.borrarApp(app3);
		usuarioService.borrarApp(app4);

		usuarioService.borrarUsuario(cliente1);
		usuarioService.borrarUsuario(cliente2);
	
		usuarioService.borrarUsuario(programador1);
		usuarioService.borrarUsuario(programador2);


		adminService.borrarTipoApp(tipoFreeware);
		adminService.borrarTipoApp(tipoShareware);
		adminService.borrarTipoApp(tipoPayware); 
		
		/* Borramos categorías principales. Asumimos propagación del borrado para el resto */		

		adminService.borrarCategoria(cat1);
		adminService.borrarCategoria(cat2);
	
	}	
}
