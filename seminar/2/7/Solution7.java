import java.util.Arrays;

class Solution7 {

    public static void main(String[] args) {
//        так как это стек, то инвертированное представление, закодировано число 0011 -> 1100 = 11
        int[] bits = {0, 0, 1, 1};
        inc(bits);
        System.out.println(Arrays.toString(bits));
    }

    public static void inc(int[] bits) { // int -> boolean
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == 0) {
                bits[i] = 1;
                return;
            } else {
                bits[i] = 0;
            }
        }
    }

}
