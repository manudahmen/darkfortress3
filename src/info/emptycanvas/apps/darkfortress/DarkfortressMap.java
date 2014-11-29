/***
Global license : 

    CC Attribution
    
    author Manuel Dahmen <ibiiztera.it@gmail.com>

***/


package info.emptycanvas.apps.darkfortress;

import info.emptycanvas.library.object.Point3D;

/**
 *
 * @author Manuel Dahmen <ibiiztera.it@gmail.com>
 */
public class DarkfortressMap extends SimpleMap
{
    private final Mover mover;

    public DarkfortressMap(Mover m) {
        this.mover = m;
    }

    @Override
    public Point3D getGamePosition() {
        return mover.calcCposition();
    }
}
