package ilya.sheverov.projectstask.factory;

import ilya.sheverov.projectstask.config.TaskViewsConfigI;
import ilya.sheverov.projectstask.entity.converter.TaskToTaskPresenterConverter;
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

    final
    AnnotationConfigWebApplicationContext context;

    final
    TaskServiceI taskService;

    final
    TaskToTaskPresenterConverter taskToTaskPresenterConverter;

    private final PageListService pageListService;

    public TaskModelFactory(AnnotationConfigWebApplicationContext context, TaskServiceI taskService, TaskToTaskPresenterConverter taskToTaskPresenterConverter, PageListService pageListService) {
        this.context = context;
        this.taskService = taskService;
        this.taskToTaskPresenterConverter = taskToTaskPresenterConverter;
        this.pageListService = pageListService;
    }

    public TaskModel getNewTaskModel(Class<? extends TaskViewsConfigI> aClass, Locale locale) {
        TaskViewsConfigI taskViewsConfig = context.getBean(aClass);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("locale/task-constants", locale);
        return new TaskModel(taskService, pageListService, resourceBundle, taskViewsConfig, taskToTaskPresenterConverter);
    }
}
