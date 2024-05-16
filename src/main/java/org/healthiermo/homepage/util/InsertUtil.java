package org.healthiermo.homepage.util;

import org.healthiermo.homepage.TextBox;
import org.healthiermo.homepage.TextBoxRepository;

public class InsertUtil {

    public static void insert(TextBoxRepository textBoxRepository, String box, String pie, String title, String text) {
        if(box == null || box.equals("")) {
            return;
        }

        if(pie == null || pie.equals("")) {
            return;
        }

        if(text == null || text.equals("")) {
            return;
        }
        textBoxRepository.save(new TextBox(box, pie, title, text));
    }
}
