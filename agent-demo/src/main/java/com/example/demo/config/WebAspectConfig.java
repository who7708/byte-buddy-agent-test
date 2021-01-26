package com.example.demo.config;

import com.example.demo.intercept.ServiceInterceptor;
import com.example.demo.intercept.WebInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.context.annotation.Bean;

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
        advisor.setExpression("execution(public * com.example.demo.controller..*(..))");
        advisor.setAdvice(new WebInterceptor());
        return advisor;
    }

    @Bean
    public AspectJExpressionPointcutAdvisor serviceInterceptor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression("execution(public * com.example.demo.service..*(..))");
        advisor.setAdvice(new ServiceInterceptor());
        return advisor;
    }

}
