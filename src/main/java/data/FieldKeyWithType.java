package data;


/**
 * Key Содежржит имя полей
 * Type содержит тип полей
 */
public class FieldKeyWithType
{
    private String key;
    private Class<?> type;

    public FieldKeyWithType(String key, Class<?> type) {
        this.key = key;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
