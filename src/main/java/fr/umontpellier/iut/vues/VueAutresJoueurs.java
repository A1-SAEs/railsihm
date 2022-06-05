package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
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
    @FXML
    private Label nomJoueur;

    @FXML
    private Label score;

    @FXML
    private Label wagon;

    private VBox autresJoueursBox;
    private List<Integer> nbWagon;
    private List<Integer> nbScore;
    private List<Integer> nbGare;

    ChangeListener<List<IJoueur>> changementAutresJoueursListener;
    List<IJoueur> autresJoueurs;

    public VueAutresJoueurs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/jeu.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.autresJoueursBox = new VBox();
        this.nbWagon = new ArrayList<>();
        this.nbScore = new ArrayList<>();
        this.nbGare = new ArrayList<>();
        this.autresJoueurs = new ArrayList<>();
    }

    public void creerBindings(IJeu jeu){
        autresJoueurs.addAll(jeu.getJoueurs());
        autresJoueurs.remove(0);

        changementAutresJoueursListener = (observableValue, ancienneValeur, nouvelleValeur) -> Platform.runLater(() -> {
            autresJoueurs = nouvelleValeur;
            for(IJoueur joueur: autresJoueurs){
                //nom
                nbWagon.add(joueur.getNbWagons());
                nbScore.add(joueur.getScore());
                nbGare.add(joueur.getNbGares());
            }
            /*
            nomJoueur.setText(nouvelleValeur.getNom());
            nbWagon.setValue(nouvelleValeur.getNbWagons());
            nbScore.setValue(nouvelleValeur.getScore());
            nbGare.setValue(nouvelleValeur.getNbGares());
            wagon.setText(nbWagon.toString());
            score.setText(nbScore.toString());
            */
        });

        //jeu.joueurCourantProperty().addListener(changementAutresJoueursListener);

    }

}
