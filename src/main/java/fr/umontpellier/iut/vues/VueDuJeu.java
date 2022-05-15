package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Destination;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    private EventHandler<ActionEvent> gestionnaireEvenement;

    private ListChangeListener<Destination> destinationsInitialesListener;
    private Button boutonPasser;
    private Label nomJoueurCourant;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        plateau = new VuePlateau();

        nomJoueurCourant = new Label();
        boutonPasser = new Button("Passer");

        getChildren().add(nomJoueurCourant);
        getChildren().add(boutonPasser);
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
        destinationsInitialesListener = (elementChange) -> {
            String nomDestination;
            while(elementChange.next()) {
                if (elementChange.wasRemoved()) {
                    List<? extends Destination> listeSuppressions = elementChange.getRemoved();
                    for (Destination destination : listeSuppressions) {
                        nomDestination = destination.getNom();
                        System.out.println(nomJoueurCourant.getText() + " a pioché " + nomDestination);
                    }
                }
            }
        };
        jeu.destinationsInitialesProperty().addListener(destinationsInitialesListener);
    }

}