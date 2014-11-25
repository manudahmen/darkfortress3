package info.emptycanvas.apps.darkfortress;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DFKeyListener implements KeyListener {
    private Mover mover;
    private boolean release_up = true;
    private boolean release_down = true;
    private boolean release_left = true;
    private boolean release_right = true;
	

	public DFKeyListener(Mover mover)
	{
		this.mover = mover;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP
				&& mover.state() == mover.STATE_GAME_IN_PROGRESS())

		{
			release_up = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN
				&& mover.state() == mover.STATE_GAME_IN_PROGRESS())

		{
			release_down = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT
				&& mover.state() == mover.STATE_GAME_IN_PROGRESS())

		{
			release_left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				&& mover.state() == mover.STATE_GAME_IN_PROGRESS())

		{
			release_right = false;
		}
                keeprolling();
		mover.testCollision();
	}

	@Override
	public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP
				&& mover.state() == mover.STATE_GAME_IN_PROGRESS())

		{
			release_up = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN
				&& mover.state() == mover.STATE_GAME_IN_PROGRESS())

		{
			release_down = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT
				&& mover.state() == mover.STATE_GAME_IN_PROGRESS())

		{
			release_left = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				&& mover.state() == mover.STATE_GAME_IN_PROGRESS())

		{
			release_right = true;
		}

		
	}

    private void keeprolling() {
        if(!release_up)
        {
            mover.acc();
        }
        if(!release_down)
        {
            mover.dec();
        }
        if(!release_left)
        {
            mover.rotationGauche();
        }
        if(!release_right)
        {
            mover.rotationDroite();
        }
    }


}
