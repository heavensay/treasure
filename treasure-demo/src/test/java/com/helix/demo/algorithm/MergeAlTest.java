package com.helix.demo.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 归并排序（MERGE-SORT)
 *  归并排序是用分治思想，分治模式在每一层递归上有三个步骤：
 *
 * 分解（Divide）：将n个元素分成个含n/2个元素的子序列。
 * 解决（Conquer）：用合并排序法对两个子序列递归的排序。
 * 合并（Combine）：合并两个已排序的子序列已得到排序结果。
 *
 * 算法步骤
 * 1申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列；
 * 2设定两个指针，最初位置分别为两个已经排序序列的起始位置；
 * 3比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置；
 * 4重复步骤 3 直到某一指针达到序列尾；
 * 5将另一序列剩下的所有元素直接复制到合并序列尾。
 *
 * 平均时间复杂度：O(nlogn)
 * 最佳时间复杂度：O(n)
 * 最差时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 * 排序方式：In-place
 * 稳定性：稳定
 * @author lijianyu
 * @date 2023/7/6 16:51
 */
public class MergeAlTest {

    @Test
    public void merge(){
        Integer[] array1 = new Integer[]{4,5,7,8};
        Integer[] array2 = new Integer[]{1,2,3,6};

        Integer[] list = merge(array1, array2);
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

    @Test
    public void selectSort(){
        Integer[] array1 = new Integer[]{1,7,9,4,5,7,8};

        Integer[] list = sort(array1);
        for (int i = 0; i < list.length; i++) {
            System.out.println(list[i]);
        }
    }

    /**
     * 数组进行排序
     */
    public Integer[] sort(Integer[] array){
        //采用选择排序；每次查询最小数
        for (int i = 0; i < array.length; i++) {
            Integer min = array[i];
            Integer cursor = i;
            for (int j = i; j <array.length; j++) {
                if(min > array[j]){
                    min = array[j];
                    cursor = j;
                }
            }
            if(i!=cursor){
                Integer temp = array[i];
                array[i] = array[cursor];
                array[cursor] = temp;
            }
        }
        return array;
    }

    /**
     * 归并排序：数据已经是排序好的
     * @param array1
     * @param array2
     * @return
     */
    public Integer[] merge(Integer[] array1, Integer[] array2){
        List<Integer> list = new ArrayList<Integer>();

        int cursorNow = 0;
        for (int i = 0; i < array1.length; i++) {
            if(cursorNow>=array2.length){
                list.add(array1[i]);
                continue;
            }
            for (int j = cursorNow; j < array2.length; j++) {
                if (array1[i] >= array2[j]) {
                    list.add(array2[j]);
                    cursorNow = j+1;
                } else if (array1[i] < array2[j]) {
                    list.add(array1[i]);
                    break;
                }
            }
        }
        return list.toArray(new Integer[0]);
    }
}
