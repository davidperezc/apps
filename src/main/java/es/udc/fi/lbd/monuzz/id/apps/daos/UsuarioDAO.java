package es.udc.fi.lbd.monuzz.id.apps.daos;

import java.util.List;

import es.udc.fi.lbd.monuzz.id.apps.model.*;


public interface UsuarioDAO {
	public Long create(Usuario miUsuario);
	public void remove (Usuario miUsuario);
	public void update (Usuario miUsuario);
	public Usuario findById(Long id);
	public Usuario findByNombreDeUsuario (String nombreDeUsuario);	
	public List<Usuario> findAll();
	public List<Cliente> findAllClientes();
	public List<Programador> findAllProgramadores();
}
