package CSC017_Lab_02;

import java.util.ArrayList;
//import java.util.Optional;
import java.awt.event.*;

public class maze extends mazebase
{
    // default constructor suffices and is equivalent to
    // public maze() { super(); }

    protected static final int[] Dx = {0, 1, 0, -1};
    protected static final int[] Dy = {-1, 0, 1, 0};
    
    public boolean withinBounds(int y, int x, int direction, int magnitude, boolean checkForEmpty){
        if(!(direction >= 0 && direction < 4)) return false;
        int y2 = y + (magnitude * Dy[direction]);
        int x2 = x + (magnitude * Dx[direction]);
        if (checkForEmpty) return ((y2 >= 0) && (y2 < mheight) && (x2 >= 0) && (x2 < mwidth) && (M[y2][x2] == 0));
        return ((y2 >= 0) && (y2 < mheight) && (x2 >= 0) && (x2 < mwidth));
    }

    public ArrayList<Integer> getValidDir(int y, int x, int magnitude){
        ArrayList<Integer> validDirArr = new ArrayList<Integer>();
        for(int i = 0; i < 4; i++){
            if(withinBounds(y, x, i, magnitude, true) /*&&  M[y + (2 * Dy[i])][x + (2 * Dx[i])] == 0)*/){
                validDirArr.add(i);
            }
        }
        return validDirArr;
    }

 @Override
    public void digout(int y, int x)   // modify this function
    {
        M[y][x] = 1;
        drawblock(y,x);
        nextframe(10);

        int ny, nx;

        ArrayList<Integer> validDirArr = getValidDir(y, x, 2);

        //while(((y+2>0 && y+2<mheight) && M[y+2][x] == 0) || ((y-2>0 && y-2<mheight) && M[y-2][x] == 0) || ((x+2>0 && x+2<mheight) && M[y][x+2] == 0) || ((x-2>0 && x-2<mheight) && M[y][x-2] == 0)){
        while(withinBounds(y, x, 0, 2, true) || withinBounds(y, x, 1, 2, true) || withinBounds(y, x, 2, 2, true) || withinBounds(y, x, 3, 2, true) ){
            validDirArr = getValidDir(y,x,2);
            int chosenDir = (int)(Math.random() * (validDirArr.size()));
            ny = y + 2 * Dy[validDirArr.get(chosenDir)];
            nx = x + 2 * Dx[validDirArr.get(chosenDir)];
            
            int iby = y + Dy[validDirArr.get(chosenDir)];
            int ibx = x + Dx[validDirArr.get(chosenDir)];

            M[iby][ibx] = 1;
            drawblock(iby, ibx);
            
            //validDirArr.remove(chosenDir);
            digout(ny, nx);
        }
    }//digout()

    @Override
    public void solve(){
        //showvalue = true;
        solveInc();
    }//solve()

    
    public void solveInc(){
        int targety = mheight-2, targetx = mwidth-1;
        M[targety][targetx] = 1; drawblock(targety, targetx); nextframe(10);

        int x = 1, y = 1;
        
        drawdot(y, x); nextframe(10);
        
        while(y != targety || x != targetx){
            M[y][x]++;
            int direction = getSmallestDir(y, x, true); //drawblock(y, x);
            y += Dy[direction]; x += Dx[direction]; //drawdot(y, x); nextframe(10);
        }
    }
    public int getSmallestDir(int y, int x, boolean includeOne){
        int smallestDir = -1;
        int smallestVal = Integer.MAX_VALUE;
        for(int i = 0; i < 4; i++){
            int newy = y + Dy[i], newx = x + Dx[i];
            if(withinBounds(y, x, i, 1, false) && M[newy][newx] < smallestVal && M[newy][newx] != 0 && (includeOne || M[newy][newx] != 1)){
                smallestVal = M[newy][newx];
                smallestDir = i;
            }
        }
        return smallestDir;
    }

    /*
    public void solveStack(){

    }
    */

    public void trace(){
       traceInc();
    }//trace()

    public int MAXCOUNT;
    public void traceInc(){
        //showvalue = true;
        int y = mheight-2, x = mwidth-1;
        int targety = 1, targetx = 1;
        //drawdot(y,x); nextframe(10);
        int counter = 0;
        while(y != targety || x != targetx){
            M[y][x] = Integer.MAX_VALUE;
            int dir = getSmallestDir(y, x, false);
            y += Dy[dir]; x += Dx[dir]; //drawdot(y, x); nextframe(40);
            counter++;
        }
        //drawgif(y,x); nextframe(40);
        MAXCOUNT = counter;
    }

    @Override
    public void play(){
        drawblock(mheight-2, mheight-1);
    }
    public int currX = 1;
    public int currY = 1;
    
    //public int numValidKeyPresses = 0;
    public int numMovements = 0;
    @Override
    public void keyPressed(KeyEvent e){
        int keyVal = e.getKeyCode();
        int dir = -1;
        String message = "";
        switch(keyVal){
            case 37:
                dir = 3; break;
            case 38:
                dir = 0; break;
            case 39:
                dir = 1; break;
            case 40:
                dir = 2; break;
            default:
                System.out.println("Invalid Key Press");
        }
        if(dir != -1){
            //System.out.println("Input Valid");
            //numValidKeyPresses++;
            if(!withinBounds(currY, currX, dir, 1, false)){
                //System.out.println("Out of Bounds");
            }
            else { 
                int y2 = currY + Dy[dir], x2 = currX + Dx[dir]; //System.out.println("Within Bounds");
                //System.out.println(M[y2][x2]);
                
                if(M[y2][x2] == 0){
                    //System.out.println("Thats a wall");
                    //drawMessage("Thats a Wall. :( "); nextframe(10);
                    message += "That's a Wall :(";
                } else {
                    numMovements++; message += "Movements: " + numMovements + " -- ";
                    if(M[y2][x2] == Integer.MAX_VALUE) message += "This is the right way.";
                    else message += "You are on the incorrect path.";
                    drawblock(currY, currX); drawgif(y2, x2); nextframe(10);
                    currY = y2; currX = x2;
                }
                
            }
        }
        if(currY == mheight - 2 && currX == mwidth - 1){
            message = "Congratulation, You Win! -- Total Movements: " + numMovements + " -- Extra Movements: " + (numMovements - MAXCOUNT);
            System.out.println(message);
        }

        drawMessage(message); nextframe(10);
    }//keyPressed()

    public static void main(String[] av)
    {
	    new maze(); // constructor of superclass will initiate everything
    }

    // other hints:  override customize to change maze parameters:
    @Override
    public void customize()
    {
	    // ... can change mwidth, mheight, bw,bh, colors here
        mwidth = 21; mheight = 21;
    }
}//maze subclass