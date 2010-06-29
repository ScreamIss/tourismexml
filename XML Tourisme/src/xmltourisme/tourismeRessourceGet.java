/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xmltourisme;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

/**
 *
 * @author Guillaume
 */
public class tourismeRessourceGet {



    public static void main(String[] args) throws IOException, JDOMException{

        SAXBuilder sxb = new SAXBuilder();
        Document doc = sxb.build("http://localhost:8182/xml/activites/");

//Récupère les informations contenues dans ce XML
        Element root = doc.getRootElement();

        List list = root.getChildren("activite");
        String entree = null;
        Iterator it = list.iterator();
        while(it.hasNext()){
            Element courant = (Element) it.next();
            System.out.println(courant.getAttribute("libelle").getValue());
            System.out.println("de type : "+courant.getAttribute("type").getValue());
            System.out.println(courant.getAttribute("placesLibres").getValue());
        }


    }


}
