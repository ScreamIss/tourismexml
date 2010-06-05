/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ville1;

import org.restlet.Component;
import org.restlet.data.Protocol;
import ville1.modele.ActiviteResourceV1;

/**
 *
 * @author Guillaume
 */
public class ServerVille1 {

    public static void main(String[] args) throws Exception {
// Creer un composant serveur
        Component composantServeur = new Component();
        // Lui adjoindre un serveur HTTP sur le port 8182
        composantServeur.getServers().add(Protocol.HTTP, 8182);
        // Lui attacher une ressource
//        composantServeur.getDefaultHost().attach("/produits/{id}", ProduitResource.class);
        
        composantServeur.getDefaultHost().attach("/xml", ActiviteResourceV1.class);
        // Demarrer le composant (et donc le serveur)
        composantServeur.start();

    }
}
