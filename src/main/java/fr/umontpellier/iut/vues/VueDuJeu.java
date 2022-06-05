package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
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
public class VueDuJeu extends HBox {

    //Vues
    private IJeu jeu;
    @FXML
    private VuePlateau plateau;
    @FXML
    private VueJoueurCourant vueJoueurCourant;

    //Instructions
    private ChangeListener<String> instructionsListener;
    @FXML
    Label instructions;

    //Conteneur pioche visible
    private ListChangeListener<CouleurWagon> piocheVisibleListener;
    @FXML
    private VBox piocheVisible;

    //Choix des destinations
    private ListChangeListener<Destination> choixDestinationsListener;
    @FXML
    private VBox destinationsInitiales;

    //Bouton passer tour
    @FXML
    private Button boutonPasser;

    public VueDuJeu(IJeu jeu) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/jeu.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.jeu = jeu;

        boutonPasser = new Button("Passer");
    }

    public IJeu getJeu() {
        return jeu;
    }

    @FXML
    public void passer(){
        jeu.passerAEteChoisi();
    }

    @FXML
    public void piocherDestination(){ jeu.uneDestinationAEtePiochee();}

    @FXML
    public void piocherCarte(){ jeu.uneCarteWagonAEtePiochee();}

    public void creerBindings() {
        //Pioche des destinations
        choixDestinationsListener = elementChange -> Platform.runLater(() -> {
            while (elementChange.next()) {
                if (elementChange.wasAdded()) {
                    List<? extends IDestination> listeAjouts = elementChange.getAddedSubList();
                    for(IDestination destination : listeAjouts){
                        destinationsInitiales.getChildren().add(new VueDestination(destination));
                    }
                }
                if (elementChange.wasRemoved()){
                    List<? extends IDestination> listeSuppressions = elementChange.getRemoved();
                    for(IDestination destination : listeSuppressions){
                        destinationsInitiales.getChildren().remove(destinationVersVue(destination));
                    }
                }
            }
            });

        //Pioche des cartes visibles
        piocheVisibleListener = elementChange -> Platform.runLater(() -> {
            while (elementChange.next()) {
                if (elementChange.wasAdded()) {
                    List<? extends ICouleurWagon> listeAjouts = elementChange.getAddedSubList();
                    for(ICouleurWagon carte : listeAjouts){
                        piocheVisible.getChildren().add(new VueCarteWagon(carte));
                    }
                }
                if (elementChange.wasRemoved()){
                    List<? extends ICouleurWagon> listeSuppressions = elementChange.getRemoved();
                    for(ICouleurWagon carte : listeSuppressions){
                        piocheVisible.getChildren().remove(carteVersVue(carte));
                    }
                }
            }
        });
        instructionsListener = (observableValue, ancienneValeur, nouvelleValeur) -> Platform.runLater(() -> {
            instructions.setText(nouvelleValeur);
        });

        jeu.instructionProperty().addListener(instructionsListener);
        jeu.destinationsInitialesProperty().addListener(choixDestinationsListener);
        jeu.cartesWagonVisiblesProperty().addListener(piocheVisibleListener);

        //Création des liaisons dans la vue du joueur courant
        vueJoueurCourant.creerBindings(this.getJeu());
        //plateau.creerBindings();
    }

    //Recherche d'une destination en sa vue correspondante
    public VueDestination destinationVersVue(IDestination destination){
        for(Node vueDestination : destinationsInitiales.getChildren()){
            if(((VueDestination) vueDestination).getDestination().equals(destination)){
                return (VueDestination) vueDestination;
            }
        }
        return null;
    }

    public VueCarteWagon carteVersVue(ICouleurWagon carte){
        for(Node vueCarteWagon : piocheVisible.getChildren()){
            if(((VueCarteWagon) vueCarteWagon).getCouleurWagon().equals(carte)){
                return (VueCarteWagon) vueCarteWagon;
            }
        }
        return null;
    }
}