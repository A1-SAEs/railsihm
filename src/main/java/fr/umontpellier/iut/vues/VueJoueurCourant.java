package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends HBox {

    @FXML
    Label nomJoueur;
    @FXML
    Label wagonJoueur;
    @FXML
    Label scoreJoueur;

    HBox cartesJoueurCourant;
    VBox destinationsJoueurCourant;
    ChangeListener<IJoueur> changementJoueurCourantListener;
    ChangeListener<ICouleurWagon> changementCartesJoueursListener;
    IJoueur joueurCourant;

    public VueJoueurCourant(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/joueurCourant.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cartesJoueurCourant = new HBox();
        destinationsJoueurCourant = new VBox();
    }

    public void creerBindings(IJeu jeu){
        joueurCourant = jeu.getJoueurs().get(0);

        changementJoueurCourantListener = (observableValue, ancienneValeur, nouvelleValeur) -> Platform.runLater(() -> {
            joueurCourant = nouvelleValeur;
            //Changement des labels du joueur
            nomJoueur.setText(nouvelleValeur.getNom());
            wagonJoueur.setText(String.valueOf(nouvelleValeur.getNbWagons()));
            scoreJoueur.setText(String.valueOf(nouvelleValeur.getScore()));

            //Nettoyage des cartes et destinations du joueur précédent
            cartesJoueurCourant.getChildren().clear();

            //Mise en place des cartes et destinations du joueur actuel
            for(CouleurWagon carteJoueur : nouvelleValeur.getCartesWagon()){
                cartesJoueurCourant.getChildren().add(new Label(carteJoueur.name()));
            }

            destinationsJoueurCourant.getChildren().clear();
            for(Destination destinationJoueur : nouvelleValeur.getDestinations()) {
                destinationsJoueurCourant.getChildren().add(new Label(destinationJoueur.getNom()));
            }
        });

        jeu.joueurCourantProperty().addListener(changementJoueurCourantListener);
    }

    public Label carteVersLabel(CouleurWagon couleur){
        for(Node label : cartesJoueurCourant.getChildren()){
            if(((Label) label).getText().equals(couleur.toString())){
                return (Label) label;
            }
        }
        return null;
    }


}
