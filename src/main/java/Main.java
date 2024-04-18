import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class Main extends Application {
    private DatabaseManager dbManager;
    private TextField idField = new TextField();
    private TextField nameField = new TextField();
    private TextField ageField = new TextField();
    private TextField cityField = new TextField();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Anton MongoDB CRUD");

        setupUI(primaryStage);

        dbManager = new DatabaseManager();
    }

    private void setupUI(Stage primaryStage) {
        Button createButton = new Button("Create");
        Button readButton = new Button("Read");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");

        createButton.setOnAction(e -> dbManager.create(idField.getText(), nameField.getText(), ageField.getText(), cityField.getText()));
        readButton.setOnAction(e -> dbManager.read(idField.getText()));
        updateButton.setOnAction(e -> dbManager.update(idField.getText(), nameField.getText(), ageField.getText(), cityField.getText()));
        deleteButton.setOnAction(e -> dbManager.delete(idField.getText()));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(20, 20, 20, 20));
        hBox.getChildren().addAll(createButton, readButton, updateButton, deleteButton);
        layout.getChildren().addAll(new Label("ID"), idField, new Label("Name"), nameField, new Label("Age"), ageField, new Label("City"), cityField, hBox);

        Scene scene = new Scene(layout, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
