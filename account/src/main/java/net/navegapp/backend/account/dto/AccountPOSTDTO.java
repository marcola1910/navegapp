package net.navegapp.backend.account.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AccountPOSTDTO {

	@Size(min=3, max=255)
	public String name;
	
	@NotEmpty
	public String lastName;
	
	@Email
	public String email;
	
	public LocationDTO location;
	
}
