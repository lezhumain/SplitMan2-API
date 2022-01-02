package com.dju.demo;//package com.dju.demo;

import com.dju.demo.helpers.CMDHelper;
import com.dju.demo.helpers.CMDHelperResponse;
import com.dju.demo.helpers.ND5Helper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CMDTests {
	@Test
	public void testRunCMD() throws IOException, InterruptedException {
		final String[] cmd = new String[] {
			"C:\\Users\\djuuu\\AppData\\Local\\Programs\\Python\\Python39\\python",
			"C:\\Users\\djuuu\\Dropbox\\progWeb\\LecteurPS\\LecteurPS.py",
			"-n", "[NOM2]", "-s", "[PRENOM]", "-b", "[DATE_NAISS]", "-d", "[DATE]"
		};


		CMDHelperResponse resp = new CMDHelper().runCMD(List.of(cmd));
		Assert.assertNotNull(resp.response);
		Assert.assertEquals(resp.exitCode, 0);
		Assert.assertTrue(!resp.response.contains("Missing args"));
		Assert.assertTrue(!resp.response.toLowerCase().contains("error"));
	}

	@Test
	public void testGen() throws IOException, InterruptedException {
		CMDHelperResponse resp = new CMDHelper().genImg("[NOM]", "[PRENOM]", "[BIRTH]", "[DATE");
		Assert.assertNotNull(resp.response);
		Assert.assertEquals(resp.exitCode, 0);
		Assert.assertTrue(!resp.response.contains("Missing args"));
		Assert.assertTrue(!resp.response.toLowerCase().contains("error"));
	}
}
