package com.test.runntime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class AspectjManager {

    static HashMap<String, HashSet<Class>> whiteListMap = null;

    // release包不调用init， whiteListMap为null，不插桩
    public static void init(Class [] whiteListFile, Class [][] whiteListFileExceptionClass) {
        if (whiteListFile.length == whiteListFileExceptionClass.length) {
            whiteListMap = new HashMap<>();
            for (int i = 0; i < whiteListFile.length; i++) {
                whiteListMap.put(whiteListFile[i].getCanonicalName(), new HashSet<>(Arrays.asList(whiteListFileExceptionClass[i])));
            }
        }
    }


}
