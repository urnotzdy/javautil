package common;

public class fastslowp {

    //删除有序数组中的重复元素
    public static int rvmDepLength(int[] nums) {
        int total = nums.length;
        if (total == 0) {
            return 0;
        }
        int fast = 1, slow = 1;
        while (fast < total) {
            if (nums[fast] != nums[fast - 1]) {
                nums[slow++] = nums[fast];
            }
            fast++;
        }
        return slow;
    }

    //去掉等于某值，剩余数组长度
    public static int rvmEqValLength(int[] nums, int val) {
        int total = nums.length;
        if (total == 0) {
            return 0;
        }
        int fast = 0, slow = 0;
        while (fast < total) {
            if(nums[fast] != val){
                nums[slow++]=nums[fast];
            }
            fast++;
        }
        return slow;
    }

    public static void main(String[] args) {

    }

}
