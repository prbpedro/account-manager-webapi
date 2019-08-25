package test;

import org.junit.Assert;
import org.junit.Test;

import com.github.prbpedro.accountmanager.webapi.Main;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;


public class MainTest {

	@Test
	public void test() throws Exception {
		Thread t1 = new Thread(new Runnable() {
	        public void run() {
	        	Main.main(null);
	        }
	    });
		t1.start();
		
		Thread.sleep(10000);

		HttpResponse<String> response = Unirest.post("http://localhost:5000/account-manager/transfer/do")
				.header("Content-Type", "application/x-www-form-urlencoded").header("cache-control", "no-cache")
				.body("sender-account-id=ACCOUNT_1&beneficiary-account-id=ACCOUNT_2&beneficiary-bank-code=REVOLUT!!!!!&currency-code=USD&ammount=1")
				.asString();
		
		Assert.assertEquals(200, response.getStatus());
		
		response = Unirest.post("http://localhost:5000/account-manager/transfer/do")
				.header("Content-Type", "application/x-www-form-urlencoded").header("cache-control", "no-cache")
				.body("sender-account-id=ACCOUNT_1999&beneficiary-account-id=ACCOUNT_2&beneficiary-bank-code=REVOLUT!!!!!&currency-code=USD&ammount=1")
				.asString();
		
		Assert.assertEquals(404, response.getStatus());
		
		response = Unirest.post("http://localhost:5000/account-manager/transfer/do")
				.header("Content-Type", "application/x-www-form-urlencoded").header("cache-control", "no-cache")
				.body("sender-account-id=ACCOUNT_1&beneficiary-account-id=ACCOUNT_2&beneficiary-bank-code=REVOLUT!!!!!&currency-code=USD&ammount=10000000")
				.asString();
		
		Assert.assertEquals(422, response.getStatus());
		
		response = Unirest.get("http://localhost:5000/account-manager/data")
				  .header("cache-control", "no-cache")
				  .asString();
		
		Assert.assertEquals(200, response.getStatus());
		
		Main.stopServer();
	}
}
