package es.udc.fi.lbd.monuzz.id.apps.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.CascadeType;


@Entity
@Table (name="APP")
public class App  {
	
	private Long idApp;
	private String titulo;
	private Timestamp fechaDeAlta;
	private Float precio;
	private Programador autor;
	private TipoApp tipoApp;
	private Categoria categoria;
	private Long votos;
	private List<Version> versiones=new ArrayList<Version>();
	
	@SuppressWarnings("unused")
	private App() {}
	
	public App(String titulo, Timestamp fechaDeAlta, Float precio, Programador autor, Categoria categoria, TipoApp tipoApp) {
		super();
		this.titulo = titulo;
		this.fechaDeAlta = fechaDeAlta;
		this.precio=precio;
		this.autor = autor;
		this.categoria=categoria;
		this.tipoApp=tipoApp;
		this.votos=new Long(0);
	}
	
	@Id
	@SequenceGenerator (name="appId", sequenceName="id_app_seq")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="appId")
	@Column (name="ID_APP")
	public Long getIdApp() {
		return idApp;
	}
	@Column (name="TITULO",unique = true,nullable=false)
	public String getTitulo() {
		return titulo;
	}

	@Column (name="FECHA_ALTA",nullable=false)
	public Timestamp getFechaDeAlta() {
		return fechaDeAlta;
	}
	@Column (name="PRECIO",nullable=false)
	public Float getPrecio() {
		return precio;
	}
	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	public Programador getAutor() {
		return autor;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ID_CATEGORIA")
	public Categoria getCategoria() {
		return categoria;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ID_TIPOAPP")
	public TipoApp getTipoApp() {
		return tipoApp;
	}
	@OneToMany(mappedBy="app",fetch=FetchType.LAZY,cascade={CascadeType.ALL})
	//EN VEZ DE ALL DEBERÍA DE IR PERSIST Y REMOVE
	public List<Version> getVersiones() {
		return versiones;
	}
	@Column (name="VOTOS",nullable=false)
	public Long getVotos() {
		return votos;
	}
	
	@SuppressWarnings("unused")
	private void setIdApp(Long idApp) {
		this.idApp = idApp;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setFechaDeAlta(Timestamp fechaDeAlta) {
		this.fechaDeAlta = fechaDeAlta;
	}
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	public void setAutor(Programador autor) {
		this.autor = autor;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public void setTipoApp(TipoApp tipoApp) {
		this.tipoApp = tipoApp;
	}
	public void setVersiones(List<Version> versiones) {
		this.versiones=versiones;
	}
	public void setVotos(Long votos) {
		this.votos=votos;
	}
	
	
	@Override
	public String toString() {
		return "App [idApp=" + idApp + ", titulo=" + titulo + ", fechaDeAlta="
				+ fechaDeAlta + ", precio=" + precio + ", autor=" + autor.getApellido1()+" "+autor.getApellido2()+", "+autor.getNombre()
				+ ", tipoApp=" + tipoApp.getNombre() + ", categoria=" + categoria.getNombre() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result
				+ ((fechaDeAlta == null) ? 0 : fechaDeAlta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		App other = (App) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (fechaDeAlta == null) {
			if (other.fechaDeAlta != null)
				return false;
		} else if (!fechaDeAlta.equals(other.fechaDeAlta))
			return false;
		return true;
	}

	



}
