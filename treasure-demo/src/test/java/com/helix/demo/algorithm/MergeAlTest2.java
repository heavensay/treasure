package com.helix.demo.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 归并排序（MERGE-SORT)
 * 参考：https://juejin.cn/post/7088591963070414884
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
public class MergeAlTest2 {
    public void sort(int[] array)  {
        mergeSort(array, 0, array.length);
    }

    public void mergeSort(int[] array, int low, int high) {
        if (low >= high) return;
        int mid = (low + high) / 2;  // 将数组一分为二
        mergeSort(array, 0, mid);  // 对左边一半进行排序
        mergeSort(array, mid, high);  // 对右边一半进行排序
        merge(array, low, mid, high);  // 将两个有序子数组合并为一个有序数组
    }

    // 将数组左右两半合并为一个排好序的数组。左右两个子数组已经是排好序的数组
    public void merge(int[] array, int low, int mid, int high) {
        // 将数组array复制到一个新数组copy中，将排序完成后的值写入原数组array
        int[] copy = Arrays.copyOf(array, array.length);  // 复制
        int i = low, j = mid, k = low;
        // 逐一比较两个子数组，小者排在前面。直至其中一个数组全部写入结果数组中
        while (i < mid && j < high) {
            if (copy[i] <= copy[j]) array[k++] = copy[i++];  // 相等的元素，相对位置不改变
            else array[k++] = copy[j++];
        }
        // 如果左边的子数组中剩余的数据写入数组中
        while (i < low) array[k++] = copy[i++];
        // 如果右边的子数组中剩余的数据写入数组中
        while (j < high) array[k++] = copy[j++];
    }

//    /**********************************************************************/
//// 将两个有序子数组合并为一个有序数组。第一个有序子数组是[low,mid)，第二个有序子数组是[mid,high)
//    public void merge(int[] array, int low, int mid, int high) {
//        // 将数组array复制到一个新数组copy中，将排序完成后的值写入原数组array
//        int[] res = new int[array.length];  // 复制
//        int i = low, j = mid, k = low;
//        // 逐一比较两个子数组，小者排在前面。直至其中一个数组全部写入结果数组中
//        while (i < mid && j < high) {
//            if (array[i] <= array[j]) res[k++] = array[i++];
//            else res[k++] = array[j++];
//        }
//        // 如果左边的子数组中剩余的数据写入数组中
//        while (i < low) res[k++] = array[i++];
//        // 如果右边的子数组中剩余的数据写入数组中
//        while (j < high) res[k++] = array[j++];
//
//        // 把结果数组res中[low,high)中的部分复制到原数组中，从而改变原数组
//        for (i = low, k = low; i < high; i++, k++) {
//            array[k] = res[i];
//        }
//    }
//
//    public void mergeSort(int[] array, int low, int high) {
//        if (low >= high) return;
//        int mid = (low + high) / 2;  // 将数组一分为二
//        mergeSort(array, 0, mid);  // 对左边一半进行排序
//        mergeSort(array, mid, high);  // 对右边一半进行排序
//        merge(array, low, mid, high);  // 将排好序的两部分合并为一个排好序的数组
//    }
}
