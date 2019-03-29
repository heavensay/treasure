package com.helix.demo.rule.easyrule;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RulesEngineParameters;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.junit.Test;

import java.io.*;

/**
 * 规则配置在yml文件中
 * @author lijianyu
 * @date 2019/3/28 15:17
 */
public class RuleYml {

    @Test
    public void rule() throws FileNotFoundException {
        // create a rules engine
        RulesEngineParameters parameters = new RulesEngineParameters().skipOnFirstAppliedRule(true);
        RulesEngine fizzBuzzEngine = new DefaultRulesEngine(parameters);

        // create rules
        ///com/helix/demo/rule/easyrule/
        InputStream inputStream = this.getClass().getResourceAsStream("/com/helix/demo/rule/easyrule/easy-rule.yml");
        InputStreamReader reader = new InputStreamReader(inputStream);
        Rules rules = MVELRuleFactory.createRulesFrom(reader);

        // fire rules
        Facts facts = new Facts();
        for (int i = 1; i <= 10; i++) {
            facts.put("number", i);
            fizzBuzzEngine.fire(rules, facts);
        }
    }
}
