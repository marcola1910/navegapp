package net.navegapp.backend.account;


import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import net.navegapp.backend.account.util.TokenUtils;

import org.approvaltests.JsonApprovals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(AccountTestLifecycleManager.class)
public class AccountResourceTest {

	private String token;
	
	@BeforeEach
	public void gereToken() throws Exception {
		token = TokenUtils.generateTokenString("/JWTSailorClaims.json", null);
	}
	
	@Test
	@DataSet("account-cenario-1.yml")
	public void testsearchAccounts() {
		String result = given()
				.when().get("/accounts").then()
				.statusCode(200)
				.extract().asString();
		
		JsonApprovals.verifyJson(result);
	}
	
	private RequestSpecification given() {
        return RestAssured.given()
                .contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + token));
    }

	/*
	@Test
	@DataSet("account-cenario-1.yml")
	public void testsearchAccountsEvents1() {
		String result = given()
				.when().get("/accounts/1/accEvents").then()
				.statusCode(200)
				.extract().asString();
		
		JsonApprovals.verifyJson(result);
	}
	*/
	
	
}
