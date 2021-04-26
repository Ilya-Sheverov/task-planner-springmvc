package ilya.sheverov.projectstask.entity.converter;

import ilya.sheverov.projectstask.exception.EntityHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Содержит метод, позволяющий преобразовывать Map<String, String>, keys которой являются название полей объекта, а
 * values, значения этих полей.
 * <p>
 * Что бы возможно было создать объект, у него должен быть конструктор по умолчанию (без аргументов) и setter методы, для
 * полей, которым необходимо присвоить значение, которое берется из передаваемой Map.
 */
@Component("entityHandler")
@Scope("singleton")
public class EntityHandler {
    public static Logger logger = LoggerFactory.getLogger(EntityHandler.class.getName());

    public <T> T getEntityHandler(Class<?> aClass, Map<String, String[]> entityFields) throws EntityHandlerException {
        try {
            final Object entity = aClass.getConstructor().newInstance();
            Field[] fields = aClass.getDeclaredFields();
            Method[] methods = aClass.getDeclaredMethods();
            for (Field field : fields) {
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();
                String setterMethodName = setterMethodName(fieldName);
                if (entityFields.containsKey(fieldName)) {
                    Predicate<Method> setMethodPredicate = method -> {
                        if (method.getName().equals(setterMethodName)) {
                            if (method.getParameterCount() == 1) {
                                if (method.getParameterTypes()[0].isAssignableFrom(fieldType)) {
                                  return method.canAccess(entity);
                                }
                            }
                        }
                        return false;
                    };
                    Optional<Method> methodOptional = Arrays.stream(methods).filter(setMethodPredicate).findFirst();
                    if (methodOptional.isPresent()) {
                        Method setMethod = methodOptional.get();
                        Object paramValue;
                        String[] paramValueAsString = entityFields.get(fieldName);
                        try {
                            paramValue = convertTheParameterToTheCorrectType(paramValueAsString, fieldType);
                        } catch (EntityHandlerException e) {
                            e.setIllegalArgument(fieldName, paramValueAsString);
                            logger.error("Не удалось преобразовать поле {} со значением {} к типу {} из-за не верного формата.",
                                fieldName, paramValueAsString, fieldType.getName());
                            throw e;
                        }
                        try {
                            setMethod.invoke(entity, paramValue);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            EntityHandlerException entityHandlerException = new EntityHandlerException(e);
                            logger.error("Не удалось выполнить метод {}, так как произошла ошибка:\n",
                                setMethod.getName(), entityHandlerException);
                            throw entityHandlerException;
                        }
                    }
                }
            }
            return (T) entity;
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            EntityHandlerException entityHandlerException = new EntityHandlerException(e);
            logger.error("Не удалось создать объект типа {}, так как произошла ошибка:\n", aClass.getName(), e);
            throw entityHandlerException;
        }
    }

    /**
     * Возвращает название set метода для поля. Например, если название поля "name", то
     * ввернет название метода "setName".
     *
     * @param fieldName название поля, для которого генерируется название set меода.
     * @return название set метода.
     */
    private String setterMethodName(String fieldName) {
        StringBuilder sb = new StringBuilder("set")
            .append(fieldName.substring(0, 1).toUpperCase())
            .append(fieldName.substring(1));
        return sb.toString();
    }

    /**
     * Преобразовывает строку к типу, переданному в параметре.
     *
     * @param parameter строка, которую необходимо преобразовать к другому типу.
     * @param reqType   класс типа, к которому надо преобразовать строку.
     * @param <T>       тип, к которому необходимо преобразовать строку.
     * @return объекта типа <T>.
     * @throws EntityHandlerException возникает, если тип объекта, к которому надо преобразовать строку не был
     *                                предусмотрен, или формат строки не правильный для преобразования к нужному типу.
     */
    private <T> T convertTheParameterToTheCorrectType(String[] parameter, Class<T> reqType)
        throws EntityHandlerException {
        Object paramValue = null;
        switch (reqType.getName()) {
            case "byte":
            case "java.lang.Byte":
                try {
                    paramValue = Byte.valueOf(parameter[0]);
                } catch (NumberFormatException e) {
                    EntityHandlerException entityHandlerException = new EntityHandlerException(e);
                    logger.error("Не удалось преобразовать строку \"{}\" к типу {}, из-за неверного формата.",
                        parameter[0], reqType.getName(), entityHandlerException);
                    throw entityHandlerException;
                }
                break;
            case "int":
            case "java.lang.Integer":
                try {
                    paramValue = Integer.valueOf(parameter[0]);
                } catch (NumberFormatException e) {
                    EntityHandlerException entityHandlerException = new EntityHandlerException(e);
                    logger.error("Не удалось преобразовать строку \"{}\" к типу {}, из-за неверного формата.",
                        parameter[0], reqType.getName(), entityHandlerException);
                    throw entityHandlerException;
                }
                break;
            case "long":
            case "java.lang.Long":
                try {
                    paramValue = Long.valueOf(parameter[0]);
                } catch (NumberFormatException e) {
                    EntityHandlerException entityHandlerException = new EntityHandlerException(e);
                    logger.error("Не удалось преобразовать строку \"{}\" к типу {}, из-за неверного формата.",
                        parameter[0], reqType.getName(), entityHandlerException);
                    throw entityHandlerException;
                }
                break;
            case "float":
            case "java.lang.Float":
                try {
                    paramValue = Float.valueOf(parameter[0]);
                } catch (NumberFormatException e) {
                    EntityHandlerException entityHandlerException = new EntityHandlerException(e);
                    logger.error("Не удалось преобразовать строку \"{}\" к типу {}, из-за неверного формата.",
                        parameter[0], reqType.getName(), entityHandlerException);
                    throw entityHandlerException;
                }
                break;
            case "double":
            case "java.lang.Double":
                try {
                    paramValue = Double.valueOf(parameter[0]);
                } catch (NumberFormatException e) {
                    EntityHandlerException entityHandlerException = new EntityHandlerException(e);
                    logger.error("Не удалось преобразовать строку \"{}\" к типу {}, из-за неверного формата.",
                        parameter[0], reqType.getName(), entityHandlerException);
                    throw entityHandlerException;
                }
                break;
            case "java.lang.String":
                paramValue = parameter[0];
                break;
            case "java.sql.Timestamp":
                try {
                    String s = parameter[0];
                    if (s.isEmpty()) {
                        paramValue = null;
                    } else {
                        if (s.matches("^((\\d{4}-\\d{2}-\\d{2})( )(\\d{2}:\\d{2}:\\d{2})(.(\\d)*)?)")) {
                            paramValue = Timestamp.valueOf(s);
                        } else {
                            s = s.replace("T", " ") + ":00";
                            paramValue = Timestamp.valueOf(s);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    EntityHandlerException entityHandlerException = new EntityHandlerException(e);
                    logger.error("Не удалось преобразовать строку \"{}\" к типу {}, из-за неверного формата.",
                        parameter[0], reqType.getName(), entityHandlerException);
                    throw entityHandlerException;
                }
                break;
            default:
                EntityHandlerException entityHandlerException =
                    new EntityHandlerException("The type of the variable is not found: " + reqType);
                logger.error("Не удалось преобразовать строку \"{}\" к типу {}, так как он не поддерживается.", parameter[0], reqType.getName());
                throw entityHandlerException;
        }
        return (T) paramValue;
    }
}
