package com.farshonok.common.logger.starter.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Aspect
class SecondAspect {

    val log = LoggerFactory.getLogger(SecondAspect::class.java)

    @Around(
        value = """com.farshonok.common.logger.starter.aop.CommonAspect.anyFindByIdServiceMethod()
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