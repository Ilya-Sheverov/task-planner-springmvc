package ilya.sheverov.projectstask.filter;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class ParamLocalResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String langValue = request.getParameter("lang");
        System.out.println("langValue = " + langValue);
        if (langValue == null) {
            return Locale.ENGLISH;
        }
        if (langValue.equals("en")) {
            return Locale.ENGLISH;
        } else if (langValue.equals("ru")) {
            return new Locale("ru");
        } else {
            return Locale.ENGLISH;
        }
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
