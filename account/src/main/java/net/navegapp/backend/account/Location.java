package net.navegapp.backend.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name="location")
public class Location extends PanacheEntityBase {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	public Long latitud;
	
	public Long longitud;
	
}
