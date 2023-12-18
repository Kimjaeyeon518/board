package com.example.board.interceptor

import com.example.board.exception.BadRequestException
import com.example.board.exception.NotFoundException
import com.example.board.util.JwtUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView


@Component
class JwtInterceptor: HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {     // 요청에서 요구한 handler 가 controller 에 있는 메소드인지 확인
            val authToken = request.getHeader("Authorization")  // "Authorization" 헤더의 value 가져오기
            if (ObjectUtils.isEmpty(authToken)) {   // "Authorization" 헤더가 없을 때
                throw BadRequestException("토큰이 필요합니다.")
            } else {    // "Authorization" 헤더가 있을 때
                val usernameFromToken = JwtUtil.getUsernameFromToken(authToken) // 토큰의 유효성 확인 및 username 반환
                val session = request.session
                session.setAttribute("username", usernameFromToken)
            }
            return true
        } else {
            throw NotFoundException("404 Error")
        }
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        super.postHandle(request, response, handler, modelAndView)
    }
}