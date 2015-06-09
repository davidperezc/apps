package es.udc.fi.lbd.monuzz.id.apps.model;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
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

@Entity
@Table (name="CATEGORIA")
public class Categoria {

	private Long idCategoria;
	private String nombre;
	private Categoria madre;
	private Set<Categoria> subcategorias=new HashSet<Categoria>();

	@SuppressWarnings("unused")
	private Categoria() {
	}
	public Categoria(String nombre) {
		this.nombre = nombre;
		this.madre=null;
	}
	
	@Id
	@SequenceGenerator (name="categoriaId", sequenceName="id_categoria_seq")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="categoriaId")
	@Column(name="ID_CATEGORIA",unique=true,nullable=false)
	public Long getIdCategoria() {
		return idCategoria;
	}
	@Column (name="NOMBRE",unique = true,nullable=false)
	public String getNombre() {
		return nombre;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CATEGORIA_MADRE")
	public Categoria getmadre() {
		return madre;
	}
	@OneToMany(mappedBy="madre",fetch=FetchType.EAGER,cascade={CascadeType.ALL})
	public Set<Categoria> getSubcategorias() {
		return subcategorias;
	}
		
	
	@SuppressWarnings("unused")
	private  void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setMadre(Categoria madre) {
		this.madre = madre;
	}
	public void setSubcategorias(Set<Categoria> subcategorias) {
		this.subcategorias=subcategorias;
	}
	
	public void addSubcategoria (Categoria subCat){
		if (subCat==null)
			throw new IllegalArgumentException("Intentando añadir categoria nula");
		if (subCat.getmadre()!=null) 
			subCat.getmadre().getSubcategorias().remove(subCat);
		subCat.setMadre(this);
		this.subcategorias.add(subCat);
	}

	@Override
	public String toString() {
		return "Categoria [idCategoria=" + idCategoria + ", nombre=" + nombre + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Categoria other = (Categoria) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	
	
}
