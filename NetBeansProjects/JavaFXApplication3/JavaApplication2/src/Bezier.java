import java.applet.*;
import java.awt.*;
import java.awt.event.*;
 
public class Bezier extends Applet implements MouseListener, MouseMotionListener, Runnable
{
 
    double[] [] point = new double [2] [2];
    int dragged;
    Thread t;
    Graphics b;
    Image offscreen;
    int mode;
 
    public void init ()
    {
        mode = 3;
        dragged = -1;
        for (int i = 0 ; i < point.length ; i++)
        {
            point [i] [0] = (int) (Math.random () * 600);
            point [i] [1] = (int) (Math.random () * 600);
        }
        setSize(800,800);
        setFocusable (true);
        addMouseListener (this);
        addMouseMotionListener (this);
        t = new Thread (this);
        t.start ();
        
        offscreen = createImage (800, 800);
        b = offscreen.getGraphics ();
 
    } // init method
 
 
    public void update (Graphics g)
    {
        paint (g);
    }
 
 
    public void run ()
    {
        while (true)
        {
            repaint ();
            try
            {
                t.sleep (10);
            }
            catch (InterruptedException e)
            {
            }
        }
    }
 
 
    public void paint (Graphics g)
    {
        b.setColor (Color.white); //clears the buffer
        b.fillRect (0, 0, 800, 800);
        b.setColor (Color.red);
        b.fillRect (0, 0, 100, 30);
        b.setColor (Color.black);
        switch (mode)
        {
            case 1:
                b.drawString ("Adding nodes", 10, 20);
                break;
            case 2:
                b.drawString ("Deleting nodes", 10, 20);
                break;
            case 3:
                b.drawString ("Moving nodes", 10, 20);
                break;
        }
        for (int i = 0 ; i < point.length ; i++)
        {
            b.fillOval ((int) (point [i] [0] - 10), (int) (point [i] [1] - 10), 20, 20);
        }
        b.setColor (Color.blue);
        for (int i = 0 ; i < point.length - 1 ; i++)
        {
           // b.drawLine ((int) (point [i] [0]), (int) (point [i] [1]), (int) (point [i + 1] [0]), (int) (point [i + 1] [1]));
        }
        double[] current = new double [2];
        b.setColor (Color.green);
        for (double t = 0 ; t <= 1 ; t += 0.0005)
        {
            current = Bez (t, point);
            b.fillOval ((int) current [0] - 2, (int) current [1] - 2, 4, 4);
        }
        g.drawImage (offscreen, 0, 0, this);
    } // paint method
 
 
    public double[] Bez (double t, double[] [] point)
    {
        if (point.length == 1 /*|| t < 0 || t > 1*/)
        {
            double[] New = new double [2];
            New [0] = point [0] [0];
            New [1] = point [0] [1];
            return New;
        }
        double[] [] New = new double [point.length - 1] [2];
        for (int i = 0 ; i < New.length ; i++)
        {
            New [i] [0] = ((1 - t) * point [i] [0]) + (t * point [i + 1] [0]);
            New [i] [1] = ((1 - t) * point [i] [1]) + (t * point [i + 1] [1]);
        }
        return Bez (t, New);
    }
 
 
    public void mouseClicked (MouseEvent e)
    {
    }
 
 
    public void mouseMoved (MouseEvent e)
    {
    }
 
 
    public void mouseReleased (MouseEvent e)
    {
        dragged = -1;
    }
 
 
    public void mouseEntered (MouseEvent e)
    {
    }
 
 
    public void mouseExited (MouseEvent e)
    {
    }
 
 
    public void mousePressed (MouseEvent e)
    {
        if (e.getX () >= 0 && e.getX () <= 100 && e.getY () >= 0 && e.getY () <= 30)
        { // red button was clicked
            switch (mode)
            {
                case 1:
                    mode = 3;
                    break; 
                case 3:
                    mode = 1;
                    break;
            } // switch (mode)
        } // if (e.getX () >= 0 && e.getX () <= 100 && e.getY () >= 0 && e.getY () <= 30)
        // mode: 1=add; 2=delete; 3=move
        else if (mode == 1 && point.length <= 3)
        {     
            dragged = -1;
            double[] [] temp = point;
            point = new double [temp.length + 1] [2];
            for (int i = 0 ; i < temp.length ; i++)
            {
                point [i] [0] = temp [i] [0];
                point [i] [1] = temp [i] [1];
            } // for (int i = 0 ; i < temp.length ; i++)
            point [point.length - 1] [0] = e.getX ();
            point [point.length - 1] [1] = e.getY ();
        } // if (mode == 1)
        else if (mode == 2)
        {
            dragged = -1;
            for (int i = 0 ; i < point.length ; i++)
            {
                if (Dist ((double) e.getX (), (double) e.getY (), point [i] [0], point [i] [1]) <= 10)
                { // delete this index
                    for (int j = i ; j < point.length - 1 ; j++)
                    {
                        point [j] [0] = point [j + 1] [0];
                        point [j] [1] = point [j + 1] [1];
 
                    } // for (int j = i ; j < point.length ; j++)
                    double[] [] temp = point;
                    point = new double [temp.length - 1] [2];
                    for (int j = 0 ; j < point.length ; j++)
                    {
                        point [j] [0] = temp [j] [0];
                        point [j] [1] = temp [j] [1];
 
                    } // for (int j = 0 ; j < point.length ; j++)
                } // if (Dist ((double) e.getX (), (double) e.getY (), point [i] [0], point [i] [1]) <= 10)
            } // for (int i = 0 ; i <= point.length ; i++)
        } // else if (mode == 2)
        else if (mode == 3)
        {
            for (int i = 0 ; i <= point.length-1 ; i++)
            {
                try
                {
                    if (Dist ((double) e.getX (), (double) e.getY (), point [i] [0], point [i] [1]) <= 10)
                    {
                        dragged = i;
                    } // if (Dist ((double) e.getX (), (double) e.getY (), point [i] [0], point [i] [1]) <= 10)
                } // try
                catch (Exception ex)
                {
                } // catch (Exception ex)
            } // for (int i = 0 ; i <= point.length ; i++)
        }
    }
 
 
    public double Dist (double x1, double y1, double x2, double y2)
    {
        return Math.pow (Math.pow (x2 - x1, 2) + Math.pow (y2 - y1, 2), 0.5);
    }
 
 
    public void mouseDragged (MouseEvent e)
    {
        if (dragged != -1)
        {
            point [dragged] [0] = e.getX ();
            point [dragged] [1] = e.getY ();
        }
    }
}