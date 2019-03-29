package com.helix.demo.rule.easyrule;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;

import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you a friend of duke?[yes/no]:");
        String input = scanner.nextLine();

        /**
         * Declare the rule
         */
        HelloWorldRule helloWorldRule = new HelloWorldRule();

        /**
         * Set business data to operate on
         */
        helloWorldRule.setInput(input.trim());

        /**
         * Create a rules engine and register the business rule
         */
        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine().build();
        
        rulesEngine.registerRule(helloWorldRule);

        /**
         * Fire rules
         */
        rulesEngine.fireRules();

    }
}