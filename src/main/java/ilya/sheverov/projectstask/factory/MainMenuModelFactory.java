package ilya.sheverov.projectstask.factory;

import ilya.sheverov.projectstask.config.MainMenuViewConfigI;
import ilya.sheverov.projectstask.model.MainMenuModel;
import ilya.sheverov.projectstask.model.PersonModel;
import ilya.sheverov.projectstask.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MainMenuModelFactory {

    @Autowired
    private PersonModelFactory personModelFactory;

    @Autowired
    private TaskModelFactory taskModelFactory;

    public MainMenuModel getMainMenuModel(Class<? extends MainMenuViewConfigI> aClass, Locale locale) {
        PersonModel personModel = personModelFactory.getPersonModel(aClass, locale);
        TaskModel taskModel = taskModelFactory.getNewTaskModel(aClass, locale);
        return new MainMenuModel(personModel, taskModel);
    }
}
