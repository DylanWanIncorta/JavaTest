import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.io.Writer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class SmartImport {

    public static void main(String[] args) {
	
		String zipf = args[0];
		String old_src = args[1];
		String new_src = args[2];
		
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

		String in_folder_name = zipf;
		String out_folder_name = "new";
		
		File in_folder = new File(in_folder_name+"\\schemas");
		File[] listOfFiles = in_folder.listFiles();
		
		File out_folder1 = new File(out_folder_name);
		File out_folder2 = new File(out_folder_name+"\\schemas");
		
    	if(!out_folder1.exists()){
//			System.out.println("Create new");
    		out_folder1.mkdir();
			out_folder2.mkdir();
    	}
		
		

    	try {
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			
			for (int i = 0; i < listOfFiles.length; i++) {
				File file = listOfFiles[i];
			
				Document doc = docBuilder.parse(file);
				doc.getDocumentElement().normalize();
			
				if (file.getName().endsWith("loader.xml")) {
					replaceSource(old_src, new_src, doc, "sql");
				} else if (file.getName().endsWith("schema.xml")) {
					replaceSource(old_src, new_src, doc, "table");
				} else {
					System.out.println("Skip "+ file.getName());
				}
			
				writeFile(doc, out_folder_name+"\\schemas\\"+file.getName());
			}


		} catch (SAXParseException err) {
			System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
			System.out.println(" " + err.getMessage ());
		} catch (SAXException e) {
			Exception x = e.getException ();
			((x == null) ? e : x).printStackTrace ();
		} catch (Throwable t) {
			t.printStackTrace ();
		}  
	}
	
	public static final void replaceSource(String old_src, String new_src, Document doc, String tag) throws Exception {

			NodeList list = doc.getElementsByTagName(tag);
			// System.out.println("Search tag "+tag);
			// System.out.println("Old Source:"+old_src);
			// System.out.println("New Source:"+new_src);
			int total = list.getLength();
			//System.out.println("Found "+total+" elements");
			
			for(int i=0; i<total; i++) {

				Node tagNode = list.item(i);
				if(tagNode.getNodeType() == Node.ELEMENT_NODE) {

					Element firstElement = (Element)tagNode;              

					if (firstElement.getAttribute("source").equals(old_src)) {
						System.out.println("Replacing "+firstElement.getAttribute("source")+" with "+new_src);
						firstElement.setAttribute("source", new_src);

					}
				}
			} 
	}
	
	
	public static final void writeFile(Document xml, String full_file_name) throws Exception {

	System.out.println("Writing to:"+full_file_name);
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        // Writer out = new StringWriter();
		File out = new File(full_file_name);
        tf.transform(new DOMSource(xml), new StreamResult(out));

    }

}


 