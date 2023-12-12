package com.example.springdemokotlin.test

import mu.KotlinLogging
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

private val log = KotlinLogging.logger {}

@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(value = [HttpMediaTypeNotSupportedException::class])
    @ResponseStatus(NOT_ACCEPTABLE)
    fun handleMediaTypeNotSupported() = Unit

    @ExceptionHandler(value = [HttpRequestMethodNotSupportedException::class])
    @ResponseStatus(METHOD_NOT_ALLOWED)
    fun handleNotSupportedHttpMethod() = Unit

    @ExceptionHandler(value = [HttpMessageNotReadableException::class, MethodArgumentNotValidException::class])
    @ResponseStatus(BAD_REQUEST)
    fun handleInvalidRequest() = Unit

    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun handleAllOtherExceptions(ex: Exception) {
        log.error(ex) { "exception occurred" }
    }
}