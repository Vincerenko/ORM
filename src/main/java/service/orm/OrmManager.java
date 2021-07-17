package service.orm;

import annotations.Entity;
import annotations.Id;
import data.FieldMap;
import service.annotation.AnnotationFieldService;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrmManager
{

    private final ConnectToJDBC connectToJDBC = new ConnectToJDBC();

    //CREATE
    //UPDATE
    public void save(Object o) throws SQLException {
        Class<?> aClass = o.getClass();
        System.out.println(aClass.getSimpleName());
        Entity entity = o.getClass().getAnnotation(Entity.class);
        String tableName = entity.name();
        System.out.println("Table name = " + tableName);
        Class<?> idType = new AnnotationFieldService().findAnnotationFieldType(aClass.getDeclaredFields(), Id.class);
        Object id = new AnnotationFieldService().findAnnotationField(o, Id.class);
        System.out.println("Id type = " + idType.getSimpleName());
        System.out.println("Id = " + id);
        if (id == null) {
            create(idType, tableName, o);
        }
        else {
            update(id, tableName, o);
        }



    }

    private void create(Class<?> typeId, String tableName, Object o) throws SQLException {
        Object id = null;
        if (typeId.isAssignableFrom(String.class)) {
            id = UUID.randomUUID().toString();//FIXME
        }
        if (typeId.isAssignableFrom(Number.class)) {
            id = (long) UUID.randomUUID().toString().hashCode();//FIXME
        }
        System.out.println(id);
        List<FieldMap> params = new AnnotationFieldService().findColumnMapFields(o);
        String keys = params.stream().map(FieldMap::getKey).collect(Collectors.joining(", "));
        String values = params.stream().map(it -> it.getValue().toString()).collect(Collectors.joining(", "));
        String[] strings = keys.split(",");

        String SQL = "INSERT INTO " + tableName + " (id, " + keys + ") VALUES (" + id + ", " + values + ")";
//        try {
//            Statement statement = connectToJDBC.connect().createStatement();
//            statement.executeUpdate(sql);
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//        }
    }

    private void update(Object id, String tableName, Object o) {//TODO
        System.out.println(id);

        List<FieldMap> params = new AnnotationFieldService().findColumnMapFields(o);

        String keys = params.stream().map(FieldMap::getKey).collect(Collectors.joining(", "));
        String values = params.stream().map(it -> it.getValue().toString()).collect(Collectors.joining(", "));

        String SQL = "UPDATE " + tableName + " (id, " + keys + ") VALUES (" + id + ", " + values + ")";//TODO
        System.out.println(SQL);
    }

    //DELETE
    public void deleteById(Long id) {

    }

    //SELECT
    public Object getById(Long id) {
        return null;
    }

    //SELECT
    public Object selectAll(Class<Object> c) {
        return null;
    }


}
