package src;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import com.google.gson.stream.JsonReader;

public class JSON_READER {

	public static void load(File file) throws IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		JsonReader reader = new JsonReader(new FileReader(file));

		ArrayList<String> imports = new ArrayList<String>();
		reader.beginArray();

		reader.beginArray();
		reader.beginObject();
		reader.nextName();
		String ik = reader.nextString();
		int size = Integer.parseInt(ik);
		reader.endObject();
		for (int i = 0; i < size; i++) {

			reader.beginObject();
			reader.nextName();
			String imp = reader.nextString();
			if (imp.contains("\\")) {
				imp = imp
						.substring(imp.lastIndexOf('\\') + 1, imp.indexOf('.'));
			}
			boolean found = false;
			for (int k = 0; k < engine.imports.size(); k++) {
				if (imp.equals(engine.imports.get(k))) {
					found = true;
				}
			}
			if (!found) {
				// do something
				JOptionPane pane = new JOptionPane();
				pane.showMessageDialog(null, "Cannot load, Please import the extended shapes");
				return;

			}
			reader.endObject();
			imports.add(imp);
		}

		reader.endArray();
		reader.beginArray();
		while (reader.hasNext()) {
			reader.beginObject();
			reader.nextName();
			MyShape e;
			
			String imp = reader.nextString();
			if (imp.contains("\\")) {
				imp = imp
						.substring(imp.lastIndexOf('\\') + 1, imp.indexOf('.'));
				int index = 0;
				for (int k = 0; k < engine.imports.size(); k++) {
					if (imp.equals(engine.imports.get(k))) {
						index = k;
						k = engine.imports.size() + 1;
					}

				}
				Class c = Class.forName("src."+imp,true,Main.plug);
				e=(MyShape) c.newInstance();
				
				
			} else {
				 e= (MyShape) Class.forName("src."+imp)
					.newInstance();
				 }
			reader.nextName();
			imp=reader.nextString();
			e.setParameters2(imp);
			reader.nextName();
			String colors[] = reader.nextString().split(" ");
			e.changeColor(new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2])));
			e.setfocused(false);
			engine.objects.add(e);
			engine.isDeleted.add(false);
			reader.endObject();

		}
		reader.endArray();
		reader.endArray();
	}
}
