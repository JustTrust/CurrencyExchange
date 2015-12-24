package org.belichenko.a.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArrayMap;

import com.google.gson.Gson;

import org.belichenko.a.App;
import org.belichenko.a.utils.MyConstants;
import org.belichenko.a.currencyexchange.R;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Contains list of users and passwords
 */
public class StorageOfUser implements MyConstants, Serializable{
    private static StorageOfUser ourInstance = new StorageOfUser();
    private ArrayMap<String, String> users = new ArrayMap<>();
    private static App context = App.getInstance();
    private static SharedPreferences mPrefs;

    public static StorageOfUser getInstance() {
        return ourInstance;
    }

    private StorageOfUser() {
        mPrefs = context.getSharedPreferences(MAIN_PREFERENCE, Context.MODE_PRIVATE);
        fillUsers();
    }

    public int setUser(String name, String pass) {

        if (findUser(name, pass)) {
            return R.string.existin_user; // we already have this user
        }
        String hashPass = getSha1Hash(pass);
        if (hashPass != null) {
            users.put(name, hashPass);

            // TODO: 22.12.2015 check json
            Gson json = new Gson();
            String jString = json.toJson(users);

            SharedPreferences.Editor edit = mPrefs.edit();
            edit.putString(STORAGE_OF_USERS, users.toString());
            edit.putString(USER_IS_LOGIN, name);
            edit.apply();

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

    public void fillUsers() {

        String usersString = mPrefs.getString(STORAGE_OF_USERS, null);

        if ((usersString != null) && (usersString.length() > 0)) {
            users.clear();
            String[] keyValue = usersString.split(";");
            for (String elemnt:keyValue) {
                if (elemnt.length()>0){
                    String[] s = elemnt.split("=");
                    users.put(s[0],s[1]);
                }
            }
        }
    }
}
