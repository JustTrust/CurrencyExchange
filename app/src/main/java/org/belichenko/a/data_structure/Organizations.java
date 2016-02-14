package org.belichenko.a.data_structure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
        StringBuilder sb = new StringBuilder();
        sb.append("Organizations = " + title );
        Iterator it = currencies.entrySet().iterator();
        while (it.hasNext()) {
            sb.append("\n");
            Map.Entry pair = (Map.Entry)it.next();
            Courses course = (Courses) pair.getValue();
            sb.append(pair.getKey() + " = " + course.ask +" - "+course.bid);
        }
        return sb.toString();
    }
}
