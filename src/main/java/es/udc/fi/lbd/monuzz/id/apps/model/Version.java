package es.udc.fi.lbd.monuzz.id.apps.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table (name="VERSION")
public class Version {

	private Long idVersion;
	private String numVersion;
	private Timestamp fechaDePublicacion;
	private App app;
	
	@SuppressWarnings("unused")
	private Version() {}
	
	
	public Version(String numVersion, Timestamp fechaDePublicacion, App app) {
		this.numVersion = numVersion;
		this.fechaDePublicacion = fechaDePublicacion;
		this.app = app;
		app.getVersiones().add(this);
	}

	@Id
	@SequenceGenerator (name="versionId", sequenceName="id_version_seq")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="versionId")
	@Column (name="ID_VERSION",unique = true,nullable=false)
	public Long getIdVersion() {
		return idVersion;
	}
	@Column (name="NUM_VERSION",unique = true,nullable=false)
	public String getNumVersion() {
		return numVersion;
	}
	@Column (name="FECHA_PUB",nullable=false)
	public Timestamp getFechaDePublicacion() {
		return fechaDePublicacion;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ID_APP")
	public App getApp() {
		return app;
	}

	@SuppressWarnings("unused")
	private void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}
	public void setNumVersion(String numVersion) {
		this.numVersion = numVersion;
	}
	public void setFechaDePublicacion(Timestamp fechaPublicacion) {
		this.fechaDePublicacion = fechaPublicacion;
	}
	public void setApp(App app) {
		this.app = app;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + ((app == null) ? 0 : app.hashCode());
		result = prime
				* result
				+ ((numVersion == null) ? 0 : numVersion.hashCode());
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
		Version other = (Version) obj;
		if (app == null) {
			if (other.app != null)
				return false;
		} else if (!app.equals(other.app))
			return false;
		if (fechaDePublicacion == null) {
			if (other.fechaDePublicacion != null)
				return false;
		} else if (!fechaDePublicacion.equals(other.fechaDePublicacion))
			return false;
		return true;
	}

	
	
}
