package org.belichenko.a.login;

import android.util.ArrayMap;

import org.belichenko.a.constant.MyConstants;
import org.belichenko.a.currencyexchange.R;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.Map;

/**
 * Contains list of users and passwords
 */
public class StorageOfUser implements MyConstants {
    private static StorageOfUser ourInstance = new StorageOfUser();
    private ArrayMap<String, String> users = new ArrayMap<>();

    public static StorageOfUser getInstance() {
        return ourInstance;
    }

    private StorageOfUser() {
    }

    public int setUser(String name, String pass) {

        if (findUser(name, pass)) {
            return R.string.existin_user; // we already have this user
        }
        String hashPass = getSha1Hash(pass);
        if (hashPass != null) { //14CC5DB28AAF9CFF18AC396CF140D9D33EE4B03A
            users.put(name, hashPass);
            return R.string.succsessfull_regisration;

        }
        return R.string.chanche_pass;
    }

    // find user and match password
    public boolean findUser(String name, String pass) {
        if ((name == null) | (pass == null)) {
            return false;
        }
        if ((name.isEmpty()) | (pass.isEmpty())) {
            return false;
        }
        if (users.containsKey(name)) {
            String oldPass = users.get(name);
            String newPass = getSha1Hash(pass);
            if (oldPass.equals(newPass)) {
                return true;
            }
        }
        return false;
    }

    // return Sha1 hash for string
    private static String getSha1Hash(String toHash) {
        String hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = toHash.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();

            // This is ~55x faster than looping and String.formating()
            hash = bytesToHex(bytes);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hash;
    }

    // convert from byte array to hex string
    private static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : users.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            result.append(key + "=" + value + ";");
        }
        return result.toString();
    }

    public void fillUsers(String source) {
        if ((source != null) && (source.length() > 0)) {
            users.clear();
            String[] keyValue = source.split(";");
            for (String elemnt:keyValue) {
                if (elemnt.length()>0){
                    String[] s = elemnt.split("=");
                    users.put(s[0],s[1]);
                }
            }
        }
    }
}
