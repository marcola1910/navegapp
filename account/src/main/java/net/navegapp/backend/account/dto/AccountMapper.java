package net.navegapp.backend.account.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import net.navegapp.backend.account.AccEvent;
import net.navegapp.backend.account.Account;

@Mapper(componentModel = "cdi")
public interface AccountMapper {

	public Account toAccount(AccountPOSTDTO dto);
	
    @Mapping(target = "creationDate", dateFormat = "dd/MM/yyyy HH:mm:ss")
    @Mapping(target = "updateDate", dateFormat = "dd/MM/yyyy HH:mm:ss")
    @Mapping(target = "localizacao.id", ignore = true)
	public AccountGETDTO toAccountDTO(Account account);
    
    public void toAccount(AccountPUTDTO dto, @MappingTarget Account acc);
    
    public AccEvent toEvent(EventPOSTDTO dto);
	
	@Mapping(target = "creationDate", dateFormat = "dd/MM/yyyy HH:mm:ss")
    @Mapping(target = "updateDate", dateFormat = "dd/MM/yyyy HH:mm:ss")
	public EventDTO toEventDTO(AccEvent accEvent);
    
    public void toEvent(EventDTO dto, @MappingTarget AccEvent accEvent);

}
