package com.hascode.uml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private MenuItem closeButton;

    @FXML
    private TextArea codeInput;

    @FXML
    private ImageView umlImage;

    @FXML
    private AnchorPane imagePanel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        umlImage.fitWidthProperty().bind(imagePanel.widthProperty());
        umlImage.fitHeightProperty().bind(imagePanel.heightProperty());
        codeInput.setText("@startuml\nMe -> You :love\nYou -> Me : love\n@enduml");
        codeInput.setOnKeyReleased(event -> {
            updateImage(codeInput.getText());
        });
        closeButton.setOnAction(event -> System.exit(0));
        updateImage(codeInput.getText());
    }

    private void updateImage(String code) {
        try {
            File tmp = File.createTempFile("plantuml-edit", ".tmp");
            OutputStream png = new FileOutputStream(tmp);
            SourceStringReader reader = new SourceStringReader(code);
            String desc = reader.generateImage(png);
            umlImage.setImage(new Image("file:"+tmp.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
