package org.healthiermo.homepage.enums;

public enum SectionTitleEnum {
    OUTER_RING ("Outer Ring"),
    INNER_RING ("Inner Ring"),
    HELP ("Help Button");

    private final String sectionTitle;
    SectionTitleEnum(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }
    public String getSectionTitle() {
        return sectionTitle;
    }
}
