package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private VueJoueurCourant vueJoueurCourant;

    private EventHandler<ActionEvent> gestionnaireEvenement;

    //Destinations initiales
    private ListChangeListener<Destination> destinationsInitialesListener;
    private VBox listeDestinations;

    private Button boutonPasser;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        vueJoueurCourant = new VueJoueurCourant();
        //plateau = new VuePlateau();

        boutonPasser = new Button("Passer");
        listeDestinations = new VBox();

        getChildren().add(vueJoueurCourant);
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
        });

        //Pioche des destinations initiales
        destinationsInitialesListener = (elementChange) -> Platform.runLater(() -> {
            while (elementChange.next()) {
                if (elementChange.wasAdded()) {
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

        //Création des liaisons dans la vue du joueur courant
        vueJoueurCourant.creerBindings(this.getJeu());
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