package util;

import java.util.Map;

public class MapUtil {
    public static boolean isNotBlank(Map map){
        if(map != null && map.keySet().size()>0){
            return true;
        }
        return false;
    }

    public static boolean isBlank(Map map){
        if(map == null || map.keySet().size()<=0){
            return true;
        }
        return false;
    }
}
