package service.annotation;

import annotations.Column;
import data.FieldMap;
import data.FieldMapWithoutValue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AnnotationFieldService
{

    /**
     * @param o          - Объект с которым мы работает
     * @param annotation Аннотация, по которой мы будем вытаскивать значение переменной
     * @return значение переменной, которая аннотирована "@param annotation"
     */
    public Object findAnnotationField(Object o, Class<?> annotation) {
        Field[] fields = o.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Annotation[] declaredAnnotations = fields[i].getDeclaredAnnotations();
            for (int j = 0; j < declaredAnnotations.length; j++) {
                if (annotation.isAssignableFrom(declaredAnnotations[j].getClass())) {
                    try {
                        Field field = fields[i];
                        field.setAccessible(true);
                        return field.get(o);
                    } catch (IllegalAccessException e) {
                        return null;//TODO добавить кастомную ошибку
                    }
                }
            }
        }
        return null;//TODO добавить кастомную ошибку
    }

    /**
     * @param fields     - поля объекта
     * @param annotation - аннотация, по которой мы будем вытаскивать тип переменной
     * @return тип переменной, которая аннотирована "@param annotation"
     */
    public Class<?> findAnnotationFieldType(Field[] fields, Class<?> annotation) {
        for (int i = 0; i < fields.length; i++) {
            Annotation[] declaredAnnotations = fields[i].getDeclaredAnnotations();
            for (int j = 0; j < declaredAnnotations.length; j++) {
                if (annotation.isAssignableFrom(declaredAnnotations[j].getClass())) {
                    return fields[i].getType();
                }
            }
        }
        return null;//TODO добавить кастомную ошибку
    }

    /**
     * @param o - Объект с которым мы работает
     * @return список объектов в виде (Клюс-значение) переменной, которая аннотирована @Column
     */
    public List<FieldMap> findColumnMapFields(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        List<FieldMap> fieldMaps = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Annotation[] declaredAnnotations = fields[i].getDeclaredAnnotations();
            for (int j = 0; j < declaredAnnotations.length; j++) {
                if (declaredAnnotations[j] instanceof Column) {
                    try {
                        Field field = fields[i];
                        field.setAccessible(true);
                        Object value = field.get(o);
                        Column column = (Column) declaredAnnotations[j];
                        FieldMap fieldMap = new FieldMap(column.name(), value);
                        fieldMaps.add(fieldMap);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return fieldMaps;
    }

    public List<FieldMapWithoutValue> findColumnMapFieldsWithoutValue(Class<?> aClass) {
        Field[] fields = aClass.getDeclaredFields();
        List<FieldMapWithoutValue> fieldMaps = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Annotation[] declaredAnnotations = fields[i].getDeclaredAnnotations();
            for (int j = 0; j < declaredAnnotations.length; j++) {
                if (declaredAnnotations[j] instanceof Column) {
                    Field field = fields[i];
                    Column column = (Column) declaredAnnotations[j];
                    FieldMapWithoutValue fieldMap = new FieldMapWithoutValue(column.name(), field.getType());
                    fieldMaps.add(fieldMap);
                }
            }
        }
        return fieldMaps;
    }

}
