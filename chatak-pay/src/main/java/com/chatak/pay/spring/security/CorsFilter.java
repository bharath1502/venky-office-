/**
 * 
 */
package com.chatak.pay.spring.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 05-Mar-2015 11:25:14 AM
 * @version 1.0
 */
public class CorsFilter extends OncePerRequestFilter {

  /**
   * @param arg0
   * @param arg1
   * @param arg2
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // CORS "pre-flight" request
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
    response.addHeader("Access-Control-Allow-Headers", "Authorization");
    response.addHeader("Access-Control-Max-Age", "1");// 30 min
    //      response.addHeader("WWW-Authenticate", "Basic realm=\"Chatak Services\"");// 30 min
    filterChain.doFilter(request, response);
  }

}
