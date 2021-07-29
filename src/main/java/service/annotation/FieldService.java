package service.annotation;

import annotations.Column;
import annotations.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class FieldService {

    /**
     *
     * @param o Обьект
     * @param fieldName имя поля из аннотации (Column name)
     * @param fieldValue значение из бд
     * @param isId для присвоения Id
     */
    public void setFieldByName(Object o, String fieldName, String fieldValue, boolean isId) {

        try {
            Field[] declaredFields = o.getClass().getDeclaredFields();
            Field declaredField;
            if (isId) {
                declaredField= getIdField(declaredFields);
            }
            else {
                 declaredField = getFieldDeclaredAnnotationColumnWithName(declaredFields, fieldName);
            }
            declaredField.setAccessible(true);
            Class<?> type = declaredField.getType();
            if (String.class.equals(type)) {
                declaredField.set(o, fieldValue);
            } else if (Integer.class.equals(type)) {
                declaredField.set(o, Integer.parseInt(fieldValue));
            } else if (Long.class.equals(type)) {
                declaredField.set(o, Long.parseLong(fieldValue));
            } else if (Float.class.equals(type)) {
                declaredField.set(o, Float.parseFloat(fieldValue));
            } else if (Double.class.equals(type)) {
                declaredField.set(o, Double.parseDouble(fieldValue));
            } else if (Short.class.equals(type)) {
                declaredField.set(o, Short.parseShort(fieldValue));
            } else if (Byte.class.equals(type)) {
                declaredField.set(o, Byte.parseByte(fieldValue));
            } else if (Boolean.class.equals(type)) {
                declaredField.set(o, Boolean.parseBoolean(fieldValue));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * По имени Column name получаем field
     * @param fields
     * @param name
     * @return
     * @throws NoSuchFieldException
     */
    public Field getFieldDeclaredAnnotationColumnWithName(Field[] fields, String name) throws NoSuchFieldException {
        for (Field field : fields) {
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
            for (Annotation declaredAnnotation : declaredAnnotations) {
                if (declaredAnnotation instanceof Column) {
                    String columnName = ((Column) declaredAnnotation).name();
                    if (columnName.equals(name)) {
                        return field;
                    }
                }
            }
        }
        throw new NoSuchFieldException();
    }

    /**
     * Метод для поиска Id
     * @param fields
     * @return
     * @throws NoSuchFieldException
     */
    public Field getIdField(Field[] fields) throws NoSuchFieldException {
        for (Field field : fields) {
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
            for (Annotation declaredAnnotation : declaredAnnotations) {
                if (declaredAnnotation instanceof Id) {
                   return field;
                }
            }
        }
        throw new NoSuchFieldException();
    }

    public void setFieldValue(Field field, Object value,Object object){
        field.setAccessible(true);
        try {
            field.set(object,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
