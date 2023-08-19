package net.navegapp.backend.account;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class AccountTestLifecycleManager implements QuarkusTestResourceLifecycleManager {

	public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:12.2");
	
	@Override
	public Map<String, String> start() {
		POSTGRES.start();
		Map<String,String> properties = new HashMap<String,String>();
		
		properties.put("quarkus.datasource.jdbc.url", "jdbc:postgresql://localhost:5432/account");
		properties.put("quarkus.datasource.username", "account");
		properties.put("quarkus.datasource.password", "account");
		
		return properties;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		if( POSTGRES != null && POSTGRES.isRunning() ) {
			POSTGRES.stop();
		}
		
	}

}
