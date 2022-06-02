package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import javafx.scene.image.ImageView;

public enum Images implements ICouleurWagon {
    NOIR, BLANC, JAUNE, ROUGE, ORANGE, BLEU, VERT, ROSE, LOCOMOTIVE;

    public ImageView setImage() {
        return switch (this) {
            case NOIR -> new ImageView("images/cards/eu_WagonCard_black.png");
            case BLANC -> new ImageView("images/cards/eu_WagonCard_white.png");
            case JAUNE -> new ImageView("images/cards/eu_WagonCard_yellow.png");
            case ROUGE -> new ImageView("images/cards/eu_WagonCard_red.png");
            case ORANGE -> new ImageView("images/cards/eu_WagonCard_brown.png");
            case BLEU -> new ImageView("images/cards/eu_WagonCard_blue.png");
            case VERT -> new ImageView("images/cards/eu_WagonCard_green.png");
            case ROSE -> new ImageView("images/cards/eu_WagonCard_purple.png");
            case LOCOMOTIVE -> new ImageView("images/cards/eu_WagonCard_loco.png");
        };
    }
}
