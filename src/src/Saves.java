package src;



import java.io.File;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import src.MyShape;
import src.engine;

public class Saves {
static	DOMSource source;

	public static void SavesXML(String path) throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element rootElement = document.createElement("Paint");
		rootElement.appendChild(document.createTextNode(""));
		document.appendChild(rootElement);
		
		// the imports here should contain every imported class used in the file 
		for(int i=0;i<engine.imports.size();i++)
		{
			Element imports = document.createElement("Imports");
			imports.appendChild(document.createTextNode(""));
			Element hoksh = document.createElement("Class");
			hoksh.appendChild(document.createTextNode(""+engine.imports.get(i)+""));
			imports.appendChild(hoksh);
			rootElement.appendChild(imports);
		}
		
		int length=engine.objects.size();
		int or=0;
		
        //now it comes for the objects itself 
		int deleted =length;
		for (int i = 0; i < length; i++) 
		{	
			if(!engine.isDeleted.get(i))
			{
				deleted--;
				MyShape shape = engine.objects.get(i);
				String name = shape.getClass().getName();
				if(name.contains("src.")){name=name.replace("src.", "");}
				
				for(int k=0;k<engine.imports.size();k++)
				{
					if(name.equals(engine.imports.get(k)))
					{
						name="Plugin"+name+".jar";
					}
				}

				Element element = document.createElement(name);
				String locString =""+shape.getParameters().getAttributes();
				Element locations = document.createElement("Attributes");
				locations.appendChild(document.createTextNode(locString+""));
				element.appendChild(locations);
				Element color = document.createElement("Color");
				color.appendChild(document.createTextNode(""+shape.getColor().getRed()+" "+shape.getColor().getBlue()+" "+shape.getColor().getGreen()+""));
				element.appendChild(color);
				Element order  = document.createElement("order");
				order.appendChild(document.createTextNode(""+(or)+""));
				element.appendChild(order);
				or++;
				// add the stroke color ??
				rootElement.appendChild(element);
			}
		}
		Element size = document.createElement("Size");
		size.appendChild(document.createTextNode(""+(length-deleted)+""));
		rootElement.appendChild(size);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		source = new DOMSource(document);
		StreamResult result = new StreamResult(new File(path));
		transformer.transform(source, result);
		JOptionPane pane = new JOptionPane();
		pane.showMessageDialog(null, "XML File Saved successfully");
	}

	public DOMSource getDocument() {
		return source;
	}
}
