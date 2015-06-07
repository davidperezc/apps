package es.udc.fi.lbd.monuzz.id.apps.daos;

import java.util.List;

import es.udc.fi.lbd.monuzz.id.apps.model.*;

public interface CategoriaDAO {
	public Long create(Categoria miCategoria);
	public void remove (Categoria miCategoria);
	public void update (Categoria miCategoria);
	public Categoria findById (Long idCategoria);	
	public Categoria findByNombre (String nombreCategoria);	
	public List<Categoria> findFirstLevel();	
	public List<Categoria> findSubcategories(Categoria miCategoria);
	public Long getNumApps(Categoria miCategoria);
	public List<App> getApps(Categoria miCategoria);
	
}
