package com.farshonok.spring.aop

import com.querydsl.core.util.MathUtils.result
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import kotlin.contracts.Returns

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

    /**
        Advices: Before, After, AfterReturning, AfterThrowing, Around

        Lifecycle:
        @Before
        try {
            method
            @AfterReturning
        } catch {
            @AfterThrowing
        } final {
            @After
        }
    **/


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
        log.info("Before: - invoke findById method on class={}, with id={} [joinPoint={}]", service, id, joinPoint)
    }

    @AfterReturning(
        value = """anyFindByIdServiceMethod()
            && args(id)
            && target(service)
            && @within(transactional)
        """,
        returning = "result"
    )
    fun addLoggingAfterReturning(
        joinPoint: JoinPoint, // may be skipped
        result: Any,
        id: Any,
        service: Any,
        transactional: Transactional

    ) {
        log.info("AfterReturn: invoke findById method on class={}, with id={} [result={}]", service, id, result)
    }

    @AfterThrowing(
        value = """anyFindByIdServiceMethod()
            && args(id)
            && target(service)
            && @within(transactional)
        """,
        throwing = "error"
    )
    fun addLoggingAfterThrowing(
        error: Throwable,
        id: Any,
        service: Any,
        transactional: Transactional

    ) {
        log.error("AfterThrowing: invoke findById method on class={}, with id={} [e={}]", service, id, error)
    }

    @After(
        value = """anyFindByIdServiceMethod()
            && args(id)
            && target(service)
            && @within(transactional)
        """,
    )
    fun addLoggingAfterFinally(
        joinPoint: JoinPoint,
        id: Any,
        service: Any,
        transactional: Transactional

    ) {
        log.info("After: invoke findById method on class={}, with id={} [jp={}]", service, id, joinPoint)
    }

    @Around(
        value = """anyFindByIdServiceMethod()
            && args(id)
            && target(service)
        """,
    )
    fun addLoggingAround(
        joinPoint: ProceedingJoinPoint,
        id: Any,
        service: Any,
    ): Any {
        log.info("AROUND Before: - invoke findById method on class={}, with id={}", service, id)
        try {
            val result = joinPoint.proceed()
            log.info("AROUND AfterReturn: invoke findById method on class={}, with id={} [result={}]", service, id, result)
            return result
        } catch (e: Throwable) {
            log.error("AROUND AfterThrowing: invoke findById method on class={}, with id={} [e={}]", service, id, e)
            throw e
        } finally {
            log.info("AROUND After: invoke findById method on class={}, with id={} [jp={}]", service, id, joinPoint)
        }
    }
}