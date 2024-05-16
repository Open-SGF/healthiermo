package org.healthiermo.homepage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DisplayController {

    @Autowired
    private TextBoxRepository textBoxRepository;

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

        System.out.println("Specific called");

        ModelAndView view = new ModelAndView("misc");
        view.addObject("pageTitle", "MISC Settings");
        view.addObject("pageShortName", "MISC");

        MiscForm miscForm = new MiscForm();

        TextBox outer = textBoxRepository.findById(new CompositeKey("MISC", "OUTER_RING")).orElse(new TextBox());
        miscForm.setOuterTitle(outer.getTitle());
        miscForm.setOuterText(outer.getText());

        TextBox inner = textBoxRepository.findById(new CompositeKey("MISC", "INNER_RING")).orElse(new TextBox());
        miscForm.setInnerTitle(inner.getTitle());
        miscForm.setInnerText(inner.getText());

        TextBox help = textBoxRepository.findById(new CompositeKey("MISC", "HELP")).orElse(new TextBox());
        miscForm.setHelpTitle(help.getTitle());
        miscForm.setHelpText(help.getText());

        view.addObject("miscForm", miscForm);

        return view;
    }

    @GetMapping("/panel/outer")
    public ModelAndView outer(Model model) {

        System.out.println("Outer called");

        ModelAndView view = new ModelAndView("outer");
        view.addObject("pageTitle", "Outer box settings");
        view.addObject("pageShortName", "OUTER");

        OuterForm outerForm = new OuterForm();

        TextBox emergency = textBoxRepository.findById(new CompositeKey("OUTER", Pages.EMERGENCY.name())).orElse(new TextBox());
        outerForm.setEmergencyText(emergency.getText());
        outerForm.setEmergencyTitle(emergency.getTitle());

        TextBox policy = textBoxRepository.findById(new CompositeKey("OUTER", Pages.POLICY.name())).orElse(new TextBox());
        outerForm.setPolicyText(policy.getText());
        outerForm.setPolicyTitle(policy.getTitle());

        TextBox communications = textBoxRepository.findById(new CompositeKey("OUTER", Pages.COMMUNICATIONS.name())).orElse(new TextBox());
        outerForm.setCommunicationsText(communications.getText());
        outerForm.setCommunicationsTitle(communications.getTitle());

        TextBox community = textBoxRepository.findById(new CompositeKey("OUTER", Pages.COMMUNITY.name())).orElse(new TextBox());
        outerForm.setCommunityText(community.getText());
        outerForm.setCommunityTitle(community.getTitle());

        TextBox organization = textBoxRepository.findById(new CompositeKey("OUTER", Pages.ORGANIZATION.name())).orElse(new TextBox());
        outerForm.setOrganizationalText(organization.getText());
        outerForm.setOrganizationalTitle(organization.getTitle());

        TextBox accountability = textBoxRepository.findById(new CompositeKey("OUTER", Pages.ACCOUNTABILITY.name())).orElse(new TextBox());
        outerForm.setAccountabilityText(accountability.getText());
        outerForm.setAccountabilityTitle(accountability.getTitle());

        TextBox assessment = textBoxRepository.findById(new CompositeKey("OUTER", Pages.ASSESSMENT.name())).orElse(new TextBox());
        outerForm.setAssessmentText(assessment.getText());
        outerForm.setAssessmentTitle(assessment.getTitle());

        view.addObject("outerForm", outerForm);

        return view;
    }

    @GetMapping("/panel/{editPage}")
    public ModelAndView panel(@PathVariable String editPage) {
        ModelAndView view = new ModelAndView("panel");
        System.out.println("Page path:" + editPage);

        Pages page = null;
        try {
            page = Pages.valueOf(editPage.toUpperCase());
        } catch(Exception e){

        }

        if(page == null) { //Bad page?
            return new ModelAndView("404");
        }

        view.addObject("pageTitle", page.getFullName());
        view.addObject("pageShortName", editPage.toUpperCase());

        ConfigurationForm filledOutForm = new ConfigurationForm();

        TextBox env = textBoxRepository.findById(new CompositeKey(editPage.toUpperCase(), Pie.ENVIRONMENT.name())).orElse(new TextBox());
        filledOutForm.setEnvTitle(env.getTitle());
        filledOutForm.setEnvText(env.getText());

        TextBox communicable = textBoxRepository.findById(new CompositeKey(editPage.toUpperCase(), Pie.COMMUNICABLE_DISEASE.name())).orElse(new TextBox());
        filledOutForm.setCommunicableDiseaseTitle(communicable.getTitle());
        filledOutForm.setCommunicableDiseaseText(communicable.getText());

        TextBox chronic = textBoxRepository.findById(new CompositeKey(editPage.toUpperCase(), Pie.CHRONIC.name())).orElse(new TextBox());
        filledOutForm.setChronicTitle(chronic.getTitle());
        filledOutForm.setChronicText(chronic.getText());

        TextBox linkage = textBoxRepository.findById(new CompositeKey(editPage.toUpperCase(), Pie.LINKAGE.name())).orElse(new TextBox());
        filledOutForm.setLinkageTitle(linkage.getTitle());
        filledOutForm.setLinkageText(linkage.getText());

        TextBox injury = textBoxRepository.findById(new CompositeKey(editPage.toUpperCase(), Pie.INJURY.name())).orElse(new TextBox());
        filledOutForm.setInjuryTitle(injury.getTitle());
        filledOutForm.setInjuryText(injury.getText());

        TextBox maternal = textBoxRepository.findById(new CompositeKey(editPage.toUpperCase(), Pie.MATERNAL.name())).orElse(new TextBox());
        filledOutForm.setMaternalTitle(maternal.getTitle());
        filledOutForm.setMaternalText(maternal.getText());

        view.addObject("configForm", filledOutForm);

        return view;
    }
}