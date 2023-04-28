package com.helix.demo.algorithm;

public class PermutationString {
    /*
     * 回溯法：打印出字符的所有排列组合
     * 参数arrayA:给定字符串的字符数组
     * 参数start:开始遍历字符与其后面各个字符将要进行交换的位置
     * 参数end:字符串数组的最后一位
     * 函数功能：输出字符串数字的各个字符全排列
     * i.e.输入：abc 输出：abc acb bac bca cba cab
     *
     *
     * 公式1：
        int x[n];
        void backtrack (int i) {
            if (i > n) {
               回溯结束；
            } else {
                // 这里回溯子节点的解空间为start~end
               for (j = start; j <= end; j++) {
                    // 满足条件，向下搜索
                    if (j满足题设条件) {
                        x[i] = j;
                        backtrack(i+1);
                    // 不满足条件，在此剪枝（即回溯）
                    } else {
                    }
               }
           }
        }
     */
    public void recursionArrange(char[] arrayA,int start,int end){
        end = 1;
        if(end <= 1)
             return;
        //满足最后条件则可以输出，而且输出的是本数组，因为经过了交换，没有辅助数组，所以一个一个输出就可以达到目的

        if(start == end){
            for(int i = 0;i < arrayA.length;i++)
                System.out.print(arrayA[i]);
            System.out.print(" ");
        }else{
            // for循环依次选取当前数组start至end位置的元素
            for(int i = start;i <= end;i++){
                // 把索引到的数据元素与第一个元素的位置交换，表示选取了此数据做排列第一个
                //  交换的目的就是为了后面更好的递归，不会出现交叉情况，而且不增加额外的空间复杂度
                swap(arrayA,i,start);

//                //剩余其他组合都可以打印出来
//                //可以打印出:a b c ab ac ba bc ca cb
//                if(start<=end){
//                    System.out.print("~~~");
//                    for(int j = 0;j <= start;j++)
//                        System.out.print(arrayA[j]);
//                    System.out.print("~~~");
//                }

                // 重复此操作依次选择第2,3,...,n位的数据元素排列
                recursionArrange(arrayA,start+1,end);
                //每交换依次，弄完了记得把数据交换回来，防止后面数据混乱
                swap(arrayA,i,start);
            }
        }
    }
    //交换数组m位置和n位置上的值
    public void swap(char[] arrayA,int m,int n){
        char temp = arrayA[m];
        arrayA[m] = arrayA[n];
        arrayA[n] = temp;
    }
    
    public static void main(String[] args){
        PermutationString test = new PermutationString();
        String A = "abc";
        char[] arrayA = A.toCharArray();
        test.recursionArrange(arrayA,0,arrayA.length-1);
    }
}