package src;
import java.awt.Color;


abstract class MyRectangularShape extends MyPolygon {

	public MyRectangularShape (int x1,int y1 ,int x2 ,int y2,Color f,Color s)
	{
		super(new int[] {x1,x2,x2,x1},new int[] {y1,y1,y2,y2},f,s);
	}
	public MyRectangularShape()
	{
		super();
	}
	public void reSize(int newX ,int newY,int pNo)
	{
		int dx;
		int dy;
		newX=Zoom.setPositionX(newX);
		newY=Zoom.setPositionY(newY);
		if(pNo == 3)
		{
			dx = newX-x[2];
			dy = newY-y[2];
			
			x[2] = newX;
			y[2] = newY;
			
			x[1]=x[1]+dx;
			y[3]=y[3]+dy;
			
		}
		else if(pNo == 1)
		{
			dx = newX-x[0];
			dy = newY-y[0];
			
			x[0] = newX;
			y[0] = newY;
			
			y[1]=y[1]+dy;
			x[3]=x[3]+dx;
		}
		else if(pNo == 2)
		{
			dx = newX-x[1];
			dy = newY-y[1];
			
			x[1] = newX;
			y[1] = newY;
			
			y[0]=y[0]+dy;
			x[2]=x[2]+dx;
		}
		else if(pNo == 4)
		{
			dx = newX-x[3];
			dy = newY-y[3];
			
			x[3] = newX;
			y[3] = newY;
			
			x[0]=x[0]+dx;
			y[2]=y[2]+dy;
		}
	}
}
