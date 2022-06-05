package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends GridPane {
    //labels à afficher
    @FXML
    private Label nomJoueur;

    @FXML
    private Label score;

    @FXML
    private Label wagon;

    @FXML
    private Label gare;

    //images à afficher
    @FXML
    ImageView imageJoueur;
    @FXML
    ImageView imageWagons;
    @FXML
    ImageView imageGares;
    @FXML
    ImageView imageScore;

    private GridPane autreJoueurGrid;


    private ListChangeListener<IJoueur> changementAutresJoueursListener;
    private IJoueur autreJoueur;

    public VueAutresJoueurs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/jeu.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.autreJoueurGrid = new GridPane();
    }

    public void creerBindings(IJeu jeu){
        changementAutresJoueursListener = elementChange -> Platform.runLater(() -> {
            while (elementChange.next()) {
                if (elementChange.wasUpdated()) {
                    List<? extends IJoueur> listeUpdate = elementChange.getList();
                    for(IJoueur joueur : listeUpdate){
                        nomJoueur.setText(joueur.getNom());
                        wagon.setText(String.valueOf(joueur.getNbWagons()));
                        score.setText(String.valueOf(joueur.getScore()));
                        gare.setText(String.valueOf(joueur.getNbGares()));

                        imageJoueur.setImage(new Image("images/images/avatar-"+ joueur.getCouleur() + ".png"));
                        imageGares.setImage(new Image("images/gares/gare-" + joueur.getCouleur() + ".png"));
                        imageWagons.setImage(new Image("images/wagons/image-wagon-" + joueur.getCouleur() + ".png"));

                    }
                }

            }
        });


        jeu.joueurCourantProperty().addListener(changementAutresJoueursListener);

    }

}
