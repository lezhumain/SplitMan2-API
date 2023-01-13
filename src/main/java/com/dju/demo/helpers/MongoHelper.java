package com.dju.demo.helpers;

import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

public class MongoHelper {
    private final String _dbName;
    private MongoClient _mongo;
    private MongoCredential _credential;

    public MongoHelper(String host, int port, String username, String pass, String dbName) {
        final String theUrl = String.format("mongodb://%s:%s@%s", username, pass, host, port);

        // Creating a Mongo client
        this._mongo = MongoClients.create(theUrl);

        this._dbName = dbName;

        // Creating Credentials
//        this._credential = MongoCredential.createCredential(username, this._dbName,
//                pass.toCharArray());
        System.out.println("Connected to the database successfully");
    }

    //    customers
    public void createCollection(String colName) {
        // Accessing the database
        MongoDatabase database = this._mongo.getDatabase(this._dbName);

        //Creating a collection
        database.createCollection(colName);

        System.out.println("Collection created successfully");
    }

    public String selectData(String colName, final boolean lastBatchOnly) {
        List<Document> all = getAllDocs(colName, lastBatchOnly);
        String res = "[";

        if (all.size() > 0) {
            for(Document d: all) {
                res += d.toJson() + ",";
            }

            res = res.substring(0, res.length() - 1); // remove last ','
        }
        res += "]";

        return res;
    }



    private int getLastBatch(final MongoCollection<Document> collection) {
        // TODO refactor with getAllDocs

        // Getting the iterable object
        FindIterable<Document> iterDoc = collection.find();
        int res = 0;
        // Getting the iterator
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            Document d = (Document) it.next();
//            System.out.println(d.toJson());

            int batch = d.containsKey("batch") ? Integer.parseInt(d.get("batch").toString()) : 0;
            if(batch > res) {
                res = batch;
            }
        }
        return res;
    }

//    public List<Document> getAllDocs(final String colName, final boolean lastBatchOnly) {
//        List<Document> ress = new ArrayList<>();
//
//        // Accessing the database
//        MongoDatabase database = this._mongo.getDatabase(this._dbName);
//
//        // Retrieving a collection
//        MongoCollection<Document> collection = database.getCollection(colName);
//
//        final int newBatch = lastBatchOnly ? getLastBatch(collection) : -1;
//
//        // Getting the iterable object
//        FindIterable<Document> iterDoc = collection.find();
//        int i = 1;
//        // Getting the iterator
//        Iterator it = iterDoc.iterator();
//        while (it.hasNext()) {
//            Document d = (Document) it.next();
//            final int dBatch = d.containsKey("batch") ? Integer.parseInt(d.get("batch").toString()) : -1;
//
////            System.out.println(d.toJson());
//            if (newBatch == -1 || newBatch == dBatch) {
//                ress.add(d);
//            }
//            i++;
//        }
//
//        return ress;
//    }

    public List<Document> getAllDocs(final String colName, final boolean lastBatchOnly) {
        List<Document> ress = new ArrayList<>();

        // Accessing the database
        MongoDatabase database = this._mongo.getDatabase(this._dbName);

        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection(colName);

//        final int newBatch = lastBatchOnly ? getLastBatch(collection) : -1;
        final int newBatch = -1;

        FindIterable<Document> iterDoc = newBatch == -1
                ? collection.find()
                : collection.find(eq("batch", newBatch));

        // Getting the iterator
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            Document d = (Document) it.next();
            ress.add(d);
        }

        return ress;
    }

    /**
     * Inserts multiple documents from JSON string. Will remove any existing `_id` key
     * @param colName collection name
     * @param json json data to insert
     * @return
     * @throws ParseException
     */
    public boolean insertJson(final String colName, final String json) throws ParseException {
        // Accessing the database
        MongoDatabase database = this._mongo.getDatabase(this._dbName);

        List<Document> docsToAdd = json.startsWith("{")
            ? Arrays.asList(Document.parse(json))
            : this.parseList(json);

        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection(colName);

//        final int newBatch = this.getLastBatch(collection) + 1;
        for (Document toAdd: docsToAdd) {
//            if(toAdd.containsKey("batch")) {
//                toAdd.replace("batch", newBatch);
//            }
//            else {
//                toAdd.put("batch", newBatch);
//            }
            toAdd.remove("_id");
        }

        try {
            collection.insertMany(docsToAdd);
//            System.out.println("Success! Inserted document id: " + result.getInsertedId());
            return true;
        } catch (MongoException me) {
            System.err.println("Unable to insert due to an error: " + me);
            return false;
        }
    }

    public String upsertJson(final String colName, final String json) throws ParseException {
        // Accessing the database
        MongoDatabase database = this._mongo.getDatabase(this._dbName);

        List<Document> docsToAdd = json.startsWith("{")
                ? Arrays.asList(Document.parse(json))
                : this.parseList(json);

        // TODO add 'updating' key here?
//        for(Document d: docsToAdd) {
//            if(!d.containsKey("updating")) {
//                d.put("updating", true);
//            }
//        }

        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection(colName);

        Document target = docsToAdd.stream()
                .filter(document -> document.containsKey("updating") && document.get("updating").equals(true))
                .findFirst().orElse(null);

        if(target == null) {
            System.out.println("Couldn't find 'updating' key in objects.");
            return null;
        }

//        if(Integer.parseInt(target.get("id").toString()) == 0) {
        if(!target.containsKey("_id")) {
            // get last id
            final Document doc = collection.find().sort(descending("id")).first();
            final int lastID = doc != null ? Integer.parseInt(doc.get("id").toString()) : 0;

            // set id + 1
            target.replace("id", lastID + 1);
        }

//        Document query = new Document()
//                .append("id",  Integer.parseInt(target.get("id").toString()))
//                .append("type",  target.get("type"))
//                .append("_rev",  target.get("_rev"))
//                .append("_id",  target.get("_id"));
        Document query = new Document()
                .append("_rev",  target.get("_rev"))
                .append("_id",  target.get("_id"));

//        System.out.println("Trying to delete: " + query.toJson());

        try {
            target.remove("updating");
//            System.out.println("deleting " + ((Document)target.get("_id")).getString("$oid") + " - " + target.get("_rev") + " - " + target.getString("id"));
            System.out.println("deleting " + query.toJson());
            DeleteResult dres = collection.deleteOne(query);

            final long dewleteCoujnt = dres.getDeletedCount();
            System.err.println("Deleted: " + dewleteCoujnt);

            if (dewleteCoujnt == 1) {
                System.err.println("Updating item.");
            }
            else {
                System.err.println("Inserting item.");
            }

            MongoHelper.updateRev(target);
            System.out.println("New rev: " + target.getString("_rev"));
            collection.insertOne(target);

            if(!target.containsKey("_id")) {
                System.out.println("Searching target after insertion...");
                final Document found = this.findByMongoID(collection, target);

                if (found == null) {
                    System.out.println("No target found.");
                    return "";
                }
                else {
                    System.out.println("Found " + found.toJson());
                    target.put("_id", found.get("_id"));
                }
            }

//            final String res = target.get("_id").toString();
//            final org.bson.types.ObjectId resD = (org.bson.types.ObjectId)target.get("_id");
//
//            System.out.println("Returning " + res);
//            System.out.println("maybe " + resD.toString());

            if(target.containsKey("password")) {
                target.replace("password", "");
            }

            return target.toJson();
        } catch (Exception me) {
            System.err.println("Unable to insert due to an error: " + me);
            return null;
        }
    }

    private Document findByMongoID(final MongoCollection<Document> collection, Document target) {
        FindIterable<Document> foundDocs = collection.find (target);
        Iterator it = foundDocs.iterator();
//                System.out.println(String.format("Found %d targets", foundDocs.));
        if (!it.hasNext()) {
//            System.out.println("No target found.");
            return null;
        }

        final Document found = (Document)it.next();
        return found;
    }

    private static void updateRev(Document target) throws NoSuchAlgorithmException {
        final boolean hasRev = target.containsKey("_rev");
        final String currentRev = hasRev ? target.getString("_rev") : null;

        System.out.println("currentRev: " + currentRev);

        final String newRev = (currentRev == null ? "1" : String.valueOf(Integer.parseInt(currentRev.split("_")[0]) + 1))
            + "_" + ND5Helper.hash(target.toJson()).toLowerCase();

        System.out.println("newRev: " + newRev);

        if(hasRev) {
            target.replace("_rev", newRev);
        }
        else {
            target.put("_rev", newRev);
        }
    }

    private void backUp(MongoCollection<Document> collection, final String colName) {
        List<Document> allDocs = this.getAllDocs(colName, true);

        final long unixTime = System.currentTimeMillis();
        final long batch = (unixTime - Long.parseLong("1668131145132")) / 1000; // nb seconds since that time
//        final long batch = (unixTime - Long.parseLong("1668131145132")) / 1000; // nb seconds since that time

        for(Document doc: allDocs) {
            doc.remove("_id");
            doc.put("batch", batch);
        }

        if(allDocs.size() == 0) {
            return;
        }

        this.insertData("archive", SplitManUtils.listToJson(allDocs));
    }

    private List<Document> parseList(String json) throws ParseException {
        JSONParser jp = new JSONParser();
        JSONArray o = (org.json.simple.JSONArray)jp.parse(json);

        return (List<Document>) o.stream().map(b -> Document.parse(((JSONObject)b).toJSONString()))
                .collect(Collectors.toList());
    }

    public boolean insertData(String collectionName, String json) {
        try {
            return insertJson(collectionName, json);
        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public String upsertData(String collectionName, String json) {
        try {
            this.backUp(null, collectionName);
            return upsertJson(collectionName, json);
        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public void deleteAll(String collectionName) {
        MongoDatabase database = this._mongo.getDatabase(this._dbName);

        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.deleteMany(Document.parse("{}"));
    }
}
