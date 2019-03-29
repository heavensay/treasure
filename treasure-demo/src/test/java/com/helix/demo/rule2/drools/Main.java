package com.helix.demo.rule2.drools;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Main
{
    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        KieSession kSession = kc.newKieSession("ksession1");

        Applicant a = new Applicant();
        a.name = "Xiao,ming";
        a.age = 15;
        a.valid = true;
        kSession.insert(a);

        kSession.fireAllRules();
        kSession.dispose();

        System.out.println(a.valid);
    }
}