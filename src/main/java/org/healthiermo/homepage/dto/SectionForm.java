package org.healthiermo.homepage.dto;

import java.util.ArrayList;
import java.util.List;

public class SectionForm {

    private List<SectionEntry> sections = new ArrayList<>();

    public SectionForm() {}

    public List<SectionEntry> getSections() {
        return sections;
    }
    public void setSections(List<SectionEntry> sections) {
        this.sections = sections;
    }

    /**
     * Creates a form pre-populated with empty entries for the given section keys.
     */
    public static SectionForm withKeys(String... keys) {
        SectionForm form = new SectionForm();
        for (String key : keys) {
            form.getSections().add(new SectionEntry(key));
        }
        return form;
    }

    @Override
    public String toString() {
        return "ConfigurationForm{sections=" + sections + "}";
    }
}
