package com.amos.think.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.amos.think.security.GrantedAuthorityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * 自定义JWT认证过滤器 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

	private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authentication;
		try {
			authentication = getAuthentication(request, response);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		} catch (Exception e) {
			logger.info("---->JWTAuthenticationFilter::doFilterInternal Exception " + e.getMessage());
			// e.printStackTrace();
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// long start = System.currentTimeMillis();
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty()) {
			// throw new Exception("Token为空");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().println(JSON.parse("{\"code\":-10,\"msg\":\"Token为空\"}"));
			response.getWriter().flush();
		}
		// parse the token.
		String user = null;
		try {
			Claims claims = Jwts.parser().setSigningKey("spring-security-@Jwt!&Secret^#").parseClaimsJws(token.replace("Bearer ", "")).getBody();
			// token签发时间
			long issuedAt = claims.getIssuedAt().getTime();
			// 当前时间
			long currentTimeMillis = System.currentTimeMillis();
			// token过期时间
			long expirationTime = claims.getExpiration().getTime();
			// 1. 签发时间 < 当前时间 < (签发时间+((token过期时间-token签发时间)/2)) 不刷新token
			// 2. (签发时间+((token过期时间-token签发时间)/2)) < 当前时间 < token过期时间 刷新token并返回给前端
			// 3. tokne过期时间 < 当前时间 跳转登录，重新登录获取token
			// 验证token时间有效性
			String refreshToken = "";
			if ((issuedAt + ((expirationTime - issuedAt) / 2)) < currentTimeMillis && currentTimeMillis < expirationTime) {
				// 重新生成token start
				Calendar calendar = Calendar.getInstance();
				Date now = calendar.getTime();
				// 设置签发时间
				calendar.setTime(new Date());
				// 设置过期时间
				calendar.add(Calendar.MINUTE, 15);// 5分钟
				Date time = calendar.getTime();
				refreshToken = Jwts.builder()
				.setSubject(claims.getSubject())
				.setIssuedAt(now)//签发时间
				.setExpiration(time)//过期时间
				.signWith(SignatureAlgorithm.HS512, "spring-security-@Jwt!&Secret^#") //采用什么算法是可以自己选择的，不一定非要采用HS512
				.compact();
				refreshToken = "Bearer " + refreshToken;
				// 重新生成token end
				
				// 主动刷新token，并返回给前端
			}
			response.setHeader("Access-Control-Expose-Headers", "refreshToken");
			response.addHeader("refreshToken", refreshToken);
			// long end = System.currentTimeMillis();
			// logger.info("执行时间: {}", (end - start) + " 毫秒");
			user = claims.getSubject();
			logger.info("-->user:" + user);
			if (user != null) {
				String[] split = user.split("-")[1].split(",");
				ArrayList<GrantedAuthority> authorities = new ArrayList<>();
				for (int i=0; i < split.length; i++) {
					authorities.add(new GrantedAuthorityImpl(split[i]));
				}
				return new UsernamePasswordAuthenticationToken(user, null, authorities);
			}
		} catch (ExpiredJwtException e) {
			// logger.error("Token已过期: {} " + e);
			// throw new Exception("Token已过期");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().println(JSON.parse("{\"code\":-1,\"msg\":\"Token已过期\"}"));
			response.getWriter().flush();
		} catch (UnsupportedJwtException e) {
			// logger.error("Token格式错误: {} " + e);
			// throw new Exception("Token格式错误");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().println(JSON.parse("{\"code\":-2,\"msg\":\"Token格式错误\"}"));
			response.getWriter().flush();
		} catch (MalformedJwtException e) {
			// logger.error("Token没有被正确构造: {} " + e);
			// throw new Exception("Token没有被正确构造");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().println(JSON.parse("{\"code\":-3,\"msg\":\"Token没有被正确构造\"}"));
			response.getWriter().flush();
		} catch (SignatureException e) {
			// logger.error("签名失败: {} " + e);
			// throw new Exception("签名失败");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().println(JSON.parse("{\"code\":-4,\"msg\":\"签名失败\"}"));
		} catch (IllegalArgumentException e) {
			// logger.error("非法参数异常: {} " + e);
			// throw new Exception("非法参数异常");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().println(JSON.parse("{\"code\":-5,\"msg\":\"非法参数异常\"}"));
		}
		return null;
	}

}
