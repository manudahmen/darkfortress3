package info.emptycanvas.apps.darkfortress;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JApplet;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

import info.emptycanvas.library.object.Point3D;
import info.emptycanvas.library.object.Representable;
import info.emptycanvas.library.object.SegmentDroite;
import info.emptycanvas.library.object.TRI;
import info.emptycanvas.library.tribase.TRIObjetGenerateur;
import info.emptycanvas.library.object.RepresentableConteneur;
import com.jogamp.opengl.util.awt.TextRenderer;
import info.emptycanvas.library.object.Cube;
import info.emptycanvas.library.object.TRIObject;
import java.awt.Dimension;
import java.awt.Font;

public class JoglDrawer implements Drawer, GLEventListener {

    private GLU glu = new GLU();
    private Object component;
    private Mover mover;
    private Terrain terrain;
    private Bonus ennemi;
    private TextRenderer renderer;
    private Vaisseau vaisseau;

    public JoglDrawer(DarkFortressGUI darkFortressGUI) {
        this.component = darkFortressGUI;

        GLProfile profile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setDoubleBuffered(true);
		// The canvas is the widget that's drawn in the JFrame

        GLCanvas glcanvas = new GLCanvas(capabilities);

        glcanvas.addGLEventListener(this);

        glcanvas.addKeyListener(darkFortressGUI);

        glcanvas.setSize(640, 480);

        if (component instanceof JFrame) {
            ((JFrame) component).add(glcanvas);
        }
    }

    public void color(GL2 gl, Color c) {
        gl.glColor3f(
                c.getRed() / 255f,
                c.getGreen() / 255f, c.getBlue() / 255f);
    }

    public void draw(SegmentDroite segd, GLU glu, GL2 gl) {
        gl.glBegin(GL2.GL_LINES);
        color(gl, segd.getC());
        gl.glVertex3f((float) segd.getOrigine().get(0), (float) segd
                .getOrigine().get(1), (float) segd.getOrigine().get(2));
        gl.glVertex3f((float) segd.getExtremite().get(0), (float) segd
                .getExtremite().get(1), (float) segd.getExtremite().get(2));
        gl.glEnd();
		//System.out.print("SD");
        // System.out.println("L");
    }
    /*
     public void draw(Representable rep, GLU glu, GL2 gl)
     {
     throw new UnsupportedOperationException("Objet non supporte par "+getClass().getCanonicalName());
     }*/

    public void draw(TRI tri, GLU glu, GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLES);
        color(gl, new Color(tri.texture().getColorAt(0.5, 0.5)));
        for (int t = 0; t < 3; t++) {
            gl.glVertex3f((float) tri.getSommet()[t].get(0),
                    (float) tri.getSommet()[t].get(1),
                    (float) tri.getSommet()[t].get(2));
        }
        gl.glEnd();
		//System.out.print("T"+tri.getCouleur());

        // System.out.println("T");
    }

    public void draw(RepresentableConteneur rc, GLU glu, GL2 gl) {
        Iterator<Representable> it = rc.iterator();
        while (it.hasNext()) {
            Representable r = it.next();

            if (r instanceof TRI) {
                draw((TRI) r, glu, gl);
            } else if (r instanceof SegmentDroite) {
                draw((SegmentDroite) r, glu, gl);
            } else if (r instanceof TRIObjetGenerateur) {
                TRIObjetGenerateur s = (TRIObjetGenerateur) r;
                for (int i = 0; i < s.getMaxX(); i++) {
                    for (int j = 0; j < s.getMaxY(); j++) {
                        TRI[] tris = new TRI[2];
                        Point3D INFINI = new Point3D(0, 0, 100000);
                        tris[0] = new TRI(INFINI, INFINI, INFINI);
                        tris[1] = new TRI(INFINI, INFINI, INFINI);
                        s.getTris(i, j, tris);
                        draw(tris[0], glu, gl);
                        draw(tris[1], glu, gl);
                    }
                }
            }

        }

		//System.out.println(logic.position.toString());
		//System.out.println(logic.direction.toString());
    }

    public void draw(TRIObject generate, GLU glu, GL2 gl) {
        Iterator<TRI> it = generate.iterator();
        while (it.hasNext()) {
            TRI t = it.next();
            draw(t, glu, gl);
        }
    }

    public void draw(Cube c, GLU glu, GL2 gl) {
        TRIObject generate = c.generate();
        draw(generate, glu, gl);
    }

    public void draw(String text, Color textColor, GLU glu, GL2 gl) {
        Dimension d = new Dimension(1, 1);
        if (component instanceof JFrame) {
            d = ((JFrame) component).getSize();
        }
        renderer.beginRendering((int) d.getWidth(), (int) d.getHeight());
        renderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        renderer.draw(text, 10, 10);
        renderer.endRendering();
    }
    /*public void drawCard(Card c, GLU glu, GL2 gl)
     {
     //Buffer buffer;
     Dimension d = new Dimension(1,1);
     if(component instanceof JFrame)
     {
     d = ((JFrame)component).getSize();
     }
     //gl.glDrawPixels(0, 0, d.getWidth(), d.getHeight(),  buffer);
            
     }*/

    @Override
    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();

        // Change to projection matrix.
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

                //glu.gluPerspective(60, 1.33, 0.001, 1.0);
        gl.glLoadIdentity();

        Point3D pos = mover.calcCposition();
        Point3D dir = mover.calcDirection();
        Point3D del = dir.moins(pos);
        glu.gluLookAt(pos.get(0), pos.get(1),
                pos.get(2), dir
                .get(0), dir.get(1),
                dir.get(2),
                del.prodVect(Point3D.Y.prodVect(del))
                .norme1().get(0),
                del.prodVect(Point3D.Y.prodVect(del))
                .norme1().get(1),
                del.prodVect(Point3D.Y.prodVect(del))
                .norme1().get(2));

        draw(ennemi, glu, gl);
        draw(terrain, glu, gl);
        draw(vaisseau.getObject(), glu, gl);
        draw("Score :  " + mover.score(), Color.WHITE, glu, gl);

        Graphics g = null;
        if (component instanceof JApplet) {
            g = ((JApplet) component).getGraphics();
        }
        if (component instanceof JFrame) {
            g = ((JFrame) component).getGraphics();
        }

        if (g == null) {
            throw new NullPointerException("Problem initialising JFrame graphics");
        }
    }

    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged,
            boolean deviceChanged) {
        System.out.println("displayChanged called");
    }

    @Override
    public void init(GLAutoDrawable gLDrawable) {
        /*
         * System.out.println("init() called"); GL2 gl =
         * gLDrawable.getGL().getGL2(); gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
         * gl.glShadeModel(GL2.GL_FLAT);
         */

        GL2 gl = gLDrawable.getGL().getGL2();
        gLDrawable.setGL(new DebugGL2(gl));

        // Global settings.
        gl.glEnable(GL2.GL_DEPTH_TEST);

        /*
         gl.glDepthFunc(GL2.GL_LEQUAL);
         gl.glShadeModel(GL2.GL_SMOOTH);
         gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		
         */
        gl.glClearColor(0f, 0f, 0f, 1f);

        // Start animator (which should be a field).
        FPSAnimator animator = new FPSAnimator(gLDrawable, 60);
        animator.start();
        renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 36));
    }

    @Override
    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width,
            int height) {
        System.out.println("reshape() called: x = " + x + ", y = " + y
                + ", width = " + width + ", height = " + height);
        final GL2 gl = gLDrawable.getGL().getGL2();

        if (height <= 0) // avoid a divide by zero error!
        {
            height = 1;
        }

        final float h = (float) width / (float) height;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(60f, h, 0.001f, 2f);

        gl.glMatrixMode(GL2.GL_MODELVIEW);

    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        System.out.println("dispose() called");
    }

    @Override
    public void setLogic(Mover m) {
        this.mover = m;

        mover.ennemi(ennemi);
        vaisseau = new Vaisseau(mover);
        terrain = ((GameMover)mover).getTerrain();
        ennemi = new Bonus(terrain);
    }

}
