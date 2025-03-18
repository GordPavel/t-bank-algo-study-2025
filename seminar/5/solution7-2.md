    /**
     * Альтернативная реализация с использованием алгоритма Кнута-Морриса-Пратта
     * для поиска подстроки за O(n)
     */
    public static boolean isCyclicShiftKMP(String S, String T) {
        if (S.length() != T.length()) {
            return false;
        }
        
        if (S.isEmpty() || S.equals(T)) {
            return true;
        }
        
        String doubledS = S + S;
        return kmpSearch(doubledS, T);
    }
    
    /**
     * Реализация алгоритма КМП для поиска подстроки
     */
    private static boolean kmpSearch(String text, String pattern) {
        int[] lps = computeLPSArray(pattern);
        int i = 0; // индекс для text
        int j = 0; // индекс для pattern
        
        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            
            if (j == pattern.length()) {
                return true; // найдено совпадение
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return false;
    }
    
    /**
     * Вычисляет массив LPS (Longest Prefix Suffix) для алгоритма КМП
     */
    private static int[] computeLPSArray(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0;
        int i = 1;
        
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }