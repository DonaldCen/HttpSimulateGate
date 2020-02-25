package com.xuanwu.ump.HttpSimulateGate.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/24
 * @Version 1.0.0
 */
public class ListUtil {
    /**
     * Null-safe check if the specified collection is empty.
     * <p>
     * Null returns true.
     *
     * @param list  the collection to check, may be null
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
     * @param list  the collection to check, may be null
     * @return true if non-null and non-empty
     */
    public static <T> boolean isNotBlank(List<T> list) {
        return !ListUtil.isBlank(list);
    }

    public static <T> List<T> cloneList(List<T> list) {
        List<T> temp = new ArrayList<T>();

        if (list == null)
            return temp;

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

    public static <T> void extractQueueItems(int extractSize,
                                             T headItem, List<T> extractList, BlockingQueue<T> queue) {
        if (headItem != null)
            extractList.add(headItem);

        for (;;) {
            if (extractList.size() >= extractSize || queue.isEmpty())
                break;

            T item = queue.poll();
            if (item != null)
                extractList.add(item);
        }
    }
}
