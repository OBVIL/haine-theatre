package tei;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RecursiveDOM {
    
	
	public static void main(final String[] args) throws SAXException, IOException, ParserConfigurationException {
        new RecursiveDOM("anon_honneur-theatre_1620.xml");
    }

    public RecursiveDOM(final String file) throws SAXException, IOException, ParserConfigurationException {
        final DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        final DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        final Document doc = docBuilder.parse(new File(file));
        final List<String> l = new ArrayList<String>();
        parse(doc, l, doc.getDocumentElement());
        System.out.println(l);
    }

    private void parse(final Document doc, final List<String> list, final Element e) {
        final NodeList children = e.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            final Node n = children.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
            	
                list.add(n.getNodeName()+" --> "+n.getTextContent());
                parse(doc, list, (Element) n);
             //   System.out.println((Element)n);
            }
        }
    }

}