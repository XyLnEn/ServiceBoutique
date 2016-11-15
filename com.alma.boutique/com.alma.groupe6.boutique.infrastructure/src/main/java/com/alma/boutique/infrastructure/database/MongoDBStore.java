package com.alma.boutique.infrastructure.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Thomas Minier
 */
public class MongoDBStore<T> {
    private static final Logger logger = Logger.getLogger(MongoDBStore.class);

    private static MongoDBStore instance = null;
    private Class<T> referenceClass;
    private String collectionName;
    private MongoClient client;
    private MongoDatabase database;
    private ObjectMapper mapper;

    private MongoDBStore(Class<T> referenceClass) throws IOException {
        try {
            Properties infos = new Properties();
            infos.load(new FileInputStream("src/main/resources/mongodb.properties"));
            String mongodbURL = String.format("mongodb://%s:%s@%s/%s", infos.getProperty("database.username"), infos.getProperty("database.password"),
                    infos.getProperty("database.url"), infos.getProperty("database.name"));
            client = new MongoClient(new MongoClientURI(mongodbURL));
            database = client.getDatabase(infos.getProperty("database.name"));
            mapper = new ObjectMapper();
            this.referenceClass = referenceClass;
            collectionName = referenceClass.getSimpleName() + "Collection";
        } catch (IOException e) {
            logger.error(e);
            throw e;
        }
    }

    public static MongoDBStore getInstance(Class<?> referenceClass) throws IOException {
        if(instance == null) {
          synchronized (MongoDBStore.class) {
              if(instance == null) {
                  instance = new MongoDBStore(referenceClass);
              }
          }
        }
        return instance;
    }

    private MongoCollection<Document> getCollection() {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        if(collection == null) {
            // create the new collection
            database.createCollection(collectionName);
            collection = database.getCollection(collectionName);
        }
        return collection;
    }

    public void create(int id, T entity) {
        try {
            // get the collection corresponding to the object, then insert it
            MongoCollection<Document> collection = getCollection();
            // create mongodb object to insert into the database using reflection
            Document newDocument = Document.parse(mapper.writeValueAsString(entity));
            collection.insertOne(newDocument);
        } catch (JsonProcessingException e) {
            logger.warn(e);
        }
    }

    public T retrieve(int id) {
        T entity = null;
        Document document = getCollection().find(eq("id", id)).first();
        // remove mongodb metadata before deserialization
        document.remove("_id");
        try {
            entity = mapper.readValue(document.toJson(), referenceClass);
        } catch (IOException e) {
            logger.warn(e);
        }
        return entity;
    }

    public List<T> retrieveAll() {
        List<T> results = new ArrayList<>();
        FindIterable<Document> allDocuments = getCollection().find();
        for(Document document : allDocuments) {
            // remove mongodb metadata before deserialization
            document.remove("_id");
            try {
                results.add(mapper.readValue(document.toJson(), referenceClass));
            } catch (IOException e) {
                logger.warn(e);
            }
        }
        return results;
    }

    public void update(int id, T entity) {
        delete(id);
        create(id, entity);
    }

    public void delete(int id) {
        // get the collection corresponding to the object, then apply deletion
        getCollection().deleteOne(eq("id", id));
    }
}
