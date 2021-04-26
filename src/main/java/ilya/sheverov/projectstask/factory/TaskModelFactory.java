package ilya.sheverov.projectstask.factory;

import ilya.sheverov.projectstask.config.TaskViewsConfigI;
import ilya.sheverov.projectstask.entity.presenter.converter.TaskViewConverter;
import ilya.sheverov.projectstask.model.TaskModel;
import ilya.sheverov.projectstask.model.pagemanager.PageListService;
import ilya.sheverov.projectstask.service.TaskServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class TaskModelFactory {
    @Autowired
    AnnotationConfigWebApplicationContext context;

    @Autowired
    TaskServiceI taskService;
    @Autowired
    TaskViewConverter taskViewConverter;
    @Autowired
    private PageListService pageListService;

    public TaskModel getNewTaskModel(Class<? extends TaskViewsConfigI> aClass, Locale locale) {
        TaskViewsConfigI taskViewsConfig = context.getBean(aClass);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("locale/task-constants", locale);
        return new TaskModel(taskService, pageListService, resourceBundle, taskViewsConfig, taskViewConverter);
    }
}
