package es.udc.fi.lbd.monuzz.id.apps.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;


@Entity
@Table (name="CLIENTE")
public class Cliente extends Usuario {
	
	private String tipoCliente;
	private Float saldo;
	private List<App> apps = new ArrayList<App>();


	public Cliente() {
	}

	public Cliente(String nombreUsuario, String password, String nombre,
			String apellido1, String apellido2, String nombreEnPantalla, String tipoCliente, Float saldo) {
		this.nombreDeUsuario = nombreUsuario;
		this.password = password;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nombreEnPantalla = nombreEnPantalla;
		this.tipoCliente=tipoCliente;
		this.saldo=saldo;
	}
	
	@Column (name="TIPO_CLIENTE",nullable=false)	
	public String getTipoCliente() {
		return tipoCliente;
	}
	@Column (name="SALDO",nullable=false)
	public Float getSaldo() {
		return saldo;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CLIENTE_APP", 
			joinColumns = { @JoinColumn(name = "IDCLIENTE") }, 
			inverseJoinColumns = { @JoinColumn(name = "IDAPP") })
	public List<App> getApps() {
		return apps;
	}


	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}
	public void setApps(List<App> apps) {
		this.apps = apps;
	}

	@Override
	public String toString() {
		return (super.toString() + "  [Cliente: Tipo cliente=" + tipoCliente + ", saldo=" + saldo + "]");
	}
	
	
}
