
package src;

import java.awt.Color;

/*this class is the class that is used to store the new and old parameters of 
 * object that extends class shape in the undo & re-do stack 
 * is also used to write the parameters in the save operations 
 * 
 * 
 * 
 * */
public class parameters {
private int pointsX[],pointsY[];

private Color color ,stroke;
public parameters(int[] inputX,int[]inputY , Color c,Color s){
	pointsX=new int[inputX.length];
	pointsY=new int[inputY.length];
	for(int i=0;i<inputX.length;i++){
	
	pointsX[i]=inputX[i];
	pointsY[i]=inputY[i];
}
color=c;
	stroke =s;}


public int[]getPointsX(){
	return this.pointsX;
}

public int[]getPointsY(){
	return this.pointsY;
}


public Color getColor(){return color;}

public Color getStroke()
{return stroke;}

public String getAttributes()
{
String result="";

for(int i=0;i<pointsX.length;i++)
{
	result+=pointsX[i]+" ";
}
for(int i=0;i<pointsY.length;i++)
{
	result+=pointsY[i]+" ";
}


return result;
	
}
}
