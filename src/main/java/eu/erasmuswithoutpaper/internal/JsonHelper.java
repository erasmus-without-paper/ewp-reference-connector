package eu.erasmuswithoutpaper.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonHelper {
    
    private JsonHelper(){}
    
    public static <T> T mapToObject(Class<T> c, String json) throws IOException {
    
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        //JSON from file to Object
        T object = mapper.readValue(json, c);

        return object;
    }
    
    public static String objectToJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
