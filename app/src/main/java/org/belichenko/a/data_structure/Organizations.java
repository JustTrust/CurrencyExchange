package org.belichenko.a.data_structure;

import java.util.HashMap;

/**
 *
 */
public class Organizations {
    public String id;
    public int oldId;
    public int orgType;
    public boolean branch;
    public String title;
    public String regionId;
    public String cityId;
    public String phone;
    public String address;
    public String link;
    public HashMap<String, Courses> currencies;

    public String mfo;

    @Override
    public String toString() {
        return title;
    }
}
