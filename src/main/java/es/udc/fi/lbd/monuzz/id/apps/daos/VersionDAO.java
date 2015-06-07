package es.udc.fi.lbd.monuzz.id.apps.daos;


import java.util.List;

import es.udc.fi.lbd.monuzz.id.apps.model.App;
import es.udc.fi.lbd.monuzz.id.apps.model.Version;


public interface VersionDAO {
	Long create(Version miVersion);
	void update (Version miVersion);
	void remove (Version miVersion);
	Version findById (Long id);
	List<Version> findAllByApp (App miApp);
	long getNumVersiones(App miApp);
}
