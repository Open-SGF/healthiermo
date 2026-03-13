package org.healthiermo.homepage.controller;

import org.healthiermo.homepage.SectionService;
import org.healthiermo.homepage.dto.SectionForm;
import org.healthiermo.homepage.enums.PagesEnum;
import org.healthiermo.homepage.enums.PieEnum;
import org.healthiermo.homepage.enums.SectionTitleEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping()
public class DisplayController {

    private final SectionService sectionService;

    public DisplayController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/panel")
    public String panelHome(Model model) {
        return "home";
    }

    @GetMapping("/panel/misc")
    public ModelAndView misc(Model model) {
        ModelAndView view = new ModelAndView("misc");
        view.addObject("pageTitle", "MISC Settings");
        view.addObject("pageShortName", "MISC");

        SectionForm miscForm = SectionForm.withKeys(SectionTitleEnum.OUTER_RING.name(),
                SectionTitleEnum.INNER_RING.name(), SectionTitleEnum.HELP.name());
        sectionService.loadSections(miscForm, "MISC");
        view.addObject("miscForm", miscForm);
        return view;
    }

    @GetMapping("/panel/outer")
    public ModelAndView outer(Model model) {
        ModelAndView view = new ModelAndView("outer");
        view.addObject("pageTitle", "Outer box settings");
        view.addObject("pageShortName", "OUTER");

        SectionForm outerForm = SectionForm.withKeys(
                PagesEnum.EMERGENCY.name(), PagesEnum.POLICY.name(),
                PagesEnum.COMMUNICATIONS.name(), PagesEnum.COMMUNITY.name(),
                PagesEnum.ORGANIZATION.name(), PagesEnum.ACCOUNTABILITY.name(),
                PagesEnum.ASSESSMENT.name());
        sectionService.loadSections(outerForm, "OUTER");

        view.addObject("outerForm", outerForm);
        return view;
    }

    @GetMapping("/panel/{editPage}")
    public ModelAndView panel(@PathVariable String editPage) {
        ModelAndView view = new ModelAndView("panel");

        PagesEnum page = null;
        try {
            page = PagesEnum.valueOf(editPage.toUpperCase());
        } catch (Exception e) {
        }

        if (page == null) {
            return new ModelAndView("404");
        }

        view.addObject("pageTitle", page.getFullName());
        view.addObject("pageShortName", editPage.toUpperCase());

        SectionForm configForm = SectionForm.withKeys(
                PieEnum.ENVIRONMENT.name(), PieEnum.COMMUNICABLE_DISEASE.name(),
                PieEnum.MATERNAL.name(), PieEnum.CHRONIC.name(),
                PieEnum.INJURY.name(), PieEnum.LINKAGE.name());
        sectionService.loadSections(configForm, editPage.toUpperCase());

        view.addObject("configForm", configForm);
        return view;
    }

}
