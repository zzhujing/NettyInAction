package com.hujing.netty.test;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author : hujing
 * @date : 2019/10/8
 */
public class MainTest {
    public static void main(String[] args) {
        ArrayList<Integer> source = Lists.newArrayList(1, 2, 3, 4);
        IntStream.rangeClosed(1, source.size() - 1).mapToObj(i -> new Integer[]{source.get(i - 1), source.get(i)}).forEach(arr -> System.out.println(Arrays.toString(arr)));
    }
}
