package src;


import java.util.ArrayList;
import java.util.Stack;

import src.MyShape;

public class engine {
	static public Stack<trace> action = new Stack<trace>();
	static public Stack<trace> redo = new Stack<trace>();
	static public ArrayList<MyShape> objects = new ArrayList<MyShape>();;
	static public ArrayList<Boolean> isDeleted = new ArrayList<Boolean>();;
	static public int index = -1;
	static public ArrayList<String> imports = new ArrayList<String>();

	/*
	 * 0--> add --> index 
	 * 1--> remove(delete) --> index 
	 * 2--> move --> index ,* old parm. 
	 * 3-->resize--> index , old parm. 4-->change color --> index ,
	 * Color 5-->change stroke color 6-->???
	 */

	

	public static void add(MyShape e)

	{
		index = objects.size()-1;
		trace z = new trace(e.getParameters(), e.getParameters(), index, 0);
		action.push(z);
		redo = new Stack<trace>();
		
	}

	public static void delete(int index)
	{
		isDeleted.set(index, true);
		trace z = new trace(null, null, index, 1);
		action.push(z);
		redo = new Stack<trace>();
		
	}

	public static void other(int index, int op, parameters old) {
		trace z = new trace(old, objects.get(index).getParameters(), index, op);
		redo = new Stack<trace>();
		action.push(z);
		
	}

}
