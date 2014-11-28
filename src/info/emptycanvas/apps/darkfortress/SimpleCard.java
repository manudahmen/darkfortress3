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
public class SimpleCard extends Card
{
    private int width;
    private int height;
    private GamePosition gamePosition;
    
    public SimpleCard()
    {
        
    }
    
    
    
    @Override
    public GamePosition getGamePosition() {
        return gamePosition;
    }

    @Override
    public void setGamePosition(GamePosition coordonnees) {
        this.gamePosition = coordonnees;
    }

    @Override
    public BufferedImage genererImage() {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bi.setRGB((int)(gamePosition.positionCard().getX())*width, (int)(gamePosition.positionCard().getY()*height), Color.YELLOW.getRGB());
        return bi;
    }

    @Override
    public void initCard(int width, int height, Color[][] objet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
