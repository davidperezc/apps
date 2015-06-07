package es.udc.fi.lbd.monuzz.id.apps.daos;

import java.util.List;

import es.udc.fi.lbd.monuzz.id.apps.model.TipoApp;

public interface TipoAppDAO {
	Long create(TipoApp miTipo);
	void remove (TipoApp miTipo);
	TipoApp findById(Long Id);
	TipoApp findByNombre(String nombre);
	List<TipoApp> findAll();
}
