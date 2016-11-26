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
 * Classe singleton représentant une base de données MongoDB
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
     * Constructeur privé
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
     * Accesseur permettant de récupérer l'instance de la base de données
     * @return L'instance de la base de données MongoDB
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
     * @param path Le chemin vers le fichier de configuration (au format .properties)
     */
    public static void setConfigFile(String path) {
        configFile = path;
    }

    /**
     * Méthode privée qui permet d'accéder à une collection de documents, et de la créer si elle n'existe pas encore
     * @param dataClass La classe des objets correspondant à la collection désirée
     * @return La collection de documents correspondant à la classe passée en paramètre
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
     * Méthode enregistrant un objet dans la base de données
     * @param id L'id unique de l'objet
     * @param entity L'objet à sauvegarder
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
     * Méthode récupérant un objet dans la base de données
     * @param id L'id unique de l'objet
     * @param entityType La classe de l'objet recherché
     * @return L'objet correspondant à l'id passé en paramètre
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
     * Méthode permettant de récupérer tous les objets dans la base qui correspondent à une classe donnée
     * @param entityType La classe des objets que l'on veut récupérer
     * @return La liste de tous les objets stockés dans la base de données correspondant à la classe passée en paramètre
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
     * Méthode mettant à jour un objet déjà présent dans la base de donnée
     * @param id L'id unique de l'objet à mettre à jour
     * @param entity La nouvelle version de l'objet
     */
    @Override
    public void update(int id, Object entity) {
        delete(id, entity.getClass());
        create(id, entity);
    }

    /**
     * Méthode supprimant un objet de la base de données
     * @param id l'id unique de l'objet à supprimer
     * @param entityType La classe correspodant à l'objet que l'on veut supprimer
     */
    @Override
    public void delete(int id, Class entityType) {
        // read the collection corresponding to the object, then apply deletion
        getCollection(entityType).deleteOne(eq("id", id));
    }
}
