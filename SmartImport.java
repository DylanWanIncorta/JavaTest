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
		String src;
		
		String old_src = "incorta_metadata";
		String new_src = "InCorta_Metadata";
		
		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse (new File("558_loader.xml"));

			// normalize text representation
			doc.getDocumentElement().normalize();
//			System.out.println ("Root element of the doc is " + doc.getDocumentElement().getNodeName());

			NodeList listOfSqls = doc.getElementsByTagName("sql");
			int totalSqls = listOfSqls.getLength();
//			System.out.println("Total no of sqls : " + totalSqls);

			for(int i=0; i<totalSqls; i++) {

				Node sqlNode = listOfSqls.item(i);
				if(sqlNode.getNodeType() == Node.ELEMENT_NODE) {

					Element firstElement = (Element)sqlNode;              

					src = firstElement.getAttribute("source");
					System.out.println("Source :"+src);
					if (src == old_src ) 
					  firstElement.setAttribute("source",new_src);

				}
			} //end of for loop with s var
			
			prettyPrint(doc);

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
	
	public static final void prettyPrint(Document xml) throws Exception {

        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));

        System.out.println(out.toString());

    }

}


 