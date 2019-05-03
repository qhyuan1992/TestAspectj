package com.test.runntime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class AspectjManager {

    static HashMap<String, HashSet<Class>> whiteListMap = new HashMap<>();

    public static void init(Class [] whiteListFile, Class [][] whiteListFileExceptionClass) {
        if (whiteListFile.length == whiteListFileExceptionClass.length) {
            for (int i = 0; i < whiteListFile.length; i++) {
                whiteListMap.put(whiteListFile[i].getCanonicalName(), new HashSet<>(Arrays.asList(whiteListFileExceptionClass[i])));
            }
        }
    }


}
