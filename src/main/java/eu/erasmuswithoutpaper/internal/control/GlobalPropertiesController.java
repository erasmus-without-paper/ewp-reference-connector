package eu.erasmuswithoutpaper.internal.control;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class GlobalPropertiesController {
    public static enum University {
        IKEA_U,
        POMODORO_U
    }
    
    private University university;
    private String universityName;
    private String hostname;
    
    @PostConstruct
    private void loadProperties() {
        this.hostname = System.getProperty("ewp.host.name", "localhost");

        String ewpHost = System.getProperty("ewp.host", "IKEA").toUpperCase();
        switch(ewpHost) {
            case "POMODORO":
                university = University.POMODORO_U;
                universityName = "Pomodoro University";
                break;
            case "IKEA":
                university = University.IKEA_U;
                universityName = "IKEA University";
                break;
        }
    }
    
    public University getUniversity() {
        return this.university;
    }
    
    public String getUniversityName() {
        return this.universityName;
    }

    public String getHostname() {
        return this.hostname;
    }
}
