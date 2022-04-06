package com.clluv.poc.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest

class NFTNotFoundException : Exception()

@ControllerAdvice
class NFTErrorHandler {
    @ExceptionHandler(NFTNotFoundException::class)
    fun handledNFTNotFoundException(
        servletRequest: HttpServletRequest,
        exception: Exception
    ): ResponseEntity<String> = ResponseEntity("NFT not found", HttpStatus.NOT_FOUND)
}