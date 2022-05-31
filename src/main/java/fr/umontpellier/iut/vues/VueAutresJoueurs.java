package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends GridPane {
    @FXML
    private Label nomJoueur;

    @FXML
    private Label score;

    @FXML
    private Label wagon;

    private IntegerProperty nbWagon;
    private IntegerProperty nbScore;
    private IntegerProperty nbGare;



    ChangeListener<IJoueur> changementAutresJoueursListener;
    IJoueur autresJoueurs;

    public VueAutresJoueurs() {
        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/jeu.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        this.nbWagon = new SimpleIntegerProperty();
        this.nbScore = new SimpleIntegerProperty();
        this.nbGare = new SimpleIntegerProperty();
    }

    public void creerBindings(IJeu jeu){
        changementAutresJoueursListener = (observableValue, ancienneValeur, nouvelleValeur) -> Platform.runLater(() -> {
            nomJoueur.setText(nouvelleValeur.getNom());
            nbWagon.setValue(nouvelleValeur.getNbWagons());
            nbScore.setValue(nouvelleValeur.getScore());
            nbGare.setValue(nouvelleValeur.getNbGares());
            wagon.setText(nbWagon.toString());
            score.setText(nbScore.toString());
        });

        jeu.joueurCourantProperty().addListener(changementAutresJoueursListener);

    }

}
