package service.orm;

import annotations.Entity;
import data.FieldMap;
import service.annotation.AnnotationFieldService;
import service.annotation.FieldService;
import service.configs.OrmConnection;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Вес методы не public вынесены в абстрактный класс + логика Check DB Table
 * @param <T> тип данных Entity
 * @param <I>  тип дынный Id
 */
public abstract class AbstractOrmManager<T, I> implements Repository<T, I> {



    protected Class<T> clazz;
    protected Class<I> idClazz;
    protected String tableName;
    protected OrmConnection ormConnection;

    public AbstractOrmManager(Class<T> clazz, Class<I> idClazz, OrmConnection ormConnection) {
        this.clazz = clazz;
        this.idClazz = idClazz;
        this.tableName = clazz.getAnnotation(Entity.class).name();
        this.ormConnection = ormConnection;

    }


    protected final AnnotationFieldService annotationFieldService = new AnnotationFieldService();
    protected final FieldService fieldService = new FieldService();

    protected T create(T object) {
        Object id = null;
        if (idClazz.isAssignableFrom(String.class)) {
            id = UUID.randomUUID().toString();
        }else {
            id = new Random().nextLong();
        }




        List<FieldMap> params = annotationFieldService.findColumnFields(object);
        String keys = params.stream()
                .map(FieldMap::getKey)
                .collect(Collectors.joining(", "));
        String values = params.stream()
                .map(it -> "'" + it.getValue().toString() + "'")
                .collect(Collectors.joining(", "));

        String SQL = "INSERT INTO " + tableName + " (id, " + keys + ") VALUES (" + "'" + id + "'" + ", " + values + ")";
        try {
            Statement statement = ormConnection.connect().createStatement();
            statement.executeUpdate(SQL);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Field idField = fieldService.getIdField(object.getClass().getDeclaredFields());
            fieldService.setFieldValue(idField,id,object);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        return object;
    }

    protected T update(Object id, T object) {
        List<FieldMap> params = annotationFieldService.findColumnFields(object);
        if (!params.isEmpty()) {

            String keyValueSql = params.stream().map(it -> {
                String value = it.getValue().toString();
                value = (it.getValue() instanceof String) ? "'" + value + "'" : value;
                return it.getKey() + " = " + value;
            }).collect(Collectors.joining(", "));

            String sql = "UPDATE " + tableName + " SET " + keyValueSql + " WHERE id = '" + id.toString() + "'";
            try {
                Statement statement = ormConnection.connect().createStatement();
                statement.executeUpdate(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return object;
    }

    protected T createObject(ResultSet resultSet, List<String> fieldKeys) throws InstantiationException, IllegalAccessException, SQLException {
        T object = clazz.newInstance();
        String id = resultSet.getString("id");
        fieldService.setFieldByName(object, "id", id, true);
        fieldKeys.remove("id");
        for (String key : fieldKeys) {

            String value = resultSet.getString(key);
            fieldService.setFieldByName(object, key, value, false);
        }

        return object;
    }

    public abstract T save(T object);

    public abstract T getById(I id);

    public abstract void deleteById(I id);

    public abstract List<T> selectAll();

    public abstract List<T> saveAll(List<T> list);

}
