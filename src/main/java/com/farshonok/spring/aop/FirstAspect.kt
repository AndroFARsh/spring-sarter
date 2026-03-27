package com.farshonok.spring.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Aspect
@Component
class FirstAspect {

    val log = LoggerFactory.getLogger(FirstAspect::class.java)

    /*
        @within - check annotation on the class level
     */
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    fun isControllerLayer() { /*no-op*/ }


    /*
        within - check class type name
     */
    @Pointcut("within(com.farshonok.spring.service.*Service)")
    fun isServiceLayer() { /*no-op*/ }

    /*
        this - check AOP proxy lass type
        target - check target(concrete) object class type
     */
    @Pointcut("this(org.springframework.data.repository.support.Repositories)")
    // @Pointcut("target(org.springframework.data.repository.support.Repositories)")
    fun isRepositoryLayer() { /*no-op*/ }

    /*
        @annotation - check annotation on method level
     */
    @Pointcut("isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    fun hasGetMapping() { /*no-op*/ }

    /*
        args - check methods param type
        * - any param type
        .. - 0+ any param type
     */
    @Pointcut("isControllerLayer() && args(org.springframework.ui.Model, ..)") // at least 1 param
    // @Pointcut("args(org.springframework.ui.Model,*, *)") // 3 - parm in total
    fun hasModalParam() { /*no-op*/ }

    /*
        @args - check annotation on the param type
        * - any param type
        .. - 0+ any param type
     */
     @Pointcut("@args(com.farshonok.spring.validators.UserInfo,..)") // 3 - parm in total
    fun hasHasUserInfoParamAnnotation() { /*no-op*/ }


    /*
        bean - check name of spring bean name
     */
    @Pointcut("bean(*Service)")
    fun isServiceLayerBean() { /*no-op*/ }

    /*
        execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?
     */
    @Pointcut("execution(public * com.farshonok.spring.service.*Service.findById(..))")
    fun anyFindByIdServiceMethod() { /*no-op*/ }

    //
    // Advices: Before, After, AfterReturning, AfterThrowing, Around
    //

    @Before("""anyFindByIdServiceMethod()
        && args(id)
        && target(service)
        && @within(transactional)
    """)
    //@Before("execution(public * com.farshonok.spring.service.*Service.findById(..))")
    fun addLogging(
        joinPoint: JoinPoint,
        id: Any,
        service: Any,
        transactional: Transactional
    ) {
        log.info("invoke findById method on cass={}, with id-{} [joinPoint={}]", service, id, joinPoint)
    }
}