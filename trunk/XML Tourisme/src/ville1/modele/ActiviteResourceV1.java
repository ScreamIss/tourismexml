/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ville1.modele;

import java.io.IOException;
import java.sql.SQLException;
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
public class ActiviteResourceV1 extends ServerResource {

    private ActiviteV1 activite;
    private Representation resultat;
    List<String> erreurs;

    protected void init() {
        String libelle = getRequest().getAttributes().get("libelle").toString();
        try {
            activite = ActiviteV1.getByLib(libelle);
            if (activite == null) {
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
            assert activite != null;
            try {
                DomRepresentation dom = new DomRepresentation(MediaType.TEXT_XML);
                // Generer un DOM representant la ressource
                Document doc = dom.getDocument();
                Element root = doc.createElement("activite");
                doc.appendChild(root);
                root.setAttribute("libelle", activite.getLibelle());
                root.setAttribute("type", activite.getType());
                //A VERIFIER
                root.setAttribute("placesLibres", "oui");
                if (!ActiviteV1.estLibre(activite.getLibelle())) {
                    root.setAttribute("placesLibres", "non");
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
