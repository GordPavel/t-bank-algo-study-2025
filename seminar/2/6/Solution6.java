import java.util.ArrayList;
import java.util.List;

class Solution6 {

    public static void main(String[] args) {
        System.out.println(restoreOriginalArray(new int[]{3, 3, 1, 2, 4, 5, 5, 4, 3}));
    }

    public static List<Integer> restoreOriginalArray(int[] arr) {
        List<Integer> result = new ArrayList<>();
        int i = 0;
        result.add(arr[i++]);
        while (i < arr.length - 1) {
            int lastIdx = result.size() - 1;
            if (!result.isEmpty() && arr[i] == result.get(lastIdx)) {
                result.remove(lastIdx);
            } else {
                result.add(arr[i]);
            }
            i++;
        }
        if (i < arr.length) {
            result.add(arr[i]);
        }
        return result;
    }
}
