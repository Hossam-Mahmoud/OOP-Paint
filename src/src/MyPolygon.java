package src;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;

public class MyPolygon extends MyShape {

	protected int []x;
	protected int []y;

	
	public MyPolygon (int []x,int[]y,Color f,Color s)

	{
		this.x=Zoom.setPositionX(x);
		
		this.y=Zoom.setPositionY(y);
		this.color=f;
		this.focused = true;
			
	}
	public MyPolygon()
	{
		
		this.focused = true;
	}
	@Override
	public
	void move(int newX, int newY, int startX, int startY) {
		// TODO Auto-generated method stub
		newX=Zoom.setPositionX(newX);
		startX=Zoom.setPositionX(startX);
		newY=Zoom.setPositionY(newY);
		startY=Zoom.setPositionY(startY);
		int dx = newX-startX;
		int dy = newY-startY;
		for(int i = 0 ;i<x.length;i++)
		{
			x[i]=x[i]+dx;
			y[i]=y[i]+dy;
		}
	}

	@Override
	public
	void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.setColor(color);
		g2.fillPolygon(Zoom.getPositionX(x), Zoom.getPositionY(y), x.length);
		g2.setColor(Color.BLACK);
		g2.drawPolygon(Zoom.getPositionX(x), Zoom.getPositionY(y), x.length);
//		focused??
		if(focused)
		focused(g2);
	
	}
	protected void focused (Graphics2D g2)

	{
		g2.setColor(Color.DARK_GRAY);
			for(int i  = 0;i<x.length;i++)
				g2.fillRect(Zoom.getPositionX(x[i])-5, Zoom.getPositionY(y[i])-5, 10, 10);
	}

	@Override
	public
	void reSize(int newX, int newY, int pNo) {
		// TODO Auto-generated method stub
		x[pNo-1]=Zoom.setPositionX(newX);
		y[pNo-1]=Zoom.setPositionY(newY);
	}


	@Override
	public int getwhichpoint(int xp, int yp) {
		// TODO Auto-generated method stub
		xp=Zoom.setPositionX(xp);
		yp=Zoom.setPositionY(yp);
		for(int i = 0;i<this.x.length;i++)
		{
			int xh = x[i];
			int yh = y[i];
			if((xp>=xh-5 && xp<=xh+5) && (yp>=yh-5&&yp<=yh+5))
			{
				return i+1;
			}
		}
		return 0;
	}
	
	private int crossprod(int xp,int yp,int i)
	{
		int a = x[i]-xp;
		int b = y[i]-yp;
		int c = x[i+1]-xp;
		int d = y[i+1]-yp;
		int prod = a*d-b*c;
		return prod;
	}
	
	private double angle(int xp,int yp,int i)
	{
		int a = x[i]-xp;
		int b = y[i]-yp;
		int c = x[i+1]-xp;
		int d = y[i+1]-yp;
		double f = Math.sqrt((Math.pow(a, 2)+Math.pow(b, 2))*(Math.pow(c, 2)+Math.pow(d, 2)));
		double l =a*c+b*d;
		double h = l/f;
		double r = Math.acos(h);
		
		return r;
	}
	
	@Override
	public boolean checkIntersect(int xp, int yp) {

		// TODO Auto-generated method stub
		xp=Zoom.setPositionX(xp);
		yp=Zoom.setPositionY(yp);
		
		double posx = 0;
		double posy = 0;
		double posx1 = 0;
		double posy1 = 0;
		for(int i = 0;i<x.length;i++)
		{
			posx += x[i];
			posy += y[i];
		}
			
		posx = posx/x.length;
		posy = posy/y.length;
		
		posx =posx-xp ;
		posy =posy -yp;
		if(posx>=-5 && posx<=5 && posy>=-5 && posy<=5)
			return true;
			int i;
		for( i = 0; i<x.length;i++)
		{
			if(i!= 0)
			{
				if(posx!=x[i] && posy!=y[i])
				{
					posx1 = x[i];
					posy1 = y[i];
					break;
				}
			}
			else
			{
				posx = x[i];
				posy = y[i];
			}
		}
		boolean flag = true;
		for(int k = i+1;k<x.length;k++)
		{
			if(!((posx == x[k] && posy == y[k])|| (posx1 == x[k] && posy1 == y[k])))
			{
				flag = false;
				break;
			}
		}
		if(flag)
		{
			MyLine d = new MyLine(Zoom.getPositionX((int)posx),Zoom.getPositionY((int)posy),Zoom.getPositionX((int)posx1),Zoom.getPositionY((int)posy1),null);
			return d.checkIntersect(Zoom.getPositionX(xp),Zoom.getPositionY(yp));
		}
		
			double sum =0;
		for(int j = 0; j<x.length-1;j++)
			if(crossprod(xp, yp, j)<0)
				sum-=angle(xp, yp, j);
			else
				sum+=angle(xp, yp, j);
			
		
		return(Math.abs(sum-2*Math.PI)<3||Math.abs(sum+2*Math.PI)<3);
		
		
	}
	
	@Override
	public
	void setParameters(String parameters) {
		// TODO Auto-generated method stub
		String d[] =parameters.split(" ");
		x = new int [Integer.parseInt(d[0])];
		y = new int [Integer.parseInt(d[0])];
		Arrays.fill(x, Integer.parseInt(d[1]));
		Arrays.fill(y,Integer.parseInt(d[2]));
		
		for(int i=0;i<x.length;i++)
		{
			x[i]=Zoom.setPositionX(x[i]);
			y[i]=Zoom.setPositionY(y[i]);
			
		}
	
	}
	@Override
	public
	void changeColor(Color c) {
		// TODO Auto-generated method stub
		color =c;
	}
	@Override
	public parameters getParameters() {
		parameters d = new  parameters(x, y, color, Color.BLACK);
		return d ;
	}
	public void setParameters2(String Parameter) {
		String d[]= Parameter.split(" ");
		int i = d.length;
		x=new int[i/2];
		y=new int[i/2];

		i=i/2;

		for(int j=0;j<i;j++)
		{
			x[j]=Integer.parseInt(d[j]);
			y[j]=Integer.parseInt(d[j+i]);

		}


	}

}
