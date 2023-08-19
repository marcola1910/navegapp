package net.navegapp.backend.account;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import net.navegapp.backend.account.dto.AccountPOSTDTO;
import net.navegapp.backend.account.dto.AccountPUTDTO;
import net.navegapp.backend.account.dto.EventDTO;
import net.navegapp.backend.account.dto.EventPOSTDTO;
import net.navegapp.backend.account.dto.AccountGETDTO;
import net.navegapp.backend.account.dto.AccountMapper;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("sailor")
@SecurityScheme(securitySchemeName = "navegapp-auth" , type = SecuritySchemeType.OAUTH2 , flows = 
@OAuthFlows( password =  @OAuthFlow( tokenUrl = "http://localhost:8180/auth/realms/navegapp/protocol/openid-connect/token"  )  ) )
@SecurityRequirement( name = "navegapp-auth", scopes = {} )
public class AccountResource {

	@Inject
	AccountMapper accountMapper;
	
    @GET
    @Tag(name = "account")
    @Counted(name="Quantidade buscas em todas as contas")
    @SimplyTimed(name="Tempo buscas em todas as contas")
    @Timed(name="Tempo total buscas em todas as contas")
    public List<AccountGETDTO> allAccounts() {
    	Stream<Account> accounts = Account.streamAll();
    	return accounts.map(a -> accountMapper.toAccountDTO(a)).collect(Collectors.toList());
    }
    
    @POST
    @Transactional
    @Tag(name = "account")
    @Counted(name="Quantidade Add contas")
    @SimplyTimed(name="Tempo Add  contas")
    @Timed(name="Tempo total Add  contas")
    public void add(@Valid AccountPOSTDTO dto) {
    	Account acc = accountMapper.toAccount(dto);
    	acc.persist();
    }
    
    @PUT
    @Path("{id}")
    @Tag(name = "account")
    @Transactional
    @Counted(name="Quantidade update contas")
    @SimplyTimed(name="Tempo update contas")
    @Timed(name="Tempo total bupdatecontas")
    public void update(@PathParam("id") Long id, AccountPUTDTO dto) {
    	Optional<Account> accountOp = Account.findByIdOptional(id);
    	if( accountOp.isEmpty() ) {
    		throw new NotFoundException();
    	}
    	
    	Account account = accountOp.get();
    	
    	accountMapper.toAccount(dto, account);
    	
    	account.persist();
    }
    
    @DELETE
    @Path("{id}")
    @Tag(name = "account")
    @Transactional
    public void delete(@PathParam("id") Long id) {
    	Optional<Account> accountOp = Account.findByIdOptional(id);
    	
		accountOp.ifPresentOrElse(Account::delete, () -> {
			throw new NotFoundException();
		});
    }
    
    @GET
    @Path("{idAccount}/accEvents")
    @Tag(name = "events")
    public List<EventDTO> searchEvents(@PathParam("idAccount") Long idAccount){
    	Optional<Account> accOP = Account.findByIdOptional(idAccount);
    	if(accOP.isEmpty()) {
    		throw new NotFoundException("Events not found for this account");
    	}
    	
    	Account acc = accOP.get();
    	
    	Stream<AccEvent> accEvents =  AccEvent.stream("account", acc);
    	return accEvents.map(a -> accountMapper.toEventDTO(a)).collect(Collectors.toList());
    }
    
    @POST
    @Path("{idAccount}/accEvents")
    @Tag(name = "events")
    @Transactional
    public Response addEvent(@PathParam("idAccount") Long idAccount, EventPOSTDTO dto){
    	Optional<Account> accountOP = Account.findByIdOptional(idAccount);
    	if(accountOP.isEmpty()) {
    		throw new NotFoundException("Account not found");
    	}
    	
    	AccEvent accEvent = new AccEvent();
    	accEvent.account = accountOP.get();
    	accEvent.name = dto.name;
    	accEvent.description = dto.description;
    	accEvent.note = dto.note;
    	accEvent.persist();
    	
    	return Response.status(Status.CREATED).build();
    }
    
    @PUT
    @Path("{idAccount}/accEvents/{idEvent}")
    @Tag(name = "events")
    @Transactional
    public Response updateEvent(@PathParam("idAccount") Long idAccount, AccEvent dto, Long idEvent){
    	Optional<Account> accountOP = Account.findByIdOptional(idAccount);
    	if(accountOP.isEmpty()) {
    		throw new NotFoundException("Account not found");
    	}
    	
    	Optional<AccEvent> eventOP = AccEvent.findByIdOptional(idEvent);
    	if(eventOP.isEmpty()) {
    		throw new NotFoundException("AccEvent not found");
    	}
    	
    	AccEvent accEvent = eventOP.get();
    	accEvent.name = dto.name;
    	accEvent.description = dto.description;
    	accEvent.note = dto.note;
    	
    	accEvent.persist();
    	
    	return Response.status(Status.CREATED).build();
    }
    
    @DELETE
    @Path("{idAccount}/accEvents/{idEvent}")
    @Tag(name = "events")
    @Transactional
    public void deleteEvent(@PathParam("idAccount") Long idAccount, AccEvent dto, Long idEvent){
    	Optional<Account> accountOP = Account.findByIdOptional(idAccount);
    	if(accountOP.isEmpty()) {
    		throw new NotFoundException("Account not found");
    	}
    	
    	Optional<AccEvent> eventOP = AccEvent.findByIdOptional(idEvent);
		eventOP.ifPresentOrElse(AccEvent::delete, () -> {
			throw new NotFoundException("AccEvent not found");
		});
    	
    }
    
    
    
}