import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.scene.control.Alert;
import org.bson.Document;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseManager {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public DatabaseManager() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(".env"));
            String dbConnectionString = properties.getProperty("MONGOSTRING");
            if (dbConnectionString == null) {
                throw new RuntimeException("not found in .env file");
            }

            ConnectionString connString = new ConnectionString(dbConnectionString);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase("test");
            collection = database.getCollection("users");

        } catch (IOException e) {
            throw new RuntimeException("Failed to load .env file", e);
        }
    }

    public void create(String id, String name, String age, String city) {
        Document doc = new Document("id", id)
                .append("name", name)
                .append("age", age)
                .append("city", city);
        collection.insertOne(doc);
        showAlert("Create Operation", "Document created successfully");
    }

    public void read(String id) {
        Document doc = collection.find(new Document("id", id)).first();
        System.out.println(doc.toJson());
        showAlert("Read Operation", "Document read successfully");
    }

    public void update(String id, String name, String age, String city) {
        Document doc = new Document("id", id);
        Document newDoc = new Document("id", id)
                .append("name", name)
                .append("age", age)
                .append("city", city);
        collection.replaceOne(doc, newDoc);
        showAlert("Update Operation", "Document updated successfully");
    }

    public void delete(String id) {
        Document doc = new Document("id", id);
        collection.deleteOne(doc);
        showAlert("Delete Operation", "Document deleted successfully");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

