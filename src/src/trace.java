package src;

public class trace {
	private parameters oldPar ,newPar;
	private int operation,index;
	
	public trace(parameters OldPar, parameters NewPar,int Index,int Operation)
	{
		oldPar=OldPar;
		newPar = NewPar;
		operation=Operation;
		index=Index;
	}
	
	
	public int getIndex(){ return index; }
	public int getOperation(){ return operation; }
	public parameters getOld(){ return oldPar; }
	public parameters getNew(){ return newPar; }
	
}
