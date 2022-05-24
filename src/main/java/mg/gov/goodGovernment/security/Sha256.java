package mg.gov.goodGovernment.security;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * Service lié au cryptage en Sha256. Le but de cet service n'est pas de reinventer la roue mais de faire
 * du refactoring des codes liés aux manipulations de hash en sha256. Grâce au possibilité de création des
 * nouvelles méthodes, je préfère ne pas implémenter l'interface Function de java.
 *
 * @author Mandresy
 */
public class Sha256 {
    /**
     * Crypter une chaîne de caractères en Sha256
     * @param s Chaîne de caractère non crypté
     * @return Chaîne de caractère crypté en sha256
     */
    public static String hash(String s) {
        return  Hashing.sha256().hashString( s, StandardCharsets.UTF_8).toString();
    }
}
