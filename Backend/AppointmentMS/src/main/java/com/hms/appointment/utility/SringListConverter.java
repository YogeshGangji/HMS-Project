package com.hms.appointment.utility;

import java.util.Collections;
import java.util.List;

public class SringListConverter {

public static String covertListToString(List<String> list) {

    if(list==null||list.isEmpty())
        return "";

    return String.join(",",list);

    }

    public static List<String> convertStringToList(String str)
    {
        if(str==null||str.isEmpty())
            return Collections.emptyList();

        return List.of(str.split(","));
    }

}

