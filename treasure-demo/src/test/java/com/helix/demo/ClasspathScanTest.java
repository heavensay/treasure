package com.helix.demo;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.junit.Test;

/**
 * class文件扫描
 * @author ljy
 * @date 2019/7/17 10:58
 */
public class ClasspathScanTest {

    @Test
    public void scan() {
        ScanResult scanResult =                // Assign scanResult in try-with-resources
                new ClassGraph()                    // Create a new ClassGraph instance
                        .verbose()                      // If you want to enable logging to stderr
                        .enableAllInfo()                // Scan classes, methods, fields, annotations
                        .whitelistPackages("com.xyz")   // Scan com.xyz and subpackages
                        .scan();                      // Perform the scan and return a ScanResult
        // Use the ScanResult within the try block, e.g.
        ClassInfo widgetClassInfo = scanResult.getClassInfo("com.xyz.Widget");

    }

    @Test
    public void scanClassPath() {
        ScanResult scanResult =                // Assign scanResult in try-with-resources
                new ClassGraph()                    // Create a new ClassGraph instance
                        .verbose()                      // If you want to enable logging to stderr
                        .enableAllInfo()                // Scan classes, methods, fields, annotations
                        .whitelistPackages("com.helix.demo.cache")   // Scan com.xyz and subpackages
//                        .whitelistClasspathElementsContainingResourcePath("com.helix.demo")
                        .scan();                      // Perform the scan and return a ScanResult
        // Use the ScanResult within the try block, e.g.
        System.out.println(scanResult.getAllClasses().size());
//        ClassInfo widgetClassInfo = scanResult.getClassInfo("com.xyz.Widget");

    }
}
