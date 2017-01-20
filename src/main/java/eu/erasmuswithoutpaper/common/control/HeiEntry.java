package eu.erasmuswithoutpaper.common.control;

public class HeiEntry {
    private String id;
    private String name;
    private String url;

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

    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
