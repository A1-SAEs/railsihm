package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.security.PrivateKey;

/**
 * Cette classe représente la vue d'une carte Wagon.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagonJoueur extends Pane {

    private ICouleurWagon couleurWagon;
    private int nombre;

    @FXML
    private Label labelNombre;
    @FXML
    private ImageView imageCarte;

    public VueCarteWagonJoueur(ICouleurWagon couleurWagon, int nombre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/carteWagonJoueur.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.couleurWagon = couleurWagon;
        this.nombre = nombre;

        this.setOnMouseClicked(event -> {
            ((VueDuJeu) getScene().getRoot()).getJeu().uneCarteWagonAEteChoisie(couleurWagon);
            if(this.nombre>0){
                this.nombre--;
                this.labelNombre.setText(String.valueOf(this.nombre));
            }
        });
        imageCarte.setImage((new Image("images/cartesWagons/carte-wagon-" + couleurWagon + ".png")));
        labelNombre.setText(String.valueOf(nombre));
    }

    public void setNombre(int nombre){
        this.nombre = nombre;
        labelNombre.setText(String.valueOf(nombre));
    }

    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }

    public void creerBindings(){
        imageCarte.setPreserveRatio(true);
        imageCarte.setRotate(90);
        imageCarte.fitWidthProperty().bind(((HBox) getParent()).prefWidthProperty().divide(1.75));
    }
}
