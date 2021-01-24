package com.maggie.measure.config;

import com.maggie.measure.intercept.ServiceInterceptor;
import com.maggie.measure.intercept.WebInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/24
 */
// @Configuration
// @ComponentScan("com.maggie.measure")
public class WebAspectConfig {

    @Bean
    public AspectJExpressionPointcutAdvisor webInterceptor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression("execution(public * com.maggie.measure.controller..*(..))");
        advisor.setAdvice(new WebInterceptor());
        return advisor;
    }

    @Bean
    public AspectJExpressionPointcutAdvisor serviceInterceptor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression("execution(public * com.maggie.measure.service..*(..))");
        advisor.setAdvice(new ServiceInterceptor());
        return advisor;
    }

}
