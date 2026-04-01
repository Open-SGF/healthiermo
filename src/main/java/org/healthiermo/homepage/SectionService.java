package org.healthiermo.homepage;

import org.healthiermo.homepage.dto.SectionEntry;
import org.healthiermo.homepage.dto.SectionForm;
import org.healthiermo.homepage.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SectionService {

    private static final Logger log = LoggerFactory.getLogger(SectionService.class);

    private TextBoxRepository textBoxRepository;
    private final FileUtil fileUtil;

    public SectionService(TextBoxRepository textBoxRepository, FileUtil fileUtil) {
        this.textBoxRepository = textBoxRepository;
        this.fileUtil = fileUtil;
    }

    /**
     * Persists all sections in the form to the database and handles file uploads.
     *
     * @param form the form containing sections to save
     * @param box  the box identifier (e.g. "MISC", "OUTER", or a page name)
     */
    public void saveAll(SectionForm form, String box) {
        log.info("Saving {} sections for box: {}", form.getSections().size(), box);
        for (SectionEntry entry : form.getSections()) {
            String key = entry.getSectionKey();
            String text = entry.getText();

            // Skip sections without required data
            if (!StringUtils.hasText(key) || !StringUtils.hasText(text)) {
                continue;
            }

            textBoxRepository.save(new TextBox(box, key, entry.getTitle(), text));
            fileUtil.handleFile(entry.getFile(), box, key);
        }
    }

    /**
     * Loads title and text from the repository for each section in the form.
     *
     * @param form the form to populate
     * @param box  the box identifier (e.g. "MISC", "OUTER", or a page name)
     */
    public void loadSections(SectionForm form, String box) {
        for (SectionEntry entry : form.getSections()) {
            TextBox textBox = textBoxRepository
                    .findById(new CompositeKey(box, entry.getSectionKey()))
                    .orElse(new TextBox());
            entry.setTitle(textBox.getTitle());
            entry.setText(textBox.getText());
        }
    }

    public List<TextBox> findAll() {
        return textBoxRepository.findAll();
    }
}
