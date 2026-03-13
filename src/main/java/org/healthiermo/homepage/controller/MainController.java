package org.healthiermo.homepage.controller;

import org.healthiermo.homepage.SectionService;
import org.healthiermo.homepage.TextBox;
import org.healthiermo.homepage.dto.SectionForm;
import org.healthiermo.homepage.enums.PagesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private final SectionService sectionService;
    public MainController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping("/save/{page}")
    public ModelAndView submit(@ModelAttribute("configForm") SectionForm sectionForm,
                               @PathVariable String page, Model model) {
        page = page.toUpperCase();
        log.info("Received POST /save/{} with {} sections", page, sectionForm.getSections().size());

        sectionService.saveAll(sectionForm, PagesEnum.valueOf(page).toString());

        ModelAndView view = new ModelAndView("panel");
        view.addObject("configForm", sectionForm);
        view.addObject("pageShortName", page);
        view.addObject("pageTitle", PagesEnum.valueOf(page).getFullName());
        return view;
    }

    @PostMapping("/save/outer")
    public ModelAndView submitOuter(@ModelAttribute("outerForm") SectionForm sectionForm,
                                    Model model) {
        sectionService.saveAll(sectionForm, "OUTER");

        ModelAndView view = new ModelAndView("outer");
        view.addObject("outerForm", sectionForm);
        view.addObject("pageShortName", "OUTER");
        view.addObject("pageTitle", PagesEnum.valueOf("OUTER").getFullName());
        return view;
    }

    @PostMapping("/save/misc")
    public ModelAndView submitMisc(@ModelAttribute("miscForm") SectionForm sectionForm,
                                   Model model) {
        sectionService.saveAll(sectionForm, "MISC");

        ModelAndView view = new ModelAndView("misc");
        view.addObject("miscForm", sectionForm);
        view.addObject("pageShortName", "misc");
        view.addObject("pageTitle", "Misc Settings");
        return view;
    }

    @GetMapping("/text")
    @ResponseBody
    public List<TextBox> getText() {
        return sectionService.findAll();
    }

}
