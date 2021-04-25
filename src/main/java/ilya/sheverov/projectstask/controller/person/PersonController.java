package ilya.sheverov.projectstask.controller.person;

import ilya.sheverov.projectstask.config.PersonViewsConfig;
import ilya.sheverov.projectstask.entity.Person;
import ilya.sheverov.projectstask.entity.presenter.PersonPresenter;
import ilya.sheverov.projectstask.factory.PersonModelFactory;
import ilya.sheverov.projectstask.model.PersonModel;
import ilya.sheverov.projectstask.validator.PersonValidator;
import ilya.sheverov.projectstask.wrapper.IncorrectFieldsBindingResultWrapper;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final PersonModelFactory factory;

    @Autowired
    public PersonController(PersonModelFactory factory) {
        this.factory = factory;
    }


    @GetMapping(value = "/all")
    public ModelAndView getPersonList(@RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "sortUsersList", defaultValue = "id") String order,
                                      @RequestParam(name = "selectPersons", defaultValue = "false", required = false)
                                          Boolean selectPersons, Locale locale, Model model) {
        PersonModel personModel = (PersonModel) model.getAttribute("model");

        if (personModel == null) {
            personModel = factory.getPersonModel(PersonViewsConfig.class, locale);
        }
        personModel.prepareAListOfPersons(page, order, selectPersons);
        return new ModelAndView("person-list", "model", personModel);
    }

    @GetMapping(value = "/create")
    public ModelAndView createPerson(Model model, Locale locale) {
        PersonModel personModel = (PersonModel) model.getAttribute("model");
        if (personModel == null) {
            personModel = factory.getPersonModel(PersonViewsConfig.class, locale);
        }
        return new ModelAndView("person-create", "model", personModel);
    }

    @InitBinder({"person"})
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new PersonValidator());
    }

    @PostMapping(value = "/create")
    public ModelAndView createPerson(@Valid Person person, BindingResult br,
                                     WebRequest webRequest, RedirectAttributes redirectAttributes, Locale locale) {
        PersonModel personModel = factory.getPersonModel(PersonViewsConfig.class, locale);
        if (br.hasErrors()) {
            IncorrectFieldsBindingResultWrapper incorrectFieldsBindingResultWrapper =
                new IncorrectFieldsBindingResultWrapper(br);
            Set<String> namesOfTheIncorrectFields = incorrectFieldsBindingResultWrapper.getNamesOfTheIncorrectFields();
            PersonPresenter personPresenter = createAPersonPresenterUsingTheWebRequestParameters(webRequest);
            personModel.setPersonPresenterWithInvalidFieldsNames(personPresenter, namesOfTheIncorrectFields);
            redirectAttributes.addFlashAttribute("model", personModel);
            return new ModelAndView(new RedirectView("/person/create", true));
        }
        personModel.createPerson(person);
        if (personModel.getHasOptimisticLockException()) {
            redirectAttributes.addFlashAttribute("model", personModel);
            return new ModelAndView(new RedirectView("/person/all", true));
        }
        redirectAttributes
            .addAttribute("lang", personModel.getLang())
            .addFlashAttribute("model", personModel);
        return new ModelAndView(new RedirectView("/person/all", true));
    }

    @GetMapping("/edit")
    public ModelAndView editPerson(@RequestParam(name = "id", required = false) Integer id,
                                   @RequestParam(required = false) Timestamp version,
                                   Model model, RedirectAttributes redirectAttributes, Locale locale) {
        PersonModel personModel = (PersonModel) model.getAttribute("model");
        if (personModel == null) {
            personModel = factory.getPersonModel(PersonViewsConfig.class, locale);
            personModel.findThePerson(id, version);
        }
        if (personModel.getHasOptimisticLockException()) {
            redirectAttributes
                .addAttribute("lang", personModel.getLang())
                .addFlashAttribute("model", personModel);
            return new ModelAndView(new RedirectView("/person/all", true));
        }
        return new ModelAndView("person-edit", "model", personModel);
    }

    @PostMapping("/edit")
    public ModelAndView editPerson(@Valid Person person, BindingResult br, WebRequest webRequest,
                                   RedirectAttributes redirectAttributes, Locale locale) {
        PersonModel personModel = factory.getPersonModel(PersonViewsConfig.class, locale);
        if (br.hasErrors()) {
            IncorrectFieldsBindingResultWrapper incorrectFieldsBindingResultWrapper =
                new IncorrectFieldsBindingResultWrapper(br);
            Set<String> namesOfTheIncorrectFields = incorrectFieldsBindingResultWrapper.getNamesOfTheIncorrectFields();
            PersonPresenter personPresenter = createAPersonPresenterUsingTheWebRequestParameters(webRequest);
            personModel.setPersonPresenterWithInvalidFieldsNames(personPresenter, namesOfTheIncorrectFields);
            redirectAttributes
                .addFlashAttribute("model", personModel)
                .addAttribute("id", person.getId())
                .addAttribute("version", person.getVersion());
            return new ModelAndView(new RedirectView("/person/edit", true));
        }
        personModel.editPerson(person);
        if (personModel.getHasOptimisticLockException()) {
            redirectAttributes.addFlashAttribute("model", personModel);
            return new ModelAndView(new RedirectView("/person/all", true));
        }
        redirectAttributes.addFlashAttribute("model", personModel);
        return new ModelAndView(new RedirectView("/person/all", true));
    }

    @GetMapping("/delete")
    public ModelAndView deletePerson(@RequestParam(name = "id") Integer id, @RequestParam Timestamp version,
                                     RedirectAttributes redirectAttributes, Locale locale) {
        PersonModel personModel = factory.getPersonModel(PersonViewsConfig.class, locale);
        personModel.deletePerson(id, version);
        if (personModel.getHasOptimisticLockException()) {
            redirectAttributes.addFlashAttribute("model", personModel);
            return new ModelAndView(new RedirectView("/person/all", true));
        }
        redirectAttributes.addFlashAttribute("model", personModel);
        return new ModelAndView("redirect:/person/all");
    }

    @PostMapping("/delete/selected")
    public ModelAndView deletePerson(WebRequest webRequest,
                                     RedirectAttributes redirectAttributes,
                                     @RequestParam(name = "page", defaultValue = "1") int page,
                                     @RequestParam(name = "sortUsersList", defaultValue = "id") String order,
                                     Locale locale) {
        String[] selectedPersons = webRequest.getParameterMap().get("selectedPerson");
        if (selectedPersons == null || selectedPersons.length == 0) {
            redirectAttributes
                .addAttribute("page", page)
                .addAttribute("sortUsersList", order)
                .addAttribute("selectPersons", true);
            return new ModelAndView(new RedirectView("/person/all", true));
        }
        List<Person> personList = new ArrayList<>();
        for (String p : selectedPersons) {
            String[] personProp = p.split(",");
            Person person = new Person();
            person.setId(Integer.valueOf(personProp[0]));
            person.setVersion(Timestamp.valueOf(personProp[1]));
            personList.add(person);
        }
        PersonModel personModel = factory.getPersonModel(PersonViewsConfig.class, locale);
        personModel.deletePersons(personList);
        if (personModel.getHasOptimisticLockException()) {
            redirectAttributes
                .addAttribute("page", page)
                .addAttribute("sortUsersList", order)
                .addFlashAttribute("model", personModel);
        }
        redirectAttributes
            .addAttribute("page", page)
            .addAttribute("sortTasksList", order)
            .addFlashAttribute("model", personModel);
        return new ModelAndView(new RedirectView("/person/all", true));
    }

    private PersonPresenter createAPersonPresenterUsingTheWebRequestParameters(WebRequest webRequest) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(PersonPresenter.class);
        PropertyValues propertyValues = new MutablePropertyValues(webRequest.getParameterMap());
        beanWrapper.setPropertyValues(propertyValues, true);
        return (PersonPresenter) beanWrapper.getWrappedInstance();
    }
}
