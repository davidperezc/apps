package es.udc.fi.lbd.monuzz.id.apps;

import static org.junit.Assert.*;


import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

import es.udc.fi.lbd.monuzz.id.apps.TestUtils;
import es.udc.fi.lbd.monuzz.id.apps.model.*;
import es.udc.fi.lbd.monuzz.id.apps.services.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class TestAdmin {

	private Logger log = Logger.getLogger("apps");
	
	@Autowired
	private TestUtils testUtils;

	@Autowired
	private AdminService adminService;
	
	
	@Before
	public void setUp() throws Exception { 
		log.info ("Inicializando datos para caso de prueba: " + this.getClass().getName());
		testUtils.creaSetDatosPrueba();
		log.info ("Datos creados con éxito");
	}

	@After
	public void tearDown() throws Exception {
		log.info ("Eliminando datos para caso de prueba: " + this.getClass().getName());
		testUtils.eliminaSetDatosPrueba();  
		log.info ("Datos eliminados con éxito");
	}

	@Test
	public void testTipoApp() {
			
		// T1. Creamos nuevo tipo de app y lo registramos. Probamos recuperacion por nombre y por id. 

		TipoApp miTipoApp = new TipoApp("Mocoware", "Usar e tirar, modelo kleenex", null);
		adminService.registrarNuevoTipoApp(miTipoApp);
		assertNotNull(miTipoApp.getIdTipoApp());
		assertEquals(miTipoApp, adminService.buscarTipoAppPorId(miTipoApp.getIdTipoApp()));
		assertEquals(miTipoApp, adminService.buscarTipoAppPorNombre(miTipoApp.getNombre()));
		
		// T2. Creamos tipo de app ya existente, y detectamos duplicidad del nombre
		
		TipoApp miTipoApp2 = new TipoApp("Mocoware", "Usar e tirar, modelo kleenex", null);
		Boolean duplicado=false;
		try {adminService.registrarNuevoTipoApp(miTipoApp2);} catch (DataIntegrityViolationException e) {duplicado=true;}
		assertTrue(duplicado);
		
		// T3 Intentamos recuperar por su nombre un tipo de app inexistente 

		assertNull(adminService.buscarTipoAppPorNombre("Inexistente"));  
		
		// T4 Recuperamos los tipos de app existentes, por orden alfabético de nombre
		
		List<TipoApp> miLista = adminService.buscarTodosTipoApp();
		assertEquals(4, miLista.size());
		assertEquals("Freeware",  miLista.get(0).getNombre());
		assertEquals("Mocoware",  miLista.get(1).getNombre());
		assertEquals("Payware",   miLista.get(2).getNombre());
		assertEquals("Shareware", miLista.get(3).getNombre());
		miLista.clear();
		
		
		
		// T5 Tratamos de eliminar un tipo de app con apps asociadas
		/*
		Boolean intRef=false;
		try {adminService.borrarTipoApp(adminService.buscarTipoAppPorNombre("Freeware"));} catch (DataIntegrityViolationException e) {intRef=true;}
		assertTrue(intRef);
		*/
		// T6 Borramos un tipo de app sin apps asociadas
		
		miTipoApp=adminService.buscarTipoAppPorNombre("Mocoware");
		adminService.borrarTipoApp(miTipoApp);
		assertNull (adminService.buscarTipoAppPorId(miTipoApp.getIdTipoApp()));
		assertNull (adminService.buscarTipoAppPorNombre(miTipoApp.getNombre()));
		
	}

	@Test
	public void testCategoria() {
			
		// T1. Creamos nueva categoria (con dos subcategorias) y las registramos 

		Categoria catA  = new Categoria("Categoria A");
		Categoria catA1 = new Categoria("Categoria A.1");
		Categoria catA2 = new Categoria("Categoria A.2");		
		catA.addSubcategoria(catA1);
		catA.addSubcategoria(catA2);

		adminService.registrarNuevaCategoria(catA);
		assertNotNull(catA.getIdCategoria());
		assertNotNull(catA1.getIdCategoria());
		assertNotNull(catA2.getIdCategoria());
		
		assertNotNull(adminService.buscarCategoriaPorId(catA.getIdCategoria()));
		assertNotNull(adminService.buscarCategoriaPorId(catA1.getIdCategoria()));
		assertNotNull(adminService.buscarCategoriaPorId(catA2.getIdCategoria()));
		
		assertNotNull(adminService.buscarCategoriaPorNombre(catA.getNombre()));
		assertNotNull(adminService.buscarCategoriaPorNombre(catA1.getNombre()));
		assertNotNull(adminService.buscarCategoriaPorNombre(catA2.getNombre()));

		
		// T2. Creamos categoria ya existente, y detectamos duplicidad del nombre
		
		Categoria catANueva = new Categoria("Categoria A"); 
		Categoria catA1Nueva = new Categoria("Categoria A1Nueva");
		catANueva.addSubcategoria(catA1Nueva);

		Boolean duplicado=false;
		try {adminService.registrarNuevaCategoria(catANueva);} catch (DataIntegrityViolationException e) {duplicado=true;}
		assertTrue(duplicado);		
		assertNull(adminService.buscarCategoriaPorId(catANueva.getIdCategoria()));
		assertNull(adminService.buscarCategoriaPorId(catA1Nueva.getIdCategoria()));
		
		// T3 Intentamos recuperar por su nombre una categoria inexistente 

		Categoria catDesconocida = adminService.buscarCategoriaPorNombre("Inexistente");
		assertNull (catDesconocida);
		
		// T4 Recuperamos todas las categorías de "nivel 1" (por orden alfabetico de nombre)
		
		List<Categoria> miLista = adminService.buscarCategoriasPrincipales();
		assertEquals(3, miLista.size());
		assertEquals(testUtils.cat1, miLista.get(0));
		assertEquals(testUtils.cat2, miLista.get(1));
		assertEquals(catA, miLista.get(2));
		miLista.clear();
		
		// T5 Obtenemos subcategorias, por orden alfabetico de nombre
		
		miLista = adminService.buscarSubcategorias(testUtils.cat1);
		assertEquals(2, miLista.size());
		assertEquals(testUtils.cat11, miLista.get(0));
		assertEquals(testUtils.cat12, miLista.get(1));
		miLista.clear();
		
		miLista = adminService.buscarSubcategorias(testUtils.cat2);
		assertEquals(2, miLista.size());
		assertEquals(testUtils.cat21, miLista.get(0));
		assertEquals(testUtils.cat22, miLista.get(1));
		miLista.clear();
		
		miLista = adminService.buscarSubcategorias(testUtils.cat11);
		assertEquals(0, miLista.size());
		miLista.clear();

		miLista = adminService.buscarSubcategorias(testUtils.cat12);
		assertEquals(0, miLista.size());
		miLista.clear();
		
		// T6 Contamos aplicaciones de la categoria
		/*
		Long numApps;
		numApps = adminService.calcularNumAppsCategoria(testUtils.cat1);
		assertEquals (new Long(3), numApps);
		numApps = adminService.calcularNumAppsCategoria(testUtils.cat11);
		assertEquals (new Long(2), numApps);
		numApps = adminService.calcularNumAppsCategoria(testUtils.cat12);
		assertEquals (new Long(1), numApps);
		numApps = adminService.calcularNumAppsCategoria(testUtils.cat2);
		assertEquals (new Long(1), numApps);
		numApps = adminService.calcularNumAppsCategoria(catA);
		assertEquals (new Long(0), numApps);
		*/
		
		// T7 Recuperamos aplicaciones de diferentes categorias, por orden de votos (inverso, de más a menos)
		/*
		List<App> listaApps = adminService.buscarAppsCategoria(testUtils.cat1);
		assertEquals(3, listaApps.size());
		assertEquals(testUtils.app3, listaApps.get(0));
		assertEquals(testUtils.app1, listaApps.get(1));
		assertEquals(testUtils.app2, listaApps.get(2));
		listaApps.clear();
		
		listaApps = adminService.buscarAppsCategoria(testUtils.cat11);
		assertEquals(2, listaApps.size());
		assertEquals(testUtils.app1, listaApps.get(0));
		assertEquals(testUtils.app2, listaApps.get(1));
		listaApps.clear();
		
		listaApps = adminService.buscarAppsCategoria(testUtils.cat12);
		assertEquals(1, listaApps.size());
		assertEquals(testUtils.app3, listaApps.get(0));
		listaApps.clear();
		
		listaApps = adminService.buscarAppsCategoria(testUtils.cat2);
		assertEquals(1, listaApps.size());
		assertEquals(testUtils.app4, listaApps.get(0));
		listaApps.clear();
		
		listaApps = adminService.buscarAppsCategoria(testUtils.cat21);
		assertEquals(1, listaApps.size());
		assertEquals(testUtils.app4, listaApps.get(0));
		listaApps.clear();
		
		assertEquals(0, adminService.buscarAppsCategoria(testUtils.cat22).size());
		listaApps.clear();
*/
		// T8 Cambiar nombre de categoria

		catA.setNombre("Cat A con nuevo nombre");
		adminService.modificarCategoria(catA);
		assertEquals (catA, adminService.buscarCategoriaPorNombre(catA.getNombre()));
		
		// T9 Cambiar categoria de lugar
		
		catA1.addSubcategoria(catA2);
		adminService.modificarCategoria(catA2);
		assertEquals (catA1, adminService.buscarCategoriaPorId(catA2.getIdCategoria()).getmadre());
		assertEquals(1, adminService.buscarSubcategorias(catA).size());
		assertEquals(1, adminService.buscarSubcategorias(catA1).size());
		assertEquals(0, adminService.buscarSubcategorias(catA2).size());
		
		// T10 Intentamos borrar una categoria con apps 
	
		Boolean noBorrada=false;
		try {
			adminService.borrarCategoria(testUtils.cat1);
		} catch (Exception e) {
			noBorrada=true;
		}
		assertTrue(noBorrada);

		// T11 Borramos categoria (y subcategorias) sin apps 

		adminService.borrarCategoria(catA);
		assertNull(adminService.buscarCategoriaPorId(catA.getIdCategoria()));
		assertNull(adminService.buscarCategoriaPorId(catA1.getIdCategoria()));
		assertNull(adminService.buscarCategoriaPorId(catA2.getIdCategoria()));	
		
	}
}
