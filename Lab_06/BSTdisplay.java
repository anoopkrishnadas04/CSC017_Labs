//package CSC017_Lab_06;
import java.awt.*;
import java.awt.Graphics;
import javax.swing.*;

/* Graphical representation of binary trees (regardless of contents),
   Should work for both tree sets and tree maps.

   Designed for the following interfaces, which require a string to be
   returned.  This program will only display strings graphically.
*/
interface DisplayNode { // simplified interface to display value
    boolean isempty();
    int height();  // O(1)
    // ... 
}//interface to be implemented by both nil and vertex classes

interface Nonempty
{
    DisplayNode left();
    DisplayNode right();
    String get_item(); // get item to display, must be a string
} // interface to be implemented by the vertex class

//   See "main" at end of file for sample usage

public class BSTdisplay extends JFrame
{
    int XDIM, YDIM;
    Graphics display;
    DisplayNode currenttree;

    @Override
    public void paint(Graphics g) {drawtree(currenttree);} // override method

    // constructor sets window dimensions
    public BSTdisplay(int x, int y)
    {
	XDIM = x;  YDIM = y;
	this.setBounds(0,0,XDIM,YDIM);
	this.setVisible(true); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	display = this.getGraphics();
	// draw static background as a black rectangle
	display.setColor(Color.black);
	display.fillRect(0,0,x,y);
        display.setColor(Color.red);
	try{Thread.sleep(500);} catch(Exception e) {} // Synch with system
    }  // drawingwindow
    public BSTdisplay(int x, int y, int yoff) {
        this(x,y);
        this.yoff = yoff;
    }// constructor that sets yoff (slight diffs in graphical systems)


    // internal vars used by drawtree routines:
    int bheight = 50; // default branch height
    int yoff = 30;  // static y-offset

    // l is level, lb,rb are the bounds (position of left and right child)

    public void drawtree(DisplayNode t)
    {
        if (t==null) return;
	currenttree = t;
	int d = currenttree.height();
	display.setColor(Color.white);
	display.fillRect(0,0,XDIM,YDIM);  // clear background
	if (d<1) return;
	bheight = (YDIM/d);
        dispatch(currenttree,0,0,XDIM);
    }

    void dispatch(DisplayNode node, int l, int lb, int rb)  {
	if (!node.isempty()) draw((Nonempty)node,l,lb,rb);
    }//dispatch

    void draw(Nonempty N, int l, int lb, int rb)
    {
	//try{Thread.sleep(10);} catch(Exception e) {} // slow down
        display.setColor(Color.green);
	display.fillOval(((lb+rb)/2)-10,yoff+(l*bheight),20,20);
	display.setColor(Color.red);
	display.drawString(N.get_item(),((lb+rb)/2)-5,yoff+15+(l*bheight));
	display.setColor(Color.blue); // draw branches
        if (!N.left().isempty())
	    {
   	       display.drawLine((lb+rb)/2,yoff+10+(l*bheight),
			((3*lb+rb)/4),yoff+(l*bheight+bheight));
	       dispatch(N.left(),l+1,lb,(lb+rb)/2);
	    }
        if (!N.right().isempty())
	    {
               display.drawLine((lb+rb)/2,yoff+10+(l*bheight),
			((3*rb+lb)/4),yoff+(l*bheight+bheight));
	       dispatch(N.right(),l+1,(lb+rb)/2,rb);
	    }
    } // draw

    public void delay(int ms) {
        try { Thread.sleep(ms); }
        catch (Exception e) {}
    }
    
    public void draw_caption_bottom(String caption) {
        display.drawString(caption,20,YDIM-20);
    }
    public void draw_caption_top(String caption) {
        display.drawString(caption,20,yoff+20);
    }
    public void draw_caption_at(String caption, int x, int y) {
        display.drawString(caption,x%XDIM, yoff + (y%YDIM));
    }

} // BSTdisplay
