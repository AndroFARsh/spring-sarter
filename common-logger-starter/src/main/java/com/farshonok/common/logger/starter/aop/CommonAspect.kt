package com.farshonok.common.logger.starter.aop

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
class CommonAspect {

    val log = LoggerFactory.getLogger(CommonAspect::class.java)

    /*
        @within - check annotation on the class level
     */
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    fun isControllerLayer() { /*no-op*/ }


    /*
        within - check class type name
     */
    @Pointcut("within(com.farshonok.*.service.*Service)")
    fun isServiceLayer() { /*no-op*/ }

    /*
        this - check AOP proxy lass type
        target - check target(concrete) object class type
     */
    @Pointcut("this(org.springframework.data.repository.support.Repositories)")
    // @Pointcut("target(org.springframework.data.repository.support.Repositories)")
    fun isRepositoryLayer() { /*no-op*/ }

    /*
        bean - check name of spring bean name
     */
    @Pointcut("bean(*Service)")
    fun isServiceLayerBean() { /*no-op*/ }

    /*
        execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?
     */
    @Pointcut("execution(public * com.farshonok.*.service.*Service.findById(..))")
    fun anyFindByIdServiceMethod() { /*no-op*/ }

}