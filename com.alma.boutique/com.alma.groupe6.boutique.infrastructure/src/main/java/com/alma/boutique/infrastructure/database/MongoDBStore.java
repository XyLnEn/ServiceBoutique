package com.alma.boutique.infrastructure.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.mongodb.client.model.Filters.eq;

/**
 * Singleton class representing a MongoDB database
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class MongoDBStore implements Database {
    private static final Logger logger = Logger.getLogger(MongoDBStore.class);

    private static String configFile = "src/main/resources/mongodb.properties";
    private static MongoDBStore instance = null;
    private MongoClient client;
    private MongoDatabase database;
    private ObjectMapper mapper;

    /**
     * Private constructor
     * @throws IOException
     */
    private MongoDBStore() throws IOException {
        try {
            Properties infos = new Properties();
            infos.load(new FileInputStream(configFile));
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

    /**
     * Getter that create the instance if it was not already created
     * @return the MongoDB instance
     * @throws IOException
     */
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

    /**
     * Mutateur statique utilisé pour changer le fichier de configuration utilisé par la base de données
     * Static method allowing the mutation of the configuration file used by the database
     * @param path the path to the configuration file (.properties)
     */
    public static void setConfigFile(String path) {
        configFile = path;
    }

    /**
     * Private method to access a collection of documents. Create the collection if it does not exist.
     * @param dataClass the class of the objects in the collection to find
     * @return the collection of documents of the correct class
     */
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

    /**
     * Method saving an object into the database
     * @param id the unique Id of the object
     * @param entity the object to save
     */
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

    /**
     * Method to get an object from the database
     * @param id the unique ID of the object
     * @param entityType the class of the object to find
     * @return the object
     */
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

    /**
     * Method to get every object of a certain class from the database
     * @param entityType the class of the objects to find
     * @return the list of every objects in the Database of the correct instance
     */
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

    /**
     * Method to update an already present object in the database
     * @param id the unique Id of the object
     * @param entity the new version of the object
     */
    @Override
    public void update(int id, Object entity) {
        delete(id, entity.getClass());
        create(id, entity);
    }

    /**
     * Method to delete an already existing object from the database
     * @param id the unique Id of the object
     * @param entityType the class of the object to delete
     */
    @Override
    public void delete(int id, Class entityType) {
        // read the collection corresponding to the object, then apply deletion
        getCollection(entityType).deleteOne(eq("id", id));
    }
}
