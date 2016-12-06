package pro.cherkassy.rboyko;

/**
 * Created by rboyko on 05.12.16.
 */
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


public class CollectionUtils {


    private CollectionUtils() {
    }


    public static <E> List<E> filter(List<E> elements, Predicate<E> filter) {
        List<E> newList=new ArrayList<>();
        for(E element:elements) {
            if (filter.test(element))
                newList.add(element);

        }
        return newList;
    }


    public static <E> boolean anyMatch(List<E> elements, Predicate<E> predicate) {
        for(E element:elements)
            if(predicate.test(element))
                return true;
        return false;
    }


    public static <E> boolean allMatch(List<E> elements, Predicate<E> predicate) {
        for (E element:elements)
            if(!predicate.test(element))
                return false;
        return true;
    }


    public static <E> boolean noneMatch(List<E> elements, Predicate<E> predicate) {
        for (E element:elements)
            if(predicate.test(element))
                return false;
        return true;
    }


    public static <T, R> List<R> map(List<T> elements, Function<T, R> mappingFunction) {
        List<R> newList=new ArrayList<>();
        for(T element:elements)
            newList.add(mappingFunction.apply(element));
        return newList;
    }


    public static <E> Optional<E> max(List<E> elements, Comparator<E> comparator) {
        if(elements.size()==0)
            return Optional.empty();
        E element =elements.get(0);
        for(int i=1;i<elements.size();i++) {
            element=(comparator.compare(element, elements.get(i)) > 0 ? element: elements.get(i));
        }
        return Optional.of(element);
    }


    public static <E> Optional<E> min(List<E> elements, Comparator<E> comparator) {
        if(elements.size()==0)
            return Optional.empty();
        E element =elements.get(0);
        for(int i=1;i<elements.size();i++) {
            element=(comparator.compare(element, elements.get(i)) < 0 ? element: elements.get(i));
        }
        return Optional.of(element);
    }


    public static <E> List<E> distinct(List<E> elements) {
        List<E> newList=new ArrayList<>();
        for(E element:elements)
            if(!newList.contains(element))
                newList.add(element);
        return newList;
    }


    public static <E> void forEach(List<E> elements, Consumer<E> consumer) {
        for (E element:elements)
            consumer.accept(element);
    }


    public static <E> Optional<E> reduce(List<E> elements, BinaryOperator<E> accumulator) {
        if(elements.size()==0)
            return Optional.empty();
        E elemen=elements.get(0);
        for(int i=1;i<elements.size();i++)
            elemen=accumulator.apply(elemen,elements.get(i));
        return Optional.of(elemen);
    }




    public static <E> E reduce(E seed, List<E> elements, BinaryOperator<E> accumulator) {
        if(elements.size()==0)
            return null;
        E element=elements.get(0);
        for(int i=1;i<elements.size();i++)
            element=accumulator.apply(element,elements.get(i));
        return element;
    }


    public static <E> Map<Boolean, List<E>> partitionBy(List<E> elements, Predicate<E> predicate) {
        Map<Boolean,List<E>> retMap=new HashMap<>();
        List<E> trueGroup=new ArrayList<>();
        List<E> falseGroup=new ArrayList<>();
        for(E element:elements){
            if(predicate.test(element))
                trueGroup.add(element);
            else
                falseGroup.add(element);
        }
        retMap.put(true,trueGroup);
        retMap.put(false,falseGroup);
        return retMap;
    }


    public static <T, K> Map<K, List<T>> groupBy(List<T> elements, Function<T, K> classifier) {
        Map<K,List<T>> retMap=new HashMap<>();

        for(T element:elements) {
            K key = classifier.apply(element);
            if (retMap.containsKey(key))
                retMap.get(key).add(element);
            else {
                ArrayList<T> list = new ArrayList<>();
                list.add(element);
                retMap.put(key, list);
            }

        }
        return retMap;
    }


    public static <T, K, U> Map<K, U> toMap(List<T> elements,
                                            Function<T, K> keyFunction,
                                            Function<T, U> valueFunction,
                                            BinaryOperator<U> mergeFunction) {
        Map<K,U> retMap=new HashMap<>();
        K key;
        U value;
        for(T element:elements){
            key=keyFunction.apply(element);
            value=valueFunction.apply(element);
            if(retMap.containsKey(key))
                retMap.put(key,mergeFunction.apply(retMap.get(key),value));
            else
                retMap.put(key,value);
        }
        return retMap;
    }
}

