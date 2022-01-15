package mg.gov.goodGovernment.security;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Sha256 {
    /**
     * Crypter en sha256 une chaîne de caractères
     * @param s Chaîne de caractère non crypté
     * @return Chaîne de caractère crypté en sha256
     */
    public static String hash(String s) {
        return  Hashing.sha256().hashString( s, StandardCharsets.UTF_8).toString();
    }
}
