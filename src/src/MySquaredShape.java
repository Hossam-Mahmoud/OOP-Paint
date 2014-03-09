package src;

import java.awt.Color;

public abstract class MySquaredShape extends MyRectangularShape {
	
	public MySquaredShape(int x1, int y1, int x2, int y2, Color f, Color s) {
		super(x1, y1, x2, y2, f, s);
		// TODO Auto-generated constructor stub
	}
	public MySquaredShape()
	{
		super();
	}

	public void reSize (int newX, int newY ,int pNo)
	{
		int dx;
		int dy;
		newX=Zoom.setPositionX(newX);
		newY=Zoom.setPositionY(newY);
		 if(pNo == 2)
		 {
			dx = newX-x[1];
			dy = newY-y[1];
			
			x[1] = newX;
			y[1] = newY;
			
			
			
			x[2]+=dx;
			y[2]+=dx;
			y[3]+=dx;
			x[3]+=dy;
			x[0]+=dy;
			y[0]+=dy;
		}
		else if(pNo == 4)
		{
			dx = newX-x[3];
			dy = newY-y[3];
			
			x[3] = newX;
			y[3] = newY;
			
			
			x[1]+=dy;
			y[1]+=dx;
			y[2]+=dy;
			x[2]+=dy;
			x[0]+=dx;
			y[0]+=dx;
		}
		else if(pNo == 3)
		{
			dx = newX-x[2];
			dy = newY-y[2];
			
			x[2] = newX;
			y[2] = newY;
			
			x[1]+=dx;
			y[1]-=dx;
			y[3]+=dy;
			x[3]-=dy;
			x[0]-=dy;
			y[0]-=dx;
		}
		else if(pNo == 1)
		{
			dx = newX-x[0];
			dy = newY-y[0];
			
			x[0] = newX;
			y[0] = newY;
			
			x[1]-=dy;
			y[1]+=dy;
			y[3]-=dx;
			x[3]+=dx;
			x[2]-=dy;
			y[2]-=dx;
			
		}
	}
}
