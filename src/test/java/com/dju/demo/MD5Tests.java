package com.dju.demo;//package com.dju.demo;

import com.dju.demo.helpers.ND5Helper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;

@SpringBootTest
class MD5Tests {
	@Test
	public void givenPassword_whenHashing_thenVerifying() throws NoSuchAlgorithmException {
		String hash = "6C902F88D0BAE318AAB5F03B9AF5B9C1";
		String password = "ILoveJava";

		String myHash = ND5Helper.hashForSession(password, "uname", "salt");
		String myHash1 = ND5Helper.hash("a");
		String myHash2 = ND5Helper.hash("s");
		System.out.println(myHash);

		Assert.assertTrue(myHash.equals(hash));
	}
}
