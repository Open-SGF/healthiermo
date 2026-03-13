package org.healthiermo.homepage.dto;

import org.healthiermo.homepage.enums.SectionTitleEnum;
import org.springframework.web.multipart.MultipartFile;

/**
 * A single section of a form, holding a title, text body, optional file upload,
 * and the key that identifies which section it belongs to (e.g. "ENVIRONMENT").
 */
public class SectionEntry {
    private String sectionKey;
    private String title;
    private String text;
    private MultipartFile file;

    public SectionEntry() {}
    public SectionEntry(String sectionKey) {
        this.sectionKey = sectionKey;
    }

    public String getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(String sectionKey) {
        this.sectionKey = sectionKey;
    }

    public String getTitle() {
        return title;
    }

    public String getSectionTitle() {
        return SectionTitleEnum.valueOf(sectionKey).getSectionTitle();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "SectionEntry{key='" + sectionKey + "', title='" + title + "', text='" + text + "'}";
    }
}
