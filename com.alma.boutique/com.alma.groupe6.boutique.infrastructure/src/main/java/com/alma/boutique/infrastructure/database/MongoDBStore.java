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
public class MongoDBStore implements DatabaseFacade {
    private static final Logger logger = Logger.getLogger(MongoDBStore.class);

    private static MongoDBStore instance = null;
    private MongoClient client;
    private MongoDatabase database;
    private ObjectMapper mapper;

    private MongoDBStore() throws IOException {
        try {
            Properties infos = new Properties();
            infos.load(new FileInputStream("src/main/resources/mongodb.properties"));
            String mongodbURL = String.format("mongodb://%s:%s@%s/%s", infos.getProperty("database.username"), infos.getProperty("database.password"),
                    infos.getProperty("database.url"), infos.getProperty("database.name"));
            client = new MongoClient(new MongoClientURI(mongodbURL));
            database = client.getDatabase(infos.getProperty("database.name"));
            mapper = new ObjectMapper();
        } catch (IOException e) {
            logger.error(e);
            throw e;
        }
    }

    public static MongoDBStore getInstance() throws IOException {
        if(instance == null) {
            synchronized (MongoDBStore.class) {
                if(instance == null) {
                    instance = new MongoDBStore();
                }
            }
        }
        return instance;
    }

    private MongoCollection<Document> getCollection(Class dataClass) {
        String collectionName = dataClass.getSimpleName() + "Collection";
        MongoCollection<Document> collection = database.getCollection(collectionName);
        if(collection == null) {
            // create the new collection
            collectionName = dataClass.getSimpleName() + "Collection";
            database.createCollection(collectionName);
            collection = database.getCollection(collectionName);
        }
        return collection;
    }

    @Override
    public void create(int id, Object entity) {
        try {
            // read the collection corresponding to the object, then insert it
            MongoCollection<Document> collection = getCollection(entity.getClass());
            // create mongodb object to insert into the database using reflection
            Document newDocument = Document.parse(mapper.writeValueAsString(entity));
            collection.insertOne(newDocument);
        } catch (JsonProcessingException e) {
            logger.warn(e);
        }
    }

    @Override
    public <C> C retrieve(int id, Class<C> entityType) {
        Object entity = null;
        Document document = getCollection(entityType).find(eq("id", id)).first();
        // remove mongodb metadata before deserialization
        document.remove("_id");
        try {
            entity = mapper.readValue(document.toJson(), entityType);
        } catch (IOException e) {
            logger.warn(e);
        }
        return (C) entity;
    }

    @Override
    public <C> List<C> retrieveAll(Class<C> entityType) {
        List<C> results = new ArrayList<>();
        FindIterable<Document> allDocuments = getCollection(entityType).find();
        for(Document document : allDocuments) {
            // remove mongodb metadata before deserialization
            document.remove("_id");
            try {
                results.add(mapper.readValue(document.toJson(), entityType));
            } catch (IOException e) {
                logger.warn(e);
            }
        }
        return results;
    }

    @Override
    public void update(int id, Object entity) {
        delete(id, entity.getClass());
        create(id, entity);
    }

    @Override
    public void delete(int id, Class entityType) {
        // read the collection corresponding to the object, then apply deletion
        getCollection(entityType).deleteOne(eq("id", id));
    }
}
