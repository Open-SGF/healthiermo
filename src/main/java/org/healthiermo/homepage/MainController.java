package org.healthiermo.homepage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.healthiermo.homepage.util.FileUtil;
import org.healthiermo.homepage.util.InsertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * There is definitely a better way to model the repetive nature of the text boxes.
 * However, I am not able to figure that out with my lack of spring knowledge.
 * However, this works, and its straight forward.
 * <p>
 * This class represents the entire submitted form when a user hit "submit" on the panel.
 * We translate the page they submitted for
 */
class ConfigurationForm {

    //Environmental Public health
    private String envText;
    private String envTitle;
    private MultipartFile envFile;

    //Communicable Disease Control
    private String communicableDiseaseText;
    private String communicableDiseaseTitle;
    private MultipartFile communicableDiseaseFile;

    //Maternal, Child, and Family Health
    private String maternalText;
    private String maternalTitle;
    private MultipartFile maternalFile;

    //Chronic Disease Prevention
    private String chronicText;
    private String chronicTitle;
    private MultipartFile chronicFile;

    //Injury prevention
    private String injuryText;
    private String injuryTitle;
    private MultipartFile injuryFile;

    //Linkage to medical behavior and community resources
    private String linkageText;
    private String linkageTitle;
    private MultipartFile linkageFile;

    // I'm sorry.
    public ConfigurationForm(String envText, String envTitle, MultipartFile envFile,
                             String communicableDiseaseText, String communicableDiseaseTitle, MultipartFile communicableDiseaseFile,
                             String maternalText, String maternalTitle, MultipartFile maternalFile,
                             String chronicText, String chronicTitle, MultipartFile chronicFile,
                             String injuryText, String injuryTitle, MultipartFile injuryFile,
                             String linkageText, String linkageTitle, MultipartFile linkageFile) {
        this.envText = envText;
        this.envTitle = envTitle;
        this.envFile = envFile;
        this.communicableDiseaseText = communicableDiseaseText;
        this.communicableDiseaseTitle = communicableDiseaseTitle;
        this.communicableDiseaseFile = communicableDiseaseFile;
        this.maternalText = maternalText;
        this.maternalTitle = maternalTitle;
        this.maternalFile = maternalFile;
        this.chronicText = chronicText;
        this.chronicTitle = chronicTitle;
        this.chronicFile = chronicFile;
        this.injuryText = injuryText;
        this.injuryTitle = injuryTitle;
        this.injuryFile = injuryFile;
        this.linkageText = linkageText;
        this.linkageTitle = linkageTitle;
        this.linkageFile = linkageFile;
    }

    public ConfigurationForm() {
    }

    public String getEnvText() {
        return envText;
    }

    public void setEnvText(String envText) {
        this.envText = envText;
    }

    public String getEnvTitle() {
        return envTitle;
    }

    public void setEnvTitle(String envTitle) {
        this.envTitle = envTitle;
    }

    public MultipartFile getEnvFile() {
        return envFile;
    }

    public void setEnvFile(MultipartFile envFile) {
        this.envFile = envFile;
    }

    public String getCommunicableDiseaseText() {
        return communicableDiseaseText;
    }

    public void setCommunicableDiseaseText(String communicableDiseaseText) {
        this.communicableDiseaseText = communicableDiseaseText;
    }

    public String getCommunicableDiseaseTitle() {
        return communicableDiseaseTitle;
    }

    public void setCommunicableDiseaseTitle(String communicableDiseaseTitle) {
        this.communicableDiseaseTitle = communicableDiseaseTitle;
    }

    public MultipartFile getCommunicableDiseaseFile() {
        return communicableDiseaseFile;
    }

    public void setCommunicableDiseaseFile(MultipartFile communicableDiseaseFile) {
        this.communicableDiseaseFile = communicableDiseaseFile;
    }

    public String getMaternalText() {
        return maternalText;
    }

    public void setMaternalText(String maternalText) {
        this.maternalText = maternalText;
    }

    public String getMaternalTitle() {
        return maternalTitle;
    }

    public void setMaternalTitle(String maternalTitle) {
        this.maternalTitle = maternalTitle;
    }

    public MultipartFile getMaternalFile() {
        return maternalFile;
    }

    public void setMaternalFile(MultipartFile maternalFile) {
        this.maternalFile = maternalFile;
    }

    public String getChronicText() {
        return chronicText;
    }

    public void setChronicText(String chronicText) {
        this.chronicText = chronicText;
    }

    public String getChronicTitle() {
        return chronicTitle;
    }

    public void setChronicTitle(String chronicTitle) {
        this.chronicTitle = chronicTitle;
    }

    public MultipartFile getChronicFile() {
        return chronicFile;
    }

    public void setChronicFile(MultipartFile chronicFile) {
        this.chronicFile = chronicFile;
    }

    public String getInjuryText() {
        return injuryText;
    }

    public void setInjuryText(String injuryText) {
        this.injuryText = injuryText;
    }

    public String getInjuryTitle() {
        return injuryTitle;
    }

    public void setInjuryTitle(String injuryTitle) {
        this.injuryTitle = injuryTitle;
    }

    public MultipartFile getInjuryFile() {
        return injuryFile;
    }

    public void setInjuryFile(MultipartFile injuryFile) {
        this.injuryFile = injuryFile;
    }

    public String getLinkageText() {
        return linkageText;
    }

    public void setLinkageText(String linkageText) {
        this.linkageText = linkageText;
    }

    public String getLinkageTitle() {
        return linkageTitle;
    }

    public void setLinkageTitle(String linkageTitle) {
        this.linkageTitle = linkageTitle;
    }

    public MultipartFile getLinkageFile() {
        return linkageFile;
    }

    public void setLinkageFile(MultipartFile linkageFile) {
        this.linkageFile = linkageFile;
    }

    @Override
    public String toString() {
        return "ConfigurationForm{" +
                "envText='" + envText + '\'' +
                ", envTitle='" + envTitle + '\'' +
                ", envFile=" + envFile +
                ", communicableDiseaseText='" + communicableDiseaseText + '\'' +
                ", communicableDiseaseTitle='" + communicableDiseaseTitle + '\'' +
                ", communicableDiseaseFile=" + communicableDiseaseFile +
                ", maternalText='" + maternalText + '\'' +
                ", maternalTitle='" + maternalTitle + '\'' +
                ", maternalFile=" + maternalFile +
                ", chronicText='" + chronicText + '\'' +
                ", chronicTitle='" + chronicTitle + '\'' +
                ", chronicFile=" + chronicFile +
                ", injuryText='" + injuryText + '\'' +
                ", injuryTitle='" + injuryTitle + '\'' +
                ", injuryFile=" + injuryFile +
                ", linkageText='" + linkageText + '\'' +
                ", linkageTitle='" + linkageTitle + '\'' +
                ", linkageFile=" + linkageFile +
                '}';
    }
}

class MiscForm {
    private String outerTitle;
    private String outerText;
    private MultipartFile outerFile;

    private String innerTitle;
    private String innerText;
    private MultipartFile innerFile;

    private String helpTitle;
    private String helpText;
    private MultipartFile helpAudio;

    public String getOuterTitle() {
        return outerTitle;
    }

    public void setOuterTitle(String outerTitle) {
        this.outerTitle = outerTitle;
    }

    public String getOuterText() {
        return outerText;
    }

    public void setOuterText(String outerText) {
        this.outerText = outerText;
    }

    public MultipartFile getOuterFile() {
        return outerFile;
    }

    public void setOuterFile(MultipartFile outerFile) {
        this.outerFile = outerFile;
    }

    public String getInnerTitle() {
        return innerTitle;
    }

    public void setInnerTitle(String innerTitle) {
        this.innerTitle = innerTitle;
    }

    public String getInnerText() {
        return innerText;
    }

    public void setInnerText(String innerText) {
        this.innerText = innerText;
    }

    public MultipartFile getInnerFile() {
        return innerFile;
    }

    public void setInnerFile(MultipartFile innerFile) {
        this.innerFile = innerFile;
    }

    public String getHelpTitle() {
        return helpTitle;
    }

    public void setHelpTitle(String helpTitle) {
        this.helpTitle = helpTitle;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public MultipartFile getHelpAudio() {
        return helpAudio;
    }

    public void setHelpAudio(MultipartFile helpAudio) {
        this.helpAudio = helpAudio;
    }
}


class OuterForm {
    private String emergencyText;
    private String emergencyTitle;
    private MultipartFile emergencyFile;

    private String policyText;
    private String policyTitle;
    private MultipartFile policyFile;

    private String communicationsText;
    private String communicationsTitle;
    private MultipartFile communicationsFile;

    private String communityText;
    private String communityTitle;
    private MultipartFile communityFile;

    private String organizationalText;
    private String organizationalTitle;
    private MultipartFile organizationalFile;

    private String accountabilityText;
    private String accountabilityTitle;
    private MultipartFile accountabilityFile;

    private String assessmentText;
    private String assessmentTitle;
    private MultipartFile assessmentFile;

    public String getEmergencyText() {
        return emergencyText;
    }

    public void setEmergencyText(String emergencyText) {
        this.emergencyText = emergencyText;
    }

    public String getEmergencyTitle() {
        return emergencyTitle;
    }

    public void setEmergencyTitle(String emergencyTitle) {
        this.emergencyTitle = emergencyTitle;
    }

    public MultipartFile getEmergencyFile() {
        return emergencyFile;
    }

    public void setEmergencyFile(MultipartFile emergencyFile) {
        this.emergencyFile = emergencyFile;
    }

    public String getPolicyText() {
        return policyText;
    }

    public void setPolicyText(String policyText) {
        this.policyText = policyText;
    }

    public String getPolicyTitle() {
        return policyTitle;
    }

    public void setPolicyTitle(String policyTitle) {
        this.policyTitle = policyTitle;
    }

    public MultipartFile getPolicyFile() {
        return policyFile;
    }

    public void setPolicyFile(MultipartFile policyFile) {
        this.policyFile = policyFile;
    }

    public String getCommunicationsText() {
        return communicationsText;
    }

    public void setCommunicationsText(String communicationsText) {
        this.communicationsText = communicationsText;
    }

    public String getCommunicationsTitle() {
        return communicationsTitle;
    }

    public void setCommunicationsTitle(String communicationsTitle) {
        this.communicationsTitle = communicationsTitle;
    }

    public MultipartFile getCommunicationsFile() {
        return communicationsFile;
    }

    public void setCommunicationsFile(MultipartFile communicationsFile) {
        this.communicationsFile = communicationsFile;
    }

    public String getCommunityText() {
        return communityText;
    }

    public void setCommunityText(String communityText) {
        this.communityText = communityText;
    }

    public String getCommunityTitle() {
        return communityTitle;
    }

    public void setCommunityTitle(String communityTitle) {
        this.communityTitle = communityTitle;
    }

    public MultipartFile getCommunityFile() {
        return communityFile;
    }

    public void setCommunityFile(MultipartFile communityFile) {
        this.communityFile = communityFile;
    }

    public String getOrganizationalText() {
        return organizationalText;
    }

    public void setOrganizationalText(String organizationalText) {
        this.organizationalText = organizationalText;
    }

    public String getOrganizationalTitle() {
        return organizationalTitle;
    }

    public void setOrganizationalTitle(String organizationalTitle) {
        this.organizationalTitle = organizationalTitle;
    }

    public MultipartFile getOrganizationalFile() {
        return organizationalFile;
    }

    public void setOrganizationalFile(MultipartFile organizationalFile) {
        this.organizationalFile = organizationalFile;
    }

    public String getAccountabilityText() {
        return accountabilityText;
    }

    public void setAccountabilityText(String accountabilityText) {
        this.accountabilityText = accountabilityText;
    }

    public String getAccountabilityTitle() {
        return accountabilityTitle;
    }

    public void setAccountabilityTitle(String accountabilityTitle) {
        this.accountabilityTitle = accountabilityTitle;
    }

    public MultipartFile getAccountabilityFile() {
        return accountabilityFile;
    }

    public void setAccountabilityFile(MultipartFile accountabilityFile) {
        this.accountabilityFile = accountabilityFile;
    }

    public String getAssessmentText() {
        return assessmentText;
    }

    public void setAssessmentText(String assessmentText) {
        this.assessmentText = assessmentText;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public MultipartFile getAssessmentFile() {
        return assessmentFile;
    }

    public void setAssessmentFile(MultipartFile assessmentFile) {
        this.assessmentFile = assessmentFile;
    }
}


@RestController
public class MainController {
    @Autowired
    private TextBoxRepository textBoxRepository;

    //https://www.baeldung.com/spring-mvc-and-the-modelattribute-annotation
    //https://www.baeldung.com/spring-file-upload
    @PostMapping("/save/{page}")
    public ModelAndView submit(@ModelAttribute("configForm") ConfigurationForm configForm, @PathVariable String page, ModelMap modelMap) {
        System.out.println("Submit called!");
        System.out.println("Path called " + page);
        //TODO: Check if legitiment path?

        page = page.toUpperCase();

        //Insert new information about text into table.
        //Note: Page in this context is the same thing as a box.
        InsertUtil.insert(textBoxRepository, Pages.valueOf(page).toString(), "COMMUNICABLE_DISEASE", configForm.getCommunicableDiseaseTitle(), configForm.getCommunicableDiseaseText());
        InsertUtil.insert(textBoxRepository, Pages.valueOf(page).toString(), "ENVIRONMENT", configForm.getEnvTitle(), configForm.getEnvText());
        InsertUtil.insert(textBoxRepository, Pages.valueOf(page).toString(), "LINKAGE", configForm.getLinkageTitle(), configForm.getLinkageText());
        InsertUtil.insert(textBoxRepository, Pages.valueOf(page).toString(), "INJURY", configForm.getInjuryTitle(), configForm.getInjuryText());
        InsertUtil.insert(textBoxRepository, Pages.valueOf(page).toString(), "MATERNAL", configForm.getMaternalTitle(), configForm.getMaternalText());
        InsertUtil.insert(textBoxRepository, Pages.valueOf(page).toString(), "CHRONIC", configForm.getChronicTitle(), configForm.getChronicText());

        //Save all of our files for each form.
        FileUtil.handleFile(configForm.getCommunicableDiseaseFile(), page, "COMMUNICABLE_DISEASE");
        FileUtil.handleFile(configForm.getEnvFile(), page, "ENVIRONMENT");
        FileUtil.handleFile(configForm.getLinkageFile(), page, "LINKAGE");
        FileUtil.handleFile(configForm.getInjuryFile(), page, "INJURY");
        FileUtil.handleFile(configForm.getMaternalFile(), page, "MATERNAL");
        FileUtil.handleFile(configForm.getChronicFile(), page, "CHRONIC");

        ModelAndView view = new ModelAndView("panel");

        view.addObject("configForm", configForm);
        view.addObject("pageShortName", page);
        view.addObject("pageTitle", Pages.valueOf(page).getFullName());

        return view;
    }

    @PostMapping("/save/outer")
    public ModelAndView submitOuter(@ModelAttribute("outerForm") OuterForm outerForm, ModelMap modelMap) {
        System.out.println("Submit called!");

        //Insert new information about text into table.
        //Note: Page in this context is the same thing as a box.
        InsertUtil.insert(textBoxRepository, Pages.valueOf("OUTER").toString(), "EMERGENCY", outerForm.getEmergencyTitle(), outerForm.getEmergencyText());
        InsertUtil.insert(textBoxRepository, Pages.valueOf("OUTER").toString(), "POLICY", outerForm.getPolicyTitle(), outerForm.getPolicyText());
        InsertUtil.insert(textBoxRepository, Pages.valueOf("OUTER").toString(), "COMMUNICATIONS", outerForm.getCommunicationsTitle(), outerForm.getCommunicationsText());
        InsertUtil.insert(textBoxRepository, Pages.valueOf("OUTER").toString(), "COMMUNITY", outerForm.getCommunityTitle(), outerForm.getCommunityText());
        InsertUtil.insert(textBoxRepository, Pages.valueOf("OUTER").toString(), "ORGANIZATION", outerForm.getOrganizationalTitle(), outerForm.getOrganizationalText());
        InsertUtil.insert(textBoxRepository, Pages.valueOf("OUTER").toString(), "ACCOUNTABILITY", outerForm.getAccountabilityTitle(), outerForm.getAccountabilityText());
        InsertUtil.insert(textBoxRepository, Pages.valueOf("OUTER").toString(), "ASSESSMENT", outerForm.getAssessmentTitle(), outerForm.getAssessmentText());

        //Save all of our files for each form.
        FileUtil.handleFile(outerForm.getEmergencyFile(), "OUTER", "EMERGENCY");
        FileUtil.handleFile(outerForm.getPolicyFile(), "OUTER", "POLICY");
        FileUtil.handleFile(outerForm.getCommunicationsFile(), "OUTER", "COMMUNICATIONS");
        FileUtil.handleFile(outerForm.getCommunityFile(), "OUTER", "COMMUNITY");
        FileUtil.handleFile(outerForm.getOrganizationalFile(), "OUTER", "ORGANIZATION");
        FileUtil.handleFile(outerForm.getAccountabilityFile(), "OUTER", "ACCOUNTABILITY");
        FileUtil.handleFile(outerForm.getAssessmentFile(), "OUTER", "ASSESSMENT");

        ModelAndView view = new ModelAndView("outer");

        view.addObject("outerForm", outerForm);
        view.addObject("pageShortName", "OUTER");
        view.addObject("pageTitle", Pages.valueOf("OUTER").getFullName());

        return view;
    }

    @PostMapping("/save/misc")
    public ModelAndView submitMisc(@ModelAttribute("configForm") MiscForm miscForm, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("misc");

        InsertUtil.insert(textBoxRepository, "MISC",
                "OUTER_RING", miscForm.getOuterTitle(), miscForm.getOuterText());
        InsertUtil.insert(textBoxRepository, "MISC",
                "INNER_RING", miscForm.getInnerTitle(), miscForm.getInnerText());
        InsertUtil.insert(textBoxRepository, "MISC",
                "HELP", miscForm.getHelpTitle(), miscForm.getHelpText());

        FileUtil.handleFile(miscForm.getOuterFile(), "MISC", "OUTER_RING");
        FileUtil.handleFile(miscForm.getInnerFile(), "MISC", "INNER_RING");
        FileUtil.handleFile(miscForm.getHelpAudio(), "MISC", "HELP");

        view.addObject("miscForm", miscForm);
        view.addObject("pageShortName", "misc");
        view.addObject("pageTitle", "Misc Settings");

        return view;
    }

    @GetMapping("/text")
    public String getText() {

        List<TextBox> list = new ArrayList<>();
        Iterator<TextBox> iter = textBoxRepository.findAll().iterator();
        iter.forEachRemaining(list::add);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
