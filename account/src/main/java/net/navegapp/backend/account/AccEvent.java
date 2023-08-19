package net.navegapp.backend.account;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "accevent")
public class AccEvent extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@CreationTimestamp
	public Date creationDate;
	
	@UpdateTimestamp
	public Date updateDate;
	
	@ManyToOne
	public Account account;
	
	public String name;
	
	public String description;
	
	public String note;
	
}
