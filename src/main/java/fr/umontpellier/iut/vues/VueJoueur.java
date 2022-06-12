package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class VueJoueur extends HBox {

    //Affichages labels
    @FXML
    private Label nomJoueur;
    @FXML
    private Label wagonsJoueur;
    @FXML
    private Label scoreJoueur;
    @FXML
    private Label garesJoueur;

    //Affichages images
    @FXML
    private ImageView imageJoueur;
    @FXML
    private ImageView imageWagons;
    @FXML
    private ImageView imageGares;
    @FXML
    private ImageView imageScore;



    public VueJoueur(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/joueur.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void creerBindings(IJeu jeu){

        prefWidthProperty().bind(((VBox) getParent()).prefWidthProperty());
    }

}
