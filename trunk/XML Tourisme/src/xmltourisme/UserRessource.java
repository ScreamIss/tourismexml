/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xmltourisme;

import java.io.IOException;
import java.io.Reader;
import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Guillaume
 */
public class UserRessource {



    public static void main(String[] args) throws IOException{
        ClientResource resource = new ClientResource("http://localhost:8182/xml/manegedelire");
        
//Récupère le XML en DomRepresentation
        Representation r = resource.get(MediaType.TEXT_XML);
        DomRepresentation dom = new DomRepresentation(r);
        Document doc = dom.getDocument();

//Récupère les informations contenues dans ce XML
        Element root = doc.getDocumentElement();

        String libelle = root.getAttribute("libelle");
        String type = root.getAttribute("type");
        System.out.println(libelle + " " + type);


    }


}
