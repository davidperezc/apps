package es.udc.fi.lbd.monuzz.id.apps.daos;


import java.util.List;

import es.udc.fi.lbd.monuzz.id.apps.model.App;
import es.udc.fi.lbd.monuzz.id.apps.model.Categoria;
import es.udc.fi.lbd.monuzz.id.apps.model.Cliente;
import es.udc.fi.lbd.monuzz.id.apps.model.Programador;


public interface AppDAO {
	Long create(App miApp);
	void update (App miApp);
	void remove (App miApp);
	App findById (Long id);
	App findByTitulo (String miTitulo);
	List<App> findAllByProgramador (Programador miProgramador);
	List<App> findAllByCliente (Cliente miCliente);
	List<App> findAllByCategoria (Categoria miCategoria);
	List<Cliente> findAllClientes (App miApp);
}
