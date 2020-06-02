package org.ibs.cdx.gode.codegen.velocity.util;

import java.util.List;
import java.util.stream.Collectors;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    public static String camelCase(String data){
        return capitalize(lowerCase(data));
    }

    public static String camelJoin(List<String> data){
        String temp = "\"%s\"";
        return data.stream().map(i->String.format(temp,i)).collect(Collectors.joining(","));
    }
}
