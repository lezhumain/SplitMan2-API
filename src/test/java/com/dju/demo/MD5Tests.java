package com.dju.demo;//package com.dju.demo;

import com.dju.demo.helpers.FileHelper;
import com.dju.demo.helpers.IpHelper;
import com.dju.demo.helpers.ND5Helper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class MD5Tests {
	@Test
	public void givenPassword_whenHashing_thenVerifying() throws NoSuchAlgorithmException {
		String hash = "6C902F88D0BAE318AAB5F03B9AF5B9C1";
		String password = "ILoveJava";

		String myHash = ND5Helper.hash(password, "uname", "salt");
		System.out.println(myHash);

		Assert.assertTrue(myHash.equals(hash));
	}
}
