package org.healthiermo.homepage.enums;

public enum PagesEnum {
    EMERGENCY("Emergency Preparedness and Response"),
    POLICY("Policy Development and Support"),
    COMMUNICATIONS("Communications"),
    COMMUNITY("Community partnership and Development"),
    ORGANIZATION("Organization Administrative Competencies"),
    ACCOUNTABILITY("Accountability and Performance Management"),
    ASSESSMENT("Assessment and Surveillance"),
    INNER("Inner Clickage on Pie"),
    OUTER("Outer Ring Hotspots");

    private final String fullname;

    PagesEnum(String fullname) {
        this.fullname = fullname;
    }
    public String getFullName() {
        return fullname;
    }
}
