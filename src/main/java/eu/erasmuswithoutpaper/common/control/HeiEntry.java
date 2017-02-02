package eu.erasmuswithoutpaper.common.control;

import java.util.Map;

public class HeiEntry {
    private String id;
    private String name;
    private Map<String, String> urls;

    public HeiEntry() {
    }
    
    public HeiEntry(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getUrls() {
        return this.urls;
    }
    public void setUrls(Map<String, String> urls) {
        this.urls = urls;
    }
}
