public void inc(int[] bits) { // int -> boolean
    for (int i = 0; i < bits.length; i++) {
        if (bits[i] == 0) {
            bits[i] = 1;
            return;
        } else {
            bits[i] = 0;
        }
    }

    //возможно стоит предусмотреть расширение длинны массива bits при переполнении
}