package src;
import java.awt.Color;
import java.awt.Graphics2D;


public abstract class MyShape {

protected  Color color ;
protected boolean focused;


public abstract void move(int newX,int newY,int startX,int startY);
public abstract void draw(Graphics2D g2);
public abstract void reSize(int newX,int newY,int pNo);
public abstract parameters getParameters();

public abstract void changeColor(Color c);
public Color getColor(){return color;}
public abstract int getwhichpoint(int xp,int yp);
public boolean checkpoints(int xp,int yp)
{
 return(getwhichpoint(xp, yp) != 0);
}
public abstract void setParameters(String parameters);
public abstract void setParameters2(String Parameter);
protected abstract void focused(Graphics2D g2);
public abstract  boolean checkIntersect(int xp,int yp);
public void setfocused(boolean b)
	{
		focused = b;
	}	
	
}
