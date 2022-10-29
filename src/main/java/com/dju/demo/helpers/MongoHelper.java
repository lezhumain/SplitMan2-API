package com.dju.demo.helpers;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoHelper {
    private final String _dbName;
    private MongoClient _mongo;
    private MongoCredential _credential;

    public MongoHelper(String host, int port, String username, String pass, String dbName) {
        final String theUrl = String.format("mongodb://%s:%s@%s", username, pass, host, port);

        // Creating a Mongo client
//        this._mongo = new MongoClient( host , port );
        this._mongo = MongoClients.create(theUrl);

        this._dbName = dbName;

        // Creating Credentials
        this._credential = MongoCredential.createCredential(username, this._dbName,
                pass.toCharArray());
        System.out.println("Connected to the database successfully");
    }

    public MongoDatabase accessDb(String dbName) {
        // Accessing the database
        MongoDatabase database = this._mongo.getDatabase(this._dbName);
        System.out.println("Credentials ::" + this._credential);
        return database;
    }

    //    customers
    public void createCollection(String colName) {
        // Accessing the database
        MongoDatabase database = this._mongo.getDatabase(this._dbName);

        //Creating a collection
        database.createCollection(colName);

        System.out.println("Collection created successfully");
    }

    public String selectData(String colName) {
        List<Document> all = getAllDocs(colName);
        String res = "[";

        if (all.size() > 0) {
            for(Document d: all) {
                res += d.toJson() + ",";
            }

            res = res.substring(0, res.length() - 2); // remove last ','
        }
        res += "]";

        return res;
    }

    public List<Document> getAllDocs(String colName) {
        List<Document> ress = new ArrayList<>();

        // Accessing the database
        MongoDatabase database = this._mongo.getDatabase(this._dbName);

        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection(colName);

        // Getting the iterable object
        FindIterable<Document> iterDoc = collection.find();
        int i = 1;
        // Getting the iterator
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            Document d = (Document) it.next();
            System.out.println(d.toJson());
            ress.add(d);
            i++;
        }
        return ress;
    }

    public void insertDocs(String colName) {
        // Accessing the database
        MongoDatabase database = this._mongo.getDatabase(this._dbName);

        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("sampleCollection");
        System.out.println("Collection sampleCollection selected successfully");
        Document document1 = new Document("title", "MongoDB")
                .append("description", "database")
                .append("likes", 100)
                .append("url", "http://www.tutorialspoint.com/mongodb/")
                .append("by", "tutorials point");
        Document document2 = new Document("title", "RethinkDB")
                .append("description", "database")
                .append("likes", 200)
                .append("url", "http://www.tutorialspoint.com/rethinkdb/")
                .append("by", "tutorials point");
        List<Document> list = new ArrayList<Document>();
        list.add(document1);
        list.add(document2);
        collection.insertMany(list);
        // Getting the iterable object
        FindIterable<Document> iterDoc = collection.find();
        int i = 1;
        // Getting the iterator
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            Document d = (Document) it.next();
            System.out.println(d);
            i++;
        }
    }

    public boolean insertJson(final String colName, final String json) {
        // Accessing the database
        MongoDatabase database = this._mongo.getDatabase(this._dbName);

        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection(colName);
        try {
            collection.insertOne(Document.parse(json));
//            System.out.println("Success! Inserted document id: " + result.getInsertedId());
            return true;
        } catch (MongoException me) {
            System.err.println("Unable to insert due to an error: " + me);
            return false;
        }
    }

//    public void updateJson(final String colName, final  String json) {
//        // Accessing the database
//        MongoDatabase database = this._mongo.getDatabase(this._dbName);
//
//        DBObject dbObject = (DBObject) JSON.parse(json);
//
//        // Retrieving a collection
//        MongoCollection<Document> collection = database.getCollection(colName);
//        collection.insertOne(Document.parse(json));
//
////        collection.insert(dbObject);
//    }

    public Document findBy(final String colName, final String key, final String docId) {
        List<Document> allDocs = getAllDocs(colName);
        Document res = allDocs.stream()
                .filter(document -> document.get(key).toString().equals(docId))
                .findFirst().orElse(null);

//        Document res = null;
//        for(Document d: allDocs) {
//            if(d.get(key).toString().equals(docId)) {
//                System.out.println(d.toJson());
//                res = d;
//                break;
//            }
//        }

        if (res == null) {
            System.out.println("res is null");
        }

//        // Accessing the database
//        MongoDatabase database = this._mongo.getDatabase(this._dbName);
//
//        // Retrieving a collection
//        MongoCollection<Document> collection = database.getCollection(colName);
//        Document res = collection.find(eq(key, docId))
////                .projection(projectionFields)
////                .sort(Sorts.descending("imdb.rating"))
//                .first();

        return res;
    }

    public Document findById(final String colName, final String docId) {
        return findBy(colName, "_id", docId);
    }

    public void updateOne(String colName, final String jsonItem) {
        // Accessing the database
        MongoDatabase database = this._mongo.getDatabase(this._dbName);

        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection(colName);

        Document newDoc = Document.parse(jsonItem);
        Bson query = eq("_id", newDoc.get("_id"));
        ReplaceOptions ro = new ReplaceOptions().upsert(true);
        UpdateResult ur = collection.replaceOne(query, newDoc, ro);

        System.out.println("Mod count: " + ur.getModifiedCount());
        System.out.println("upserted id: " + ur.getUpsertedId());

//        List<Document> ress = new ArrayList<>();
//
//        // Accessing the database
//        MongoDatabase database = this._mongo.getDatabase(this._dbName);
//
//        // Retrieving a collection
//        MongoCollection<Document> collection = database.getCollection(colName);
//
//        Document d = Document.parse(jsonItem);
////        UpdateResult updateResult = collection.updateOne(eq("_id", d.get("_id")),
////                Updates. setOnInsert(d.toBsonDocument()));
//
//
//
//        BasicDBObject newDocument = BasicDBObject.parse(jsonItem);
////        newDocument.put("name", "John");
//
//        BasicDBObject query = new BasicDBObject();
//        query.put("_id", newDocument.get("_id"));
//
//        BasicDBObject updateObject = new BasicDBObject();
//        updateObject.put("$set", newDocument);
//
//        collection.updateOne(query, updateObject);
    }

    public void deleteOne(String colName, final String jsonItem) {
        List<Document> ress = new ArrayList<>();

        // Accessing the database
        MongoDatabase database = this._mongo.getDatabase(this._dbName);

        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection(colName);

        BasicDBObject searchQuery = BasicDBObject.parse(jsonItem);

        collection.deleteOne(searchQuery);
    }

    public void updateOne1(String colName, final String jsonItem) {
        List<Document> ress = new ArrayList<>();

        // Accessing the database
        MongoDatabase database = this._mongo.getDatabase(this._dbName);

        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection(colName);

        BasicDBObject searchQuery = BasicDBObject.parse(jsonItem);

        collection.deleteOne(searchQuery);
        collection.insertOne(Document.parse(jsonItem));

    }

    public boolean insertData(String collectionName, String json) {
        return insertJson(collectionName, json);
    }
}
