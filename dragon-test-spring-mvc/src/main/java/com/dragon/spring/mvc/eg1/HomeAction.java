package com.dragon.spring.mvc.eg1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * 这是最原始的mvc做法，继承Controller接口，先从原始的说起，最后过渡到@Controller和RequestMapping注解式的配置。
 * 它在mvc-servlet.xml文件中的配置有一个关键的属性name="/index"。
 *
 */
public class HomeAction implements Controller {

	/**
	 * 原理：
	 * 第一步首先要找到是哪个对象，即handler，本工程的handler则是HomeAction对象。
	 * 第二步要找到访问的函数，即HomeAction的handleRequest方法。所以就出现了两个源码接口HandlerMapping和HandlerAdapter，前者
	 * 负责第一步，后者负责第二步。
	 * 
	 * HandlerMapping接口的实现：
	 * 1、BeanNameUrlHandlerMapping：通过对比url和bean的name找到对应的对象。
	 * 2、SimpleUrlHandlerMapping：也是直接配置url和对应bean，比BeanNameUrlHandlerMapping功能更多
	 * 3、RequestMappingHandlerMapping：主要是针对注解配置@RequestMapping的
	 * 
	 * HandlerAdapter接口的实现：
	 * 1、HttpRequestHandlerAdapter：要求handler实现HttpRequestHandler接口。
	 * 2、SimpleControllerHandlerAdapter：要求handler实现Controller接口，该接口的方法为ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
	 * 3、RequestMappingHandlerAdapter：和上面的RequestMappingHandlerMapping配对使用，针对@RequestMapping
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("hello");
	}

}