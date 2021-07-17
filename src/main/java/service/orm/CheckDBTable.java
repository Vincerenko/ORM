package service.orm;

import annotations.Entity;
import annotations.Id;
import data.FieldMapWithoutValue;
import utils.MapSqlConverterType;
import org.reflections.Reflections;
import service.annotation.AnnotationFieldService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CheckDBTable
{
    private final ConnectToJDBC connectToJDBC = new ConnectToJDBC();

    /**
     * Проверяет создана ли таблица сущности и создает таблицу
     */
    public void check()  {
        Connection connect = connectToJDBC.connect();
        String[] entityPackages = new String[]{"entities"};
        Reflections reflections = new Reflections(entityPackages);

        Set<Class<?>> allClasses =
                reflections.getTypesAnnotatedWith(Entity.class);
        List<Class<?>> clazz = new ArrayList<>(allClasses);
        for (int i = 0; i < clazz.size(); i++) {
            Class<?> aClass = clazz.get(i);
            Entity entity = aClass.getAnnotation(Entity.class);
            String tableName = entity.name();
            //System.out.println("Table name = " + tableName);
            Class<?> idType = new AnnotationFieldService().findAnnotationFieldType(aClass.getDeclaredFields(), Id.class);
            AnnotationFieldService annotationFieldService = new AnnotationFieldService();
            List<FieldMapWithoutValue> columnMapFieldsWithoutValue = annotationFieldService.findColumnMapFieldsWithoutValue(aClass);
            String collect = columnMapFieldsWithoutValue.stream()
                    .map(n -> n.getKey() + " " + MapSqlConverterType.CONVERTER.get(n.getType()))
                    .collect(Collectors.joining(", "));
            String idSqlFormat = "Id " + MapSqlConverterType.CONVERTER.get(idType) + " PRIMARY KEY";
            String sql;
            if (collect.isEmpty()) {
                sql = "CREATE TABLE " + tableName + " (" + idSqlFormat + ")";
            }
            else {
                sql = "CREATE TABLE " + tableName + " (" + idSqlFormat + ", " + collect + ")";
            }
            try {

                Statement statement = connect.createStatement();
                statement.executeUpdate(sql);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        try {
            connect.close();
            System.out.println("Closed database successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

}
