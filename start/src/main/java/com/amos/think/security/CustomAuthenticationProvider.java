package com.amos.think.security;

import java.util.ArrayList;
import com.alibaba.cola.dto.Response;
import com.amos.think.api.IUserService;
import com.amos.think.dto.query.UserLoginQuery;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义身份认证验证组件
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private UserDetailsService userDetailsService;

	// private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// @Resource
  private IUserService userService;

	public CustomAuthenticationProvider(UserDetailsService userDetailsService, IUserService userService){
		this.userDetailsService = userDetailsService;
		// this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userService = userService;
	}

  /**
	*执行与以下合同相同的身份验证
	* {@link org.springframework.security.authentication.AuthenticationManager＃authenticate（Authentication）}
	*。
	*
	* @param authentication身份验证请求对象。
	*
	* @返回包含凭证的经过完全认证的对象。 可能会回来
	* <code> null </ code>（如果<code> AuthenticationProvider </ code>无法支持）
	* 对传递的<code> Authentication </ code>对象的身份验证。 在这种情况下，
	* 支持所提供的下一个<code> AuthenticationProvider </ code>
	* 将尝试<code> Authentication </ code>类。
	*
	* @throws AuthenticationException如果身份验证失败。
	*/
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 获取认证的用户名 & 密码
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		// 认证逻辑
		// 替换Spring security 认证逻辑
		UserLoginQuery userLoginQuery = new UserLoginQuery();
		userLoginQuery.setUsername(name);
		userLoginQuery.setPassword(password);
		Response res = userService.login(userLoginQuery);
		System.out.println("-->login res:" + res.toString());
		if (res.isSuccess()){
			Authentication auth = new UsernamePasswordAuthenticationToken(name, password);
			return auth;
		} else {
			throw new BadCredentialsException("登陆失败:" + res.getErrMessage());
		}
		/** Spring security 默认认证逻辑
		UserDetails userDetails = userDetailsService.loadUserByUsername(name);
		if (null == userDetails) {
			throw new UsernameNotFoundException("用户不存在");
		} else {
			System.out.println("-->password match : " + password + "|" + userDetails.getPassword());
			if(name.equals(userDetails.getUsername()) && password.equals(userDetails.getPassword())){
				ArrayList<GrantedAuthority> authorities = new ArrayList<>();
				authorities.add( new GrantedAuthorityImpl("ROLE_ADMIN"));
				authorities.add( new GrantedAuthorityImpl("AUTH_WRITE"));
				// 生成令牌 这里令牌里面存入了:name,password,authorities, 当然你也可以放其他内容
				Authentication auth = new UsernamePasswordAuthenticationToken(name, password, authorities);
				return auth;
			}else {
				throw new BadCredentialsException("密码错误");
			}
		}**/   
	}

	/**
	 * 是否可以提供输入类型的认证服务
	 * @param authentication
	 * @return
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}