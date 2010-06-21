/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ville1.modele;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Guillaume
 */
public class ActivitesResourceV1 extends ServerResource {

    List<ActiviteV1> listActivite = new ArrayList<ActiviteV1>();
    private Representation resultat;
    List<String> erreurs;

    protected void init() {

        try {
            listActivite = ActiviteV1.getAll();
            if (listActivite == null) {
                setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            }
        } catch (NumberFormatException exc) {
            // Indiquer que la requete est mal formee
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            // Préciser l'erreur
            resultat = new StringRepresentation("idNotInteger");
            exc.printStackTrace();
        } catch (SQLException exc) {
            setStatus(Status.SERVER_ERROR_INTERNAL);
            resultat = new StringRepresentation(exc.getMessage());
            exc.printStackTrace();
        }
    }

    /** Représentation XML (en UTF8) en réponse à une requête GET.
     * <br/>
     * Retour possible : 200, 401 avec idNotInteger, 404, 500 (si
     * problème avec la BD)
     */
    @Get
    public Representation doGet() {
        init();
        if (getStatus() == Status.SUCCESS_OK) {
            // Pas encore d'erreur => produit existe
            assert listActivite != null;
            try {
                DomRepresentation dom = new DomRepresentation(MediaType.TEXT_XML);
                // Generer un DOM representant la ressource
                Document doc = dom.getDocument();
                Iterator<ActiviteV1> itr = listActivite.iterator();
                Element root = doc.createElement("activites");
                doc.appendChild(root);
                ActiviteV1 activite = null;
                while (itr.hasNext()) {
                    activite = itr.next();
                    Element elementAct = doc.createElement("activite");
                    elementAct.setAttribute("libelle", activite.getLibelle());
                    elementAct.setAttribute("type", activite.getType());
                    //A VERIFIER
                    elementAct.setAttribute("placesLibres", "oui");
                    if (!ActiviteV1.estLibre(activite.getLibelle())) {
                        elementAct.setAttribute("placesLibres", "non");
                    }
                    root.appendChild(elementAct);
                }
                // Encodage en UTF-8
                dom.setCharacterSet(CharacterSet.UTF_8);
                resultat = dom;
            } catch (IOException exc) {
                setStatus(Status.SERVER_ERROR_INTERNAL);
                exc.printStackTrace();
            } catch (SQLException exc) {
                setStatus(Status.SERVER_ERROR_INTERNAL);
                resultat = new StringRepresentation(exc.getMessage());
                exc.printStackTrace();
            }
        }
        return resultat;
    }
}
