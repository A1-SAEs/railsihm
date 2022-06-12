package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends VBox {

    public VueAutresJoueurs(IJeu jeu) {
       for(int i=0; i<jeu.getJoueurs().size()-1;i++){
           getChildren().add(new VueJoueur());
       }
       setSpacing(5);
    }

    public void creerBindings(IJeu jeu){
        for(Node child : getChildren()){
            ((VueJoueur) child).creerBindings(jeu);
        }
    }

}
