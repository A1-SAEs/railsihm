package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 *
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends VBox {

    private IJeu jeu;
    private VuePlateau plateau;

    private EventHandler<ActionEvent> gestionnaireEvenement;

    private ListChangeListener<Destination> destinationsInitialesListener;
    private Button boutonPasser;
    private Label nomJoueurCourant;

    private VBox listeDestinations;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        plateau = new VuePlateau();

        nomJoueurCourant = new Label();
        boutonPasser = new Button("Passer");
        listeDestinations = new VBox();

        getChildren().add(nomJoueurCourant);
        getChildren().add(boutonPasser);
        getChildren().add(listeDestinations);
        //getChildren().add(plateau);
    }

    public IJeu getJeu() {
        return jeu;
    }

    public void creerBindings() {
        //Bouton passer
        boutonPasser.setOnAction((event) -> {
            jeu.passerAEteChoisi();
            nomJoueurCourant.setText(jeu.joueurCourantProperty().getValue().getNom());
        });

        //Pioche des destinations initiales
        destinationsInitialesListener = (elementChange) -> Platform.runLater(() -> {
            while (elementChange.next()) {
                if (elementChange.wasAdded()) { //Peut-être devoir changer vers un wasAdded
                    List<? extends Destination> listeAjouts = elementChange.getAddedSubList();
                    for (Destination destination : listeAjouts) {
                        listeDestinations.getChildren().add(new Label(destination.getNom()));
                    }
                }
                if (elementChange.wasRemoved()){
                    List<? extends Destination> listeSuppressions = elementChange.getRemoved();
                    for(Destination destination : listeSuppressions){
                        listeDestinations.getChildren().remove(destinationVersLabel(destination));
                    }
                }
            }
            });
        jeu.destinationsInitialesProperty().addListener(destinationsInitialesListener);
    }

    //Recherche d'une destination en son label correspondant
    public Label destinationVersLabel(IDestination destination){
        for(Node label : listeDestinations.getChildren()){
            if(((Label) label).getText().equals(destination.getNom())){
                return (Label) label;
            }
        }
        return null;
    }
}