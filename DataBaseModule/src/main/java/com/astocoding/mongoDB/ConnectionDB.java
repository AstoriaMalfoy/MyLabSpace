package com.astocoding.mongoDB;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Random;

/**
 * @author litao34
 * @ClassName ConnectionDB
 * @Description TODO
 * @CreateDate 2022/10/14-18:01
 **/
public class ConnectionDB {

    private static final String address = "82.157.8.84";
    private static final Integer port = 8020;

    private static final Integer count = 10000;

    private static final Random randmo = new Random(System.currentTimeMillis());

    public MongoDatabase getDataBase(String database)throws Exception {
        MongoClient mongoClient = new MongoClient(address, port);
        return mongoClient.getDatabase(database);
    }

    public static void main(String[] args) {
        ConnectionDB connectionDB = new ConnectionDB();
        MongoDatabase db = null;
        try {
            db = connectionDB.getDataBase("shard");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        MongoCollection<Document> collection = db.getCollection("user");

        for (int i = 0 ;i < count ; i ++){
            Document document = new Document();
            document.append("name",getRandomName());
            document.append("gender",getRandomGender());
            document.append("id",getRandomId());
            document.append("age",getRandomAge());
            System.out.println("insert document "  + document.toString());
            collection.insertOne(document);

        }

    }

    private static Integer getRandomAge() {
        return Math.abs(randmo.nextInt()) % 100;
    }

    private static Integer getRandomId() {
        return Math.abs(randmo.nextInt()) % 10000;
    }

    private static String getRandomGender() {
        return Math.abs(randmo.nextInt()) % 2 == 1 ? "man" : "woman";
    }

    private static String getRandomName() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i =0 ; i < 5; i ++){
            if (i == 0){
                stringBuilder.append('A' +  Math.abs(randmo.nextInt()) % 26);
            }else{
                stringBuilder.append('a' + Math.abs(randmo.nextInt()) % 26);
            }
        }
        return stringBuilder.toString();
    }
}
