package src;

public class UNDO_REDO {

	/*
	 * 0--> create a new shape 
	 * 1--> delete a shape 
	 * 2--> move a shape 
	 * 3--> resize a shape 
	 * 4--> change fill color 
	 * 5--> change stroke color ........
	 */
	public static void undo() {
		if (engine.action.isEmpty()) 
		{
			// No more actions to do
			return;
		}
		trace e = engine.action.pop();

		engine.redo.push(e);
		int op = e.getOperation();
		parameters oldPar = e.getOld();
		int index = e.getIndex();
		switch (op) {
		case 0:
			// that means an object has been created
			engine.isDeleted.set(index, true);
			break;
		case 1:
			// that means an object has been deleted
			engine.isDeleted.set(index, false);
			break;
		case 2:
			engine.objects.get(index).setParameters2(oldPar.getAttributes());
			// re-move it
			break;
		case 3: 
			// resize it
			engine.objects.get(index).setParameters2(oldPar.getAttributes());
			break;
		case 4: 
			// change fill color
			engine.objects.get(index).changeColor(oldPar.getColor());
			break;

		default:
			break;
		}
	}

	public static void redo()

	{
		if (!engine.redo.isEmpty()) {
			trace e = engine.redo.pop();
			engine.action.push(e);

			int index = e.getIndex(), op = e.getOperation();
			parameters newPar = e.getNew();

			switch (op) {
			case 0:
				// delete the object
				engine.isDeleted.set(index, false);
				break;
			case 1: 
				// cancel the delete
				engine.isDeleted.set(index, true);
				break;
			case 2: 
				// remove
				// re-re-move :D
				engine.objects.get(index).setParameters2(newPar.getAttributes());
				break;

			case 3: 
				// re-resize
				// re-resize
				engine.objects.get(index).setParameters2(newPar.getAttributes());
				break;
			case 4: // re-change the fill in color
				engine.objects.get(index).changeColor(newPar.getColor());
				break;
			case 5: 
				// re-change the stroke color
				break;
			default:
				break;
			}

		}

	}

}
