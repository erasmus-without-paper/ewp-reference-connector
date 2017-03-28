package eu.erasmuswithoutpaper.course.entity;

public enum LearningOpportunitySpecificationType {
    COURSE, CLASS, MODULE, DEGREE_PROGRAMME;

    public static String displayName(LearningOpportunitySpecificationType losType) {
        String displayName;
        switch (losType) {
            case DEGREE_PROGRAMME:
                displayName = "Degree Programme";
                break;
            default:
                displayName = losType.toString();
                displayName = displayName.charAt(0) + displayName.substring(1).toLowerCase();
                break;
        }
        return displayName;
    }
    
    public static String abbreviation(LearningOpportunitySpecificationType losType) {
        String abbreviation;
        switch (losType) {
            case COURSE:
                abbreviation = "CR";
                break;
            case CLASS:
                abbreviation = "CLS";
                break;
            case MODULE:
                abbreviation = "MOD";
                break;
            case DEGREE_PROGRAMME:
                abbreviation = "DEP";
                break;
            default:
                abbreviation = "";
        }
        return abbreviation;
    }
    
    public static String loiAbbreviation(LearningOpportunitySpecificationType losType) {
        return abbreviation(losType) + "I";
    }
}
