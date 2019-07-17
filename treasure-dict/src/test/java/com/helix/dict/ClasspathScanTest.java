package com.helix.dict;

import com.helix.dict.annotation.Dict;
import com.helix.dict.annotation.DictEnumSource;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.junit.Test;

/**
 * @author ljy
 * @date 2019/7/17 11:27
 */
public class ClasspathScanTest {
    @Test
    public void scan() {
        ScanResult scanResult =                // Assign scanResult in try-with-resources
                new ClassGraph()                    // Create a new ClassGraph instance
//                        .verbose()                      // If you want to enable logging to stderr
                        .enableAllInfo()                // Scan classes, methods, fields, annotations
                        .whitelistPackages("com.helix")   // Scan com.xyz and subpackages
                        .scan();                      // Perform the scan and return a ScanResult
        // Use the ScanResult within the try block, e.g.

        ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(DictEnumSource.class.getName());

        for (String s : classInfoList.getNames()) {
            System.out.println("========name:"+s);
        }

        System.out.println("========");

        for (ClassInfo classInfo : classInfoList) {
            Class c = classInfo.loadClass();
            System.out.println(c.getName());
        }

    }

    @Test
    public void scanClassPath() {

    }
}
