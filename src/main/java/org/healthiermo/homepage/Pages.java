package org.healthiermo.homepage;

public enum Pages {

    EMERGENCY("Emergency Preparedness and Response"),
    POLICY("Policy Development and Support"),
    COMMUNICATIONS("Communications"),
    COMMUNITY("Community partnership and Development"),
    ORGANIZATION("Organization Administrative Competencies"),
    ACCOUNTABILITY("Accountability and Performance Management"),
    ASSESSMENT("Assessment and Surveillance"),
    INNER("Inner Clickage on Pie"),
    OUTER("Outer Ring Hotspots");

    private String fullName;

    Pages(String fullName) { 
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}

enum Pie {
    ENVIRONMENT,
    COMMUNICABLE_DISEASE,
    CHRONIC,
    LINKAGE,
    INJURY,
    MATERNAL
}