package com.wmjun.chianti.presentation.controllers.advice

import com.wmjun.chianti.presentation.exception.ExternalApiException
import com.wmjun.chianti.presentation.exception.InvalidKeywordException
import com.wmjun.chianti.presentation.exception.UserNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.ModelAndView


const val DEFAULT_CLIENT_ERROR_MSG = "잘못된 요청이에요."

/**
 * Spring
 */
@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    fun methodNotAllowed(e: Exception): ModelAndView {
        logger.warn("Client METHOD_NOT_ALLOWED Error ", e)

        return renderClientErrorView()
    }


    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    fun mediaTypeNotAllowed(e: Exception): ModelAndView {
        logger.warn("Client UNSUPPORTED_MEDIA_TYPE Error ", e)

        return renderClientErrorView()

    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException::class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    fun mediaTypeNotAcceptable(e: Exception): ModelAndView {
        logger.warn("Client NOT_ACCEPTABLE Error ", e)

        return renderClientErrorView()
    }


    @ExceptionHandler(value = [
        ServletRequestBindingException::class,
        MissingServletRequestParameterException::class,
        TypeMismatchException::class,
        HttpMessageNotReadableException::class,
        MethodArgumentNotValidException::class,
        MissingServletRequestPartException::class,
        BindException::class
    ])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun clientError(e: Exception): ModelAndView {
        logger.warn("Client Bad RequestError ", e)

        return renderClientErrorView()
    }


    @ExceptionHandler(InvalidKeywordException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun validationError(e: Exception): ModelAndView {
        logger.warn("Validation Error ", e)

        return renderClientErrorView(e.message ?: DEFAULT_CLIENT_ERROR_MSG)
    }

    private fun renderClientErrorView(message: String = DEFAULT_CLIENT_ERROR_MSG) =
            ModelAndView().apply {
                this.addObject("errorMsg", message)
                this.viewName = "main"
            }


    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun userNotFound(e: Exception): String {

        logger.error("UserNotFound !", e)

        return "error/user_not_found"
    }

    @ExceptionHandler(value = [ExternalApiException::class, Throwable::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun unexpectedError(e: Exception): String {
        logger.error("Unexpected Error !", e)

        return "error/error_500"
    }
}