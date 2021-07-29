package service.orm;

import annotations.Entity;
import annotations.Id;
import data.FieldKeyWithType;
import data.FieldOneToMany;
import org.reflections.Reflections;
import service.annotation.AnnotationFieldService;
import service.configs.OrmConnection;
import utils.PostgreSqlTypeConverter;
import utils.SqlTypeConverter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CheckDBTable
{
    private static final java.util.logging.Logger LOGGER =
            java.util.logging.Logger.getLogger(CheckDBTable.class.getName());
    private OrmConnection ormConnection ;
    private final SqlTypeConverter sqlTypeConverter = new PostgreSqlTypeConverter();

    public CheckDBTable(OrmConnection ormConnection) {
        this.ormConnection = ormConnection;
    }

    /**
     * Проверяет создана ли таблица сущности и создает таблицу
     */
    public void check() {
        Connection connect = ormConnection.connect();
        String[] entityPackages = new String[]{"orm.entities"};
        Reflections reflections = new Reflections(entityPackages);
        Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(Entity.class);
        List<Class<?>> clazz = new ArrayList<>(allClasses);
        for (Class<?> aClass : clazz) {
            Entity entityParent = aClass.getAnnotation(Entity.class);
            String tableName = entityParent.name();
            Class<?> idType = new AnnotationFieldService().findFieldTypeByAnnotation(aClass.getDeclaredFields(), Id.class);
            AnnotationFieldService annotationFieldService = new AnnotationFieldService();
            List<FieldKeyWithType> columnMapFieldsWithoutValue = annotationFieldService.findColumnFieldsKeyWithType(aClass);
            List<FieldKeyWithType> oneToManyFieldsWithoutValue = annotationFieldService.findOneToManyFieldsKeyWithType(aClass);
            List<FieldOneToMany> fieldsOneToMany = new ArrayList<>();
            for (int i = 0; i < oneToManyFieldsWithoutValue.size(); i++) {
                FieldKeyWithType oneToManyField = oneToManyFieldsWithoutValue.get(i);
                Entity annotationChild = oneToManyField.getType().getAnnotation(Entity.class);
                String tableNameChild = annotationChild.name();
                String idName = oneToManyField.getKey();
                Class<?> idTypeChild = new AnnotationFieldService().findFieldTypeByAnnotation(oneToManyField.getType().getDeclaredFields(), Id.class);
                fieldsOneToMany.add(new FieldOneToMany(idName, tableNameChild, idTypeChild));
            }
            String foreignString = fieldsOneToMany.stream().map(field -> {
                columnMapFieldsWithoutValue.add(new FieldKeyWithType(field.getIdName(), field.getIdType()));
                String foreign = "FOREIGN KEY (%s) REFERENCES %s(id) ON DELETE CASCADE";
                return String.format(foreign, field.getIdName(), field.getEntityName());
            }).collect(Collectors.joining(","));
            String collect = columnMapFieldsWithoutValue.stream()
                    .map(n -> n.getKey() + " " + sqlTypeConverter.getConverter().get(n.getType()))
                    .collect(Collectors.joining(", "));
            String idSqlFormat = "id " + sqlTypeConverter.getConverter().get(idType) + " PRIMARY KEY";
            String sql;
            if (collect.isEmpty() && fieldsOneToMany.isEmpty()) {
                sql = "CREATE TABLE " + tableName + " (" + idSqlFormat + ")";
            }
            else if (!collect.isEmpty() && fieldsOneToMany.isEmpty()) {
                sql = "CREATE TABLE " + tableName + " (" + idSqlFormat + ", " + collect + ")";
            }
            else {
                sql = "CREATE TABLE " + tableName + " (" + idSqlFormat + ", " + collect + ", " + foreignString + ")";
            }
            try {
                Statement statement = connect.createStatement();
                statement.executeUpdate(sql);
            } catch (SQLException e) {
            }
        }
        try {
            connect.close();
            LOGGER.info("DataBase was checked");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
