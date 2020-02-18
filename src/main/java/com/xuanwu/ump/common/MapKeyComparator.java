package com.xuanwu.ump.common;

import java.util.Comparator;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class MapKeyComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer integer, Integer t1) {
        return (integer - t1);
    }
}
