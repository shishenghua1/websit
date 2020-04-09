package com.boco.eoms.base.filter;

import com.boco.eoms.websit.oauth2.config.OAuth2Client;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*",filterName = "CorsFilter")
public class MyCorsFilter extends HttpServlet  implements Filter {
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
			    response.setHeader("Access-Control-Allow-Origin", "*");
			    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
			    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization,x-requested-with,websit_token,appat,apppd");
			    response.setHeader("Access-Control-Max-Age", "3600");
			    Enumeration<String> enum1 = request.getHeaderNames();
		        while (enum1.hasMoreElements()) {
		            String key = (String) enum1.nextElement();
		            if(key.equals("websit_token")) {
		            		String value = request.getHeader(key);
		            		OAuth2Client.getUserId(value);
		            }
		        }
			    if (request.getMethod()!="OPTIONS") {
			      chain.doFilter(req, res);
			    } else {
			    }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}

