package oteher;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by 智杰 on 2016/11/10.
 *
 */

public class Tools {

    public static int ram(int max, int min) {
        Random random = new Random();
        int s;
        s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    public static List<String> Array2list( String[] s ){
        List<String> sl = Arrays.asList(s);
        return sl;
    }


}
