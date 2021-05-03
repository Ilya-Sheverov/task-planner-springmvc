package ilya.sheverov.projectstask.factory;

import ilya.sheverov.projectstask.config.TaskViewsConfigI;
import ilya.sheverov.projectstask.entity.LocalStatusValues;
import ilya.sheverov.projectstask.entity.StatusOfATask;
import ilya.sheverov.projectstask.entity.converter.TaskToTaskPresenterConverter;
import ilya.sheverov.projectstask.model.TaskModel;
import ilya.sheverov.projectstask.model.pagemanager.PageListService;
import ilya.sheverov.projectstask.service.TaskServiceI;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Component
public class TaskModelFactory {

    final AnnotationConfigWebApplicationContext context;

    final TaskServiceI taskService;

    final TaskToTaskPresenterConverter taskToTaskPresenterConverter;

    final PageListService pageListService;

    final LocalStatusValues localStatusValues;

    public TaskModelFactory(AnnotationConfigWebApplicationContext context, TaskServiceI taskService,
        TaskToTaskPresenterConverter taskToTaskPresenterConverter,
        PageListService pageListService,
        LocalStatusValues localStatusValues) {
        this.context = context;
        this.taskService = taskService;
        this.taskToTaskPresenterConverter = taskToTaskPresenterConverter;
        this.pageListService = pageListService;
        this.localStatusValues = localStatusValues;
    }

    public TaskModel getNewTaskModel(Class<? extends TaskViewsConfigI> aClass, Locale locale) {
        TaskViewsConfigI taskViewsConfig = context.getBean(aClass);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("locale/task-constants", locale);
        Map<Enum<StatusOfATask>, String> statusValues = localStatusValues.getListOfStatuses(locale);
        return new TaskModel(taskService, pageListService, resourceBundle, taskViewsConfig,
            taskToTaskPresenterConverter, statusValues);
    }
}
