package utils;

import java.util.HashMap;
import java.util.Map;

public class MapSqlConverterType {
    public static final Map<Class<?>,String> CONVERTER=new HashMap<Class<?>,String>(){
        {
            put(Integer.class,"INT");
            put(Long.class,"INT");
            put(Double.class,"INT");
            put(Float.class,"INT");
            put(Short.class,"INT");
            put(Byte.class,"INT");
            put(Boolean.class,"INT");
            put(Character.class,"VARCHAR (250)");
            put(String.class,"VARCHAR (250)");
        }
    };

}
