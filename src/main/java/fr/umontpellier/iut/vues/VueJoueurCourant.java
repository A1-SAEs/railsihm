package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {

    Label nomJoueur;
    ChangeListener<IJoueur> changementJoueurCourantListener;

    public VueJoueurCourant(){
        nomJoueur = new Label();
        getChildren().add(nomJoueur);
    }

    public void creerBindings(IJeu jeu){
        changementJoueurCourantListener = (observableValue, ancienneValeur, nouvelleValeur) -> Platform.runLater(() -> {
            nomJoueur.setText(nouvelleValeur.getNom());
        });
        jeu.joueurCourantProperty().addListener(changementJoueurCourantListener);
    }

}
