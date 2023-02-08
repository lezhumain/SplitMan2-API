package com.dju.demo;

import com.dju.demo.services.FileService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainE2ETests {
	private final SaveController sc;

	public MainE2ETests() {
		System.out.println("");
		SaveController.aClass = FileService.class;
		sc = new SaveController();
	}

	private static String _db = "[]";

	/**
	 * Test add new user from empty
	 * @throws Exception
	 */
	@Test
	void testUpdateAA_empty_0() throws Exception {
//		org.json.simple.parser.JSONParser jp = new JSONParser();
//		JSONArray o = (org.json.simple.JSONArray)jp.parse("[]");



		final int userIdData = -2;

		JSONArray res1 = sc.updateStuff(-2, "{\n" +
		"  \"id\": " + userIdData + ",\n" +
		"  \"type\": \"user\",\n" +
		"  \"email\": \"auto.browser.blackrobot.jen@wspt.co.uk\",\n" +
		"  \"username\": \"a\",\n" +
		"  \"password\": \"a\",\n" +
		"  \"invites\": []\n" +
		"}", MainE2ETests._db);

		Assert.assertNotNull(res1);

		System.out.println(res1.toJSONString());

//		final boolean containedb = res1.stream().filter(o1 -> ((int)Integer.parseInt(((JSONObject)o1).get("id").toString()) == userIdData)).findFirst().isPresent();
		final boolean containedb = res1.stream().filter(o1 -> ((JSONObject) o1).get("username").toString().equals("a")).findFirst().isPresent();
		Assert.assertTrue(containedb);

		MainE2ETests._db = res1.toString();
	}

	/**
	 * Test add 2nd user from empty
	 * @throws Exception
	 */
	@Test
	void testUpdateAA_empty_01() throws Exception {
//		org.json.simple.parser.JSONParser jp = new JSONParser();
//		JSONArray o = (org.json.simple.JSONArray)jp.parse("[]");



		final int userIdData = -2;

		JSONArray res1 = sc.updateStuff(-2, "{\n" +
				"  \"id\": " + userIdData + ",\n" +
				"  \"type\": \"user\",\n" +
				"  \"email\": \"ddddd.browser.blackrobot.jen@wspt.co.uk\",\n" +
				"  \"username\": \"s\",\n" +
				"  \"password\": \"s\",\n" +
				"  \"invites\": []\n" +
				"}", MainE2ETests._db);

		Assert.assertNotNull(res1);

		System.out.println(res1.toJSONString());

//		final boolean containedb = res1.stream().filter(o1 -> ((int)Integer.parseInt(((JSONObject)o1).get("id").toString()) == userIdData)).findFirst().isPresent();
		final boolean containedb = res1.stream().filter(o1 -> ((JSONObject) o1).get("username").toString().equals("s")).findFirst().isPresent();
		Assert.assertTrue(containedb);

		Assert.assertEquals(res1.size(), 2);

		MainE2ETests._db = res1.toString();
	}

	/**
	 * test bad email fails on register
 	 * @throws Exception
	 */
	@Test
	void testRegisterBadEmail() throws Exception {
		final int userIdData = -2;

		JSONArray res1 = sc.updateStuff(-2, "{\n" +
				"  \"id\": " + userIdData + ",\n" +
				"  \"type\": \"user\",\n" +
				"  \"email\": \"notanemail\",\n" +
				"  \"username\": \"s\",\n" +
				"  \"password\": \"s\",\n" +
				"  \"invites\": []\n" +
				"}", MainE2ETests._db);

		Assert.assertNull(res1);
	}

	@Test
	void testRegisterExistingEmail() throws Exception {
		final int userIdData = -2;

		final JSONArray res1 = sc.updateStuff(-2, "{\n" +
				"  \"id\": " + userIdData + ",\n" +
				"  \"type\": \"user\",\n" +
				"  \"email\": \"ddddd.browser.blackrobot.jen@wspt.co.uk\",\n" +
				"  \"username\": \"s\",\n" +
				"  \"password\": \"s\",\n" +
				"  \"invites\": []\n" +
				"}", "[]");

		Assert.assertNotNull(res1);

		System.out.println(res1.toJSONString());

		final boolean containedb = res1.stream().filter(o1 -> ((JSONObject) o1).get("username").toString().equals("s")).findFirst().isPresent();
		Assert.assertTrue(containedb);
		Assert.assertEquals(res1.size(), 1);

		final JSONArray res2 = sc.updateStuff(-2, "{\n" +
				"  \"id\": " + userIdData + ",\n" +
				"  \"type\": \"user\",\n" +
				"  \"email\": \"ddddd.browser.blackrobot.jen@wspt.co.uk\",\n" +
				"  \"username\": \"sa\",\n" +
				"  \"password\": \"s\",\n" +
				"  \"invites\": []\n" +
				"}", res1.toJSONString());

		Assert.assertNull(res2);
	}

	@Test
	void testRegisterExistingUsername() throws Exception {
		final int userIdData = -2;

		final JSONArray res1 = sc.updateStuff(-2, "{\n" +
				"  \"id\": " + userIdData + ",\n" +
				"  \"type\": \"user\",\n" +
				"  \"email\": \"ddddd.browser.blackrobot.jen@wspt.co.uk\",\n" +
				"  \"username\": \"s\",\n" +
				"  \"password\": \"s\",\n" +
				"  \"invites\": []\n" +
				"}", "[]");

		Assert.assertNotNull(res1);

		System.out.println(res1.toJSONString());

		final boolean containedb = res1.stream().filter(o1 -> ((JSONObject) o1).get("username").toString().equals("s")).findFirst().isPresent();
		Assert.assertTrue(containedb);
		Assert.assertEquals(res1.size(), 1);

		final JSONArray res2 = sc.updateStuff(-2, "{\n" +
				"  \"id\": " + userIdData + ",\n" +
				"  \"type\": \"user\",\n" +
				"  \"email\": \"ddddd.browser.blackrobot@wspt.co.uk\",\n" +
				"  \"username\": \"s\",\n" +
				"  \"password\": \"s\",\n" +
				"  \"invites\": []\n" +
				"}", res1.toJSONString());

		Assert.assertNull(res2);
	}

//	/**
//	 * Test add new travel from empty
//	 * @throws Exception
//	 */
//	@Test
//	void testUpdateAA_empty_1() throws Exception {
////		org.json.simple.parser.JSONParser jp = new JSONParser();
////		JSONArray o = (org.json.simple.JSONArray)jp.parse("[]");
//
//
//
//		final int travelID = 1;
//
//		final String dataToAdd = "{\n" +
//		"  \"id\": " + travelID + ",\n" +
//		"  \"type\": \"travel\",\n" +
//		"  \"name\": \"SKI\",\n" +
//		"  \"description\": \"le ski\",\n" +
//		"  \"fromDate\": null,\n" +
//		"  \"toDate\": null,\n" +
//		"  \"participants\": [\n" +
//		"    {\n" +
//		"      \"name\": \"Dju\",\n" +
//		"      \"dayCount\": 15,\n" +
//		"      \"ratio\": 0.6818181818181818\n" +
//		"    },\n" +
//		"    {\n" +
//		"      \"name\": \"cams\",\n" +
//		"      \"dayCount\": 7,\n" +
//		"      \"ratio\": 0.3181818181818182\n" +
//		"    }\n" +
//		"  ]\n" +
//		"}";
//
////		TODO
//		// without connected user
//		JSONArray res1 = sc.updateStuff(-2, dataToAdd, this._db);
//		Assert.assertNull(res1);
//
//		// with connected user
//		res1 = sc.updateStuff(0, dataToAdd, E2ETests._db);
//		Assert.assertNotNull(res1);
//		Assert.assertEquals(res1.size(), 2);
//
//		System.out.println(res1.toJSONString());
//
//		final boolean containedb = res1.stream().filter(o1 -> ((int)Integer.parseInt(((JSONObject)o1).get("id").toString()) == travelID)).findFirst().isPresent();
//		Assert.assertTrue(containedb);
//
//		E2ETests._db = res1.toString();
//	}
//
//	/**
//	 * Test add new expenses from empty
//	 * @throws Exception
//	 */
//	@Test
//	void testUpdateAA_empty_2() throws Exception {
////		org.json.simple.parser.JSONParser jp = new JSONParser();
////		JSONArray o = (org.json.simple.JSONArray)jp.parse("[]");
//
//
//
//		final int travelID = 1;
//
//		final String dataToAdd = "[\n" +
//		"  {\n" +
//		"    \"id\": 0,\n" +
//		"    \"type\": \"expense\",\n" +
//		"    \"tripId\": 0,\n" +
//		"    \"name\": \"Camping\",\n" +
//		"    \"amount\": 300,\n" +
//		"    \"date\": \"2021-11-05T20:43:43.436Z\",\n" +
//		"    \"payer\": \"Dju\",\n" +
//		"    \"payees\": [\n" +
//		"      {\n" +
//		"        \"name\": \"Dju\",\n" +
//		"        \"selected\": true,\n" +
//		"        \"e4xpenseRatio\": 0.6818181818181818\n" +
//		"      },\n" +
//		"      {\n" +
//		"        \"name\": \"cams\",\n" +
//		"        \"selected\": true,\n" +
//		"        \"e4xpenseRatio\": 0.3181818181818182\n" +
//		"      }\n" +
//		"    ],\n" +
//		"    \"createdAt\": \"2021-11-05T20:44:00.377Z\",\n" +
//		"    \"createdBy\": \"a\",\n" +
//		"    \"updatedAt\": \"2021-11-05T20:44:00.377Z\",\n" +
//		"    \"updatedBy\": \"a\"\n" +
//		"  },\n" +
//		"  {\n" +
//		"    \"id\": 1,\n" +
//		"    \"type\": \"expense\",\n" +
//		"    \"tripId\": 0,\n" +
//		"    \"name\": \"trajet\",\n" +
//		"    \"amount\": 30,\n" +
//		"    \"date\": \"2021-11-05T20:44:02.202Z\",\n" +
//		"    \"payer\": \"cams\",\n" +
//		"    \"payees\": [\n" +
//		"      {\n" +
//		"        \"name\": \"Dju\",\n" +
//		"        \"selected\": true,\n" +
//		"        \"e4xpenseRatio\": 0.5\n" +
//		"      },\n" +
//		"      {\n" +
//		"        \"name\": \"cams\",\n" +
//		"        \"selected\": true,\n" +
//		"        \"e4xpenseRatio\": 0.5\n" +
//		"      }\n" +
//		"    ],\n" +
//		"    \"createdAt\": \"2021-11-05T20:44:26.350Z\",\n" +
//		"    \"createdBy\": \"a\",\n" +
//		"    \"updatedAt\": \"2021-11-05T20:44:26.350Z\",\n" +
//		"    \"updatedBy\": \"a\"\n" +
//		"  }\n" +
//		"]";
//
//		JSONParser jp = new JSONParser();
//		JSONArray allExpenses = (org.json.simple.JSONArray)jp.parse(dataToAdd);
//
//		JSONArray res1 = null;
//
//		for (int i = 0; i < allExpenses.size(); ++i) {
//			JSONObject expense = (JSONObject)allExpenses.get(i);
//
//			final int expenseID = 2 + i;
//
//
//			res1 = this.doStuffExpense(expense, expenseID, 1, sc, false, 0, 3 + i);
//
////			expense = (JSONObject)expense.clone();
////
////			final int expenseID = 2 + i;
////			expense.replace("id", expenseID);
////			expense.replace("tripId", 1);
////
////			// with connected user
////			JSONArray res1 = sc.updateStuff(0, expense.toString(), E2ETests._db);
////			Assert.assertNotNull(res1);
////			Assert.assertEquals(res1.size(), 3 + i);
////
////			final boolean containedb = res1.stream().filter(o1 -> ((int)Integer.parseInt(((JSONObject)o1).get("id").toString()) == expenseID)).findFirst().isPresent();
////			Assert.assertTrue(containedb);
//
//			E2ETests._db = res1.toString();
//		}
//
//		// test adding expense without userID
//		JSONObject expense = (JSONObject)allExpenses.get(0);
//		final int expenseID = res1.size() + 1;
//
//		this.doStuffExpense(expense, expenseID, 1, sc, true, -2, -1);
//	}
//
//	private JSONArray doStuffExpense(JSONObject expense, int expenseID, int tripID, SaveController sc,
//									 boolean shouldBeNull, int connectedUserID, int expectedSize) throws ParseException {
//		expense = (JSONObject)expense.clone();
//
//		expense.replace("id", expenseID);
//		expense.replace("tripId", tripID);
//
//		// with connected user
//		JSONArray res1 = sc.updateStuff(connectedUserID, expense.toString(), E2ETests._db);
//
//		if (shouldBeNull) {
//			Assert.assertNull(res1);
//		} else {
//			Assert.assertNotNull(res1);
//			Assert.assertEquals(res1.size(), expectedSize);
//
//			final boolean containedb = res1.stream().filter(o1 -> ((int)Integer.parseInt(((JSONObject)o1).get("id").toString()) == expenseID)).findFirst().isPresent();
//			Assert.assertTrue(containedb);
//		}
//
//		return res1;
//	}
}
