package src;

import java.awt.Color;
import java.awt.Graphics2D;

public class MyCircle extends MySquaredShape {

	public MyCircle()
	{
		super();
	}
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
//		
		int minx = Integer.MAX_VALUE;
		int miny = Integer.MAX_VALUE;
		for(int i = 0;i<4;i++)
		{
			minx= Math.min(minx, x[i]);
			miny= Math.min(miny, y[i]);
		}
		g2.setColor(color);
		
		g2.fillOval(Zoom.getPositionX(minx),Zoom.getPositionY(miny),Zoom.getValue(Math.abs(x[1]-x[0])),Zoom.getValue(Math.abs(y[3]-y[0])));
		g2.setColor(Color.BLACK);
		g2.drawOval(Zoom.getPositionX(minx),Zoom.getPositionY(miny),Zoom.getValue(Math.abs(x[1]-x[0])),Zoom.getValue(Math.abs(y[3]-y[0])));
		
		if(focused)
		{
			this.focused(g2);
			g2.drawPolygon(Zoom.getPositionX(x), Zoom.getPositionY(y), x.length);
		}
	}
}
