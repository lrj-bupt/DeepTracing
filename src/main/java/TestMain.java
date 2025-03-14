import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestMain<E> {
    /*
    * List<E> -> E
    * List<String> String
    * List<List<String>> -> List<String>
    GenericArrayType E[]  List<String>[]  getGenericComponentType List<String>[] -> List<String>
    * */
    public List<String> member;
    public List<ArrayList<String>> list_array;
    public static void main(String[] args) throws Exception {
        List<String> list = new LinkedList<>();
        list.add("1");

        Type type = list.getClass().getGenericSuperclass();
        if(type instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            System.out.println("actual" + actualTypeArguments[0]);
            System.out.println(actualTypeArguments[0].getClass().getName());  // TypeVariableImpl
            System.out.println(actualTypeArguments[0].getTypeName());
        }

        Field[] fields = TestMain.class.getFields();
        for (Field field : fields) {
            Type genericType = field.getGenericType();
            if(genericType instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] arguments = parameterizedType.getActualTypeArguments();
                System.out.println(arguments[0]);
            }
        }
    }

    public void test() throws Exception{

    }
}
