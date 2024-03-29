package com.helix.demo.jvm;

import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.VMOption;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public class VMArgs {
    public static void main(String[] args) throws Exception {
//		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        HotSpotDiagnosticMXBean hsdMXBean = ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class);

        List<VMOption> list = hsdMXBean.getDiagnosticOptions();
        for (VMOption vmOption : list) {
            System.out.println("vm name of arg:" + vmOption.getName() + "----  value:" + vmOption.getValue());
        }

        RuntimeMXBean runtimeMXBean = ManagementFactory.getPlatformMXBean(RuntimeMXBean.class);
        List<String> runArgs = runtimeMXBean.getInputArguments();
        for (String s : runArgs) {
            System.out.println(s);
        }
//		mbs.get
//		HotSpotDiagnostic
    }
}
