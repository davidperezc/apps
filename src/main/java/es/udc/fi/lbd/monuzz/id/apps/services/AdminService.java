package es.udc.fi.lbd.monuzz.id.apps.services;

import java.util.List;

import es.udc.fi.lbd.monuzz.id.apps.model.App;
import es.udc.fi.lbd.monuzz.id.apps.model.Categoria;
import es.udc.fi.lbd.monuzz.id.apps.model.TipoApp;

public interface AdminService {
	
	void registrarNuevoTipoApp (TipoApp miTipo);
	void borrarTipoApp(TipoApp miTipo);  							// Salta excepcion si aun hay apps ligadas al tipo
	TipoApp buscarTipoAppPorId(Long id);
	TipoApp buscarTipoAppPorNombre(String nombre);
	List<TipoApp> buscarTodosTipoApp();
	
	void registrarNuevaCategoria (Categoria miCategoria);
	void modificarCategoria (Categoria miCategoria);
	void borrarCategoria (Categoria miCategoria);  					// Salta excepcion si aun hay apps ligadas a la categoria (o sus subcategorias)
	Categoria buscarCategoriaPorId(Long id);
	Categoria buscarCategoriaPorNombre(String nombre);
	List<Categoria> buscarCategoriasPrincipales(); 					// Categorias de nivel 1 (no son subcategorias de ninguna otra)
	List<Categoria> buscarSubcategorias(Categoria miCategoria);  	// Subcategorias directas
	Long calcularNumAppsCategoria (Categoria miCategoria); 			// TODAS las apps, incluidas las de subcategorias
	List<App> buscarAppsCategoria (Categoria miCategoria);			// TODAS las apps, incluidas las de subcategorias

}
