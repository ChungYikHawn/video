package com.hang.common;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SysExceptionResovler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        SysException ex=null;
        if (e instanceof SysException){
            ex=(SysException)e;
        }else{
            ex=new SysException("系统维护");
        }
        ModelAndView mv=new ModelAndView();
        mv.setViewName("error");
        mv.addObject("errorMsg",ex.getMessage());
        return mv;
    }
}
