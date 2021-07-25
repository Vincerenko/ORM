package data;



public class FieldOneToMany {

    private String idName;
    private String entityName;
    private Class<?> idType;

    /**
     *
     * @param idName дочернего класса
     * @param entityName имя дочернего класса
     * @param idType тип ID дочернего класса
     */
    public FieldOneToMany(String idName, String entityName, Class<?> idType) {
        this.idName = idName;
        this.entityName = entityName;
        this.idType = idType;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Class<?> getIdType() {
        return idType;
    }

    public void setIdType(Class<?> idType) {
        this.idType = idType;
    }
}
