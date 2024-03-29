package com.helix.demo.jvm.testclassloader.instrument;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.Set;
import java.util.Timer;
import java.util.TreeSet;

public class HotAgent {
    protected static Set<String> clsnames = new TreeSet<String>();

    public static void premain(String agentArgs, Instrumentation inst)
            throws Exception {
        ClassFileTransformer transformer = new ClassTransform(inst);
        inst.addTransformer(transformer);
        System.out.println("是否支持类的重定义：" + inst.isRedefineClassesSupported());
        ClassDefinition cd = new ClassDefinition(Bean1.class, ClassTransform.getClassBytes(ClassTransform.transformedClass));
        inst.redefineClasses(cd);

        Timer timer = new Timer();

        timer.schedule(new ReloadTask(inst), 2000, 2000);
    }
}
