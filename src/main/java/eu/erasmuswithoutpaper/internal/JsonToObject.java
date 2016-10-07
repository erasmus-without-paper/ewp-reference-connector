package eu.erasmuswithoutpaper.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonToObject {
    
    private JsonToObject(){}
    
    public static <T> T mapToObject(Class<T> c, String json) throws IOException {
    
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        //JSON from file to Object
        T object = mapper.readValue(json, c);

        return object;
    }
}
