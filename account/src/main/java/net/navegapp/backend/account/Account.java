package net.navegapp.backend.account;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "account")
public class Account extends PanacheEntityBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@CreationTimestamp
	public Date creationDate;
	
	@UpdateTimestamp
	public Date updateDate;
	
	public String name;
	
	public String lastName;
	
	public String email;
	
	@OneToOne(cascade = CascadeType.ALL)
	public Location location;
	
}
