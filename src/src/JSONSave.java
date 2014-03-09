package src;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import com.google.gson.stream.JsonWriter;

public class JSONSave {
	public static void save(String path) throws IOException

	{
		JsonWriter writer = new JsonWriter(new FileWriter(new File(path)));
		writer.beginArray();
		writer.beginArray();
		writer.beginObject();
		writer.name("Imports Size").value(engine.imports.size());
		writer.endObject();

		for (int i = 0; i < engine.imports.size(); i++) {
			writer.beginObject();
			writer.name("Class").value(engine.imports.get(i)).flush();
			writer.endObject();
		}
		writer.endArray();
		writer.flush();
		writer.beginArray();
		for (int i = 0; i < engine.objects.size(); i++) {
			if (!engine.isDeleted.get(i)) {
				writer.beginObject();
				MyShape e = engine.objects.get(i);
				String name = e.getClass().getName();
				if(name.contains("src.")){
					name=name.replaceAll("src.", "");
				}
				
				for(int k=0;k<engine.imports.size();k++)
				{
					if(name.equals(engine.imports.get(k)))
					{
						name="\\"+name+".jar";
					}
				}
				writer.name("Class").value(name);
				writer.name("Attributes")
						.value(e.getParameters().getAttributes()).flush();
				writer.name("Color")
						.value("" + e.getColor().getRed() + " "
								+ e.getColor().getGreen() + " "
								+ e.getColor().getBlue()).flush();
				writer.endObject();
			}
			writer.flush();

		}
		writer.endArray();
		writer.endArray();
		writer.flush();
		JOptionPane pane = new JOptionPane();
		pane.showMessageDialog(null, "JSON File Saved successfully");

	}
}
