/***
Global license : 

    CC Attribution
    
    author Manuel Dahmen <ibiiztera.it@gmail.com>

***/


package info.emptycanvas.apps.darkfortress;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Manuel Dahmen <ibiiztera.it@gmail.com>
 */
public abstract class Card {
    public final int CARD_TYPE_COLORS = 0;
    public final int CARD_TYPE_POLYGONS = 1;
    
    public abstract GamePosition getGamePosition();
    public abstract void setGamePosition(GamePosition coordonnees);
    public abstract BufferedImage genererImage();
    public abstract void initCard(int width, int height, Color [][] objet);
}
