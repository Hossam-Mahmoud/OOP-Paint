package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class MyLine extends MyShape {

	private int x1;
	private int x2;
	private int y2;
	private int y1;

	public MyLine(int x1,int y1,int x2,int y2,Color color)
	{
		this.x1 = Zoom.setPositionX(x1);
		this.x2 = Zoom.setPositionX(x2);
		this.y1 = Zoom.setPositionY(y1);
		this.y2 = Zoom.setPositionY(y2);
		this.color = color;
		this.focused = true;
	}
	public MyLine()
	{
		this.focused = true;
	}
	@Override
	public
	void draw(Graphics2D g2) {

		g2.setColor(color);
		
			
		g2.drawLine(Zoom.getPositionX(x1), Zoom.getPositionY(y1), Zoom.getPositionX(x2), Zoom.getPositionY(y2));
		
		if(focused)
			focused(g2);
	}

	@Override
	public
	void reSize(int newX,int newY,int pNo) {

		// TODO Auto-generated method stub
		if(pNo == 1)
		{
			x1 = Zoom.setPositionX(newX);
			
			
			y1 = Zoom.setPositionY(newY);
		}
		else if(pNo == 2)
		{
			x2 = Zoom.setPositionX(newX);
			y2 = Zoom.setPositionY(newY);
		}
	}

	@Override
	public
	void move(int newX, int newY, int startX, int startY) {
		newX=Zoom.setPositionX(newX);
		newY=Zoom.setPositionY(newY);
		startX=Zoom.setPositionX(startX);
		startY=Zoom.setPositionY(startY);
		int dx = newX-startX;
		int dy = newY-startY;
		x1 = x1+dx;
		y1 = y1+dy;
		x2 = x2+dx;
		y2 = y2+dy;
		
	}

	public  boolean checkIntersect(int xp,int yp)
	{	
	
		xp=Zoom.setPositionX(xp);
		yp=Zoom.setPositionY(yp);
		if(x2==x1 && y1 == y2)
			if(xp >=x1-2 && xp<=x1+2 && yp >=y1-2 && yp <=y1+2)
				return true;
			else
				return false;
		else
			{
			int dfx = x2-x1;
			int dfy = y2-y1;
			int c = y1*dfx-x1*dfy;
			int cv = yp*dfx- xp*dfy;
			
			int minC = Math.min(y1*dfy+x1*dfx, y2*dfy+x2*dfx);
			int maxC = Math.max(y1*dfy+x1*dfx, y2*dfy+x2*dfx);
			int perpC = yp*dfy+xp*dfx;

			
			if((cv>=c-300&&cv<=c+300)&& (perpC>=minC&&perpC<=maxC))
				return true;
			}

		
		return false;
	}
	public int getwhichpoint(int xp,int yp)
	{
		xp=Zoom.setPositionX(xp);
		yp=Zoom.setPositionY(yp);
		if((xp>=x1-5&&xp<=x1+5)&&(yp>=y1-5&&yp<=y1+5))
		{
			return 1;
		}
		else if((xp>=x2-5&&xp<=x2+5)&&(yp>=y2-5&&yp<=y2+5))
		{
			return 2;
		}
			
		
	return 0;	
	}
	@Override
	protected
	void focused(Graphics2D g2) {

		// TODO Auto-generated method stub
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(Zoom.getPositionX(x1)-5, Zoom.getPositionY(y1)-5, 10, 10);
		g2.fillRect(Zoom.getPositionX(x2)-5, Zoom.getPositionY(y2)-5, 10, 10);
	}
	@Override
	public
	void setParameters(String parameters) {



		// TODO Auto-generated method stub
		String f[] =parameters.split(" ");
		
		x1 = x2 = Integer.parseInt(f[0]);
		x1=x2=Zoom.setPositionX(x1);
		y1 = y2 = Integer.parseInt(f[1]);
		y1=y2=Zoom.setPositionY(y1);
		this.color = Color.pink;
		this.focused = true;
	}
	
	
	@Override
	public
	void changeColor(Color c) {
		// TODO Auto-generated method stub
		this.color = c;
	}
	
	@Override
	public parameters getParameters() {

		return new parameters(new int[]{x1,x2}, new int[]{y1,y2},color, null);
	}
	
	public void setParameters2(String Parameter) {
		String d[]= Parameter.split(" ");
		x1=Integer.parseInt(d[0]);
		x2=Integer.parseInt(d[1]);
		y1=Integer.parseInt(d[2]);
		y2=Integer.parseInt(d[3]);	
	}
	
	

}
