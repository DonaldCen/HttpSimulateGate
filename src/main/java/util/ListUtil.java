package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/11
 * @Version 1.0.0
 */
public class ListUtil {
    /**
     * Null-safe check if the specified collection is empty.
     * <p>
     * Null returns true.
     *
     * @param list
     *            the collection to check, may be null
     * @return true if empty or null
     */
    public static <T> boolean isBlank(List<T> list) {
        return (list == null || list.isEmpty());
    }

    /**
     * Null-safe check if the specified collection is not empty.
     * <p>
     * Null returns false.
     *
     * @param list
     *            the collection to check, may be null
     * @return true if non-null and non-empty
     */
    public static <T> boolean isNotBlank(List<T> list) {
        return !isBlank(list);
    }

    public static <T> List<T> cloneList(List<T> list) {
        List<T> temp = new ArrayList<T>();
        if (list == null) {
            return temp;
        }
        temp.addAll(list);
        return temp;
    }

    public static <T> void clearList(List<T> list) {
        if (list != null) {
            list.clear();
        }
    }

    public static <T> List<List<T>> splitList(List<T> list, int maxSize) {
        List<List<T>> temp = new ArrayList<List<T>>();
        if (list.size() == 0 || maxSize <= 0) {
            return temp;
        }
        int i = 0;
        while (i < list.size()) {
            int toIndex = (i + maxSize);
            if (toIndex > list.size()) {
                toIndex = toIndex - maxSize + (list.size() - i);
            }
            temp.add(new ArrayList<T>(list.subList(i, toIndex)));
            i += maxSize;
        }
        return temp;
    }

    public static <T> List<T> map2List(Collection<T> vals) {
        List<T> ts = new ArrayList<T>();
        if (vals == null) {
            return ts;
        }
        Iterator<T> it = vals.iterator();
        while (it.hasNext()) {
            ts.add(it.next());
        }
        return ts;
    }

    public static String arr2String(Object[] array) {
        if (array == null || array.length == 0) {
            return "";
        }

        int i = 0;
        StringBuffer sb = new StringBuffer();
        for (Object o : array) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(o);
            i++;
        }
        return sb.toString();
    }

    /**
     * 合并元素到数组当中，会排除重复的，并且重新排序
     *
     * @param array
     * @param item
     * @return
     */
    public static Integer[] mergeItem(Integer[] array, Integer item) {
        Arrays.sort(array);
        if (Arrays.binarySearch(array, item) >= 0) {
            return array;
        }

        List<Integer> list = new ArrayList<Integer>();
        list.addAll(Arrays.asList(array));
        list.add(item);

        array = list.toArray(new Integer[list.size()]);
        Arrays.sort(array);
        return array;
    }

    /**
     * 合并元素到数组当中，会排除重复的，并且重新排序
     *
     * @param array
     * @param items
     * @return
     */
    public static Integer[] mergeItems(Integer[] array, Integer[] items) {
        for (Integer item : items) {
            array = mergeItem(array, item);
        }
        return array;
    }

    /**
     * 数组转换为列表
     */
    public static <T> List<T> fromArray(T[] array){

        if(array == null || array.length == 0){
            return new ArrayList<T>();
        }
        List<T> list = new ArrayList<T>();
        Collections.addAll(list, array);
        return list;
    }
}
