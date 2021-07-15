package service.annotation;

import java.lang.annotation.Annotation;

public class AnnotationService<T>{

    /**
     * В списке аннотаций
     * @param annotations
     * Ищем аннотацию
     * @param type
     * и
     * @return ее
     */
    public T findAnnotation(Annotation[] annotations, Class<T> type){
        for (int i = 0; i < annotations.length; i++) {
            if (type.isAssignableFrom(annotations[i].getClass())){
                return (T)annotations[i];
            }
        }
        return null;//TODO добавить кастомную ошибку
    }


}
