package com.boco.eoms.websit.oauth2.config;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.websit.oauth2.model.PrincipalUser;
import com.boco.eoms.websit.oauth2.model.UserAuthentication;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

@Component
public class OAuth2Client {
	
	private static Oauth2ServerProps oauth2ServerProps;
	
	@Autowired
    public OAuth2Client(Oauth2ServerProps oauth2ServerProps) {
		OAuth2Client.oauth2ServerProps=oauth2ServerProps;
	    	
    }

	private static final Logger logger = Logger.getLogger(OAuth2Client.class);
	
	public static String getRedirectUrl(HttpServletRequest request) {
		String szReturn = "";
		Properties config = OAuthUtils
				.getClientConfigProps(OAuthConstants.CONFIG_FILE_PATH);

		// Generate the OAuthDetails bean from the config properties file
		OAuth2Details oauthDetails = OAuthUtils.createOAuthDetails(config);
		String code = StaticMethod.nullObject2String(request
				.getParameter("code"));
		if (code.equals("")
				&& ((HttpServletRequest) request).getSession().getAttribute(
						"sessionform") == null) {

			String urlStr = ((HttpServletRequest) request).getRequestURL()
					.toString();
			String queryStr = StaticMethod
					.nullObject2String(((HttpServletRequest) request)
							.getQueryString());
			if (!queryStr.equals("")) {
				queryStr = "?" + queryStr;
			}
			szReturn = oauthDetails.getUserAuthorizationUri() + "?"
					+ oauthDetails.getClientIdName() + "="
					+ oauthDetails.getClientIdValue() + "&"
					+ oauthDetails.getRedirectUriName() + "=" + urlStr
					+ queryStr + "&response_type=code&state="
					+ testRandomString(8).get(0);
		}
		return szReturn;
	}

	public static String getUserId(String token) {
		String szReturn = "";
		if (!token.equals("")) {
			HttpClient client = new HttpClient();
			String url = "http://"+oauth2ServerProps.getIp()+":"+oauth2ServerProps.getPort()+"/v1/user/me";
			GetMethod get = new GetMethod(url);
			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			// nameValuePairList.add(new NameValuePair("state",state));

			NameValuePair[] nameValuePairArray = new NameValuePair[nameValuePairList
					.size()];
			nameValuePairList.toArray(nameValuePairArray);

			get.setQueryString(nameValuePairArray);
			get.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
					"UTF-8");
			try {
				List<Header> headers = new ArrayList<Header>();
				headers.add(new Header("Authorization", OAuthConstants.BEARER
						+ " " + token));
				headers.add(new Header("Accept", OAuthConstants.JSON_CONTENT));
				client.getHostConfiguration().getParams().setParameter(
						"http.default-headers", headers);
				int status = client.executeMethod(get);
				if (status >= 400) {
					throw new RuntimeException(
							"Could not access protected resource. Server returned http code: "
									+ status);
				}
				
				logger.debug(status);
				String result = get.getResponseBodyAsString() != null ? get
						.getResponseBodyAsString().trim() : "";
				logger.debug(result);
				
				PrincipalUser user = JSONObject.toJavaObject(JSONObject.parseObject(result), PrincipalUser.class);
				UserAuthentication userAuthentication = JSONObject.toJavaObject(JSONObject.parseObject(result), UserAuthentication.class);
				szReturn = user.getName();
				SecurityContextHolder.getContext().setAuthentication(userAuthentication);
				return szReturn;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		return szReturn;
	}

	public static List<String> testRandomString(int order) {
		// 创建List<String>
		List<String> ls = new ArrayList<String>();
		String ku = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		/*
		 * 定义一个StringBuilder用以保存生成的字符串，这里不选用String和StringBuffer（String长度
		 * 不可变，StringBuffer没有StringBuilder快）
		 */
		StringBuilder newStr = new StringBuilder();
		// 创建一个Random用以生成伪随机数，也可采用Math.random()来实现
		Random r = new Random();
		for (int j = 0; j < order; j++) {
			do {
				// 将newStr置空
				newStr.delete(0, newStr.length());
				// 得到字符串长度的随机数
				int r1 = r.nextInt(10) + 1;
				for (int i = 0; i < r1; i++) {
					// 得到随机字符
					int r2 = r.nextInt(ku.length());
					newStr.append(ku.charAt(r2));
				}
			} while (ls.contains(newStr.toString()));
			ls.add(newStr.toString());
		}
		return ls;
	}
}
