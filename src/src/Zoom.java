package src;


public class Zoom {
	public static int screenX=683 , screenY=368,zoom[]=new int[]{1,2,3,4},zoomDegree=0;

public static int getValue(int x)

{
	return x*(1+zoom[zoomDegree]);
	
}

public static int getPositionX(int x)
{
	int dx=screenX-x;
int x0=x-dx*zoom[zoomDegree];	
	
return x0;
}

public static int getPositionY(int y)
{
	int dy=screenY-y;
int y0=y-dy*zoom[zoomDegree];	
	
return y0;
}

public static int setPositionX(int x)
{
int x0=screenX*zoom[zoomDegree]+x;
x0/=(zoom[zoomDegree]+1);

return x0;

}

public static int setPositionY(int y)
{
int y0=screenY*zoom[zoomDegree]+y;
y0/=(zoom[zoomDegree]+1);


return y0;
}


public static void main(String[] args) {
	int x =25;
	zoomDegree++;
}



public static int[] getPositionY(int[] yy) {
	int y[]= new int[yy.length];
	for(int i =0 ;i<yy.length;i++)
	{
		y[i]=getPositionY(yy[i]);
		
	}
	return y;
}
public static int[] getPositionX(int[] yy) {
	int y[]= new int[yy.length];
	
	for(int i =0 ;i<yy.length;i++)
	{
		y[i]=getPositionX(yy[i]);
		
	}
	return y;
}


public static int[] setPositionY(int[] yy) {
	int y[]= new int[yy.length];
	for(int i =0 ;i<yy.length;i++)
	{
		y[i]=setPositionY(yy[i]);
		
	}
	return y;
}
public static int[]setPositionX(int[] yy) {
	int y[]= new int[yy.length];
	
	for(int i =0 ;i<yy.length;i++)
	{
		y[i]=setPositionX(yy[i]);
		
	}
	return y;
}


}
