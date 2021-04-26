package ilya.sheverov.projectstask.factory;

import ilya.sheverov.projectstask.config.PersonViewsConfigI;
import ilya.sheverov.projectstask.entity.converter.PersonToPersonPresenterConverter;
import ilya.sheverov.projectstask.model.PersonModel;
import ilya.sheverov.projectstask.model.pagemanager.PageListService;
import ilya.sheverov.projectstask.service.PersonServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class PersonModelFactory {
    @Autowired
    AnnotationConfigWebApplicationContext context;

    @Autowired
    private PersonServiceI personServiceI;

    @Autowired
    private PageListService pageListService;

    @Autowired
    private PersonToPersonPresenterConverter converter;

    public PersonModel getPersonModel(Class<? extends PersonViewsConfigI> aClass, Locale locale) {
        PersonViewsConfigI viewsConfig = context.getBean(aClass);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("locale/person-constants", locale);
        return new PersonModel(personServiceI, pageListService, resourceBundle, viewsConfig, converter);
    }
}
