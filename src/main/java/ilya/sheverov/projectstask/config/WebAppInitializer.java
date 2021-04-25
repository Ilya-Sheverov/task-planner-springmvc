package ilya.sheverov.projectstask.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext root =
            new AnnotationConfigWebApplicationContext();
        root.register(WebConfig.class);
        root.register(DataBaseConfig.class);
        root.scan("ilya.sheverov.projectstask");
        root.setServletContext(servletContext);
        root.refresh();
        ServletRegistration.Dynamic ds = servletContext.addServlet("dispatcher", new DispatcherServlet(root));
        ds.setLoadOnStartup(1);
        ds.addMapping("/");
    }

}
