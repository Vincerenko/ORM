package service.annotation;

import annotations.Column;
import annotations.Id;
import annotations.OneToMany;
import data.FieldMap;
import data.FieldKeyWithType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class AnnotationFieldService {

    /**
     * @param o          - Объект с которым мы работает
     * @param annotation Аннотация, по которой мы будем вытаскивать значение переменной
     * @return значение переменной, которая аннотирована "@param annotation"
     */
    public Object findFieldValueByAnnotation(Object o, Class<?> annotation) {
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
    public Class<?> findFieldTypeByAnnotation(Field[] fields, Class<?> annotation) {
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
     * @return список объектов в виде (Ключ-значение) переменной, которая аннотирована @Column
     */
    public List<FieldMap> findColumnFields(Object o) {
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

    /**
     * Метод для поиска полей по аннотации (Column)
     * @param aClass Класс обьекта
     * @return набор полей которые аннотированы Column
     */
    public List<FieldKeyWithType> findColumnFieldsKeyWithType(Class<?> aClass) {
        Field[] fields = aClass.getDeclaredFields();
        List<FieldKeyWithType> fieldMaps = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Annotation[] declaredAnnotations = fields[i].getDeclaredAnnotations();
            for (int j = 0; j < declaredAnnotations.length; j++) {
                if (declaredAnnotations[j] instanceof Column) {
                    Field field = fields[i];
                    Column column = (Column) declaredAnnotations[j];
                    FieldKeyWithType fieldMap = new FieldKeyWithType(column.name(), field.getType());
                    fieldMaps.add(fieldMap);
                }
            }
        }
        return fieldMaps;
    }

    /**
     *
     * @param aClass Класс обьекта
     * @return набор полей которые аннотированы OneToMany
     */
    public List<FieldKeyWithType> findOneToManyFieldsKeyWithType(Class<?> aClass) {
        Field[] fields = aClass.getDeclaredFields();
        List<FieldKeyWithType> fieldMaps = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Annotation[] declaredAnnotations = fields[i].getDeclaredAnnotations();
            for (int j = 0; j < declaredAnnotations.length; j++) {
                if (declaredAnnotations[j] instanceof OneToMany) {
                    Field integerListField = fields[i];
                    ParameterizedType integerListType = (ParameterizedType) integerListField.getGenericType();
                    Class<?> genericTypeInList = (Class<?>) integerListType.getActualTypeArguments()[0];
                    OneToMany oneToMany = (OneToMany) declaredAnnotations[j];
                    String name = oneToMany.idName();
                    FieldKeyWithType fieldMap = new FieldKeyWithType(name, genericTypeInList);
                    fieldMaps.add(fieldMap);
                }
            }
        }
        return fieldMaps;
    }

    /**
     *
     * @param aClass Класс обьекта
     * @return  имена всех Columns
     */
    public List<String> findColumnKeys(Class<?> aClass) {
        Field[] fields = aClass.getDeclaredFields();
        List<String> fieldKeys = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Annotation[] declaredAnnotations = fields[i].getDeclaredAnnotations();
            for (int j = 0; j < declaredAnnotations.length; j++) {
                if (declaredAnnotations[j] instanceof Column ) {
                    Column column = (Column) declaredAnnotations[j];
                    fieldKeys.add(column.name());
                }
//                else if (declaredAnnotations[j] instanceof Id){
//                    Id id = (Id) declaredAnnotations[j];
//                    fieldKeys.add("id");
//                }
            }
        }
        return fieldKeys;
    }

}
