/**
 * 
 */
package kr.letech.study.cmmn.sec.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.letech.study.cmmn.vo.UserRoleVO;
import kr.letech.study.user.vo.UserVO;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * < 개정이력 >
 * 
 *  수정일			수정자			수정내용
 *  ------------------------------------------------
 *  2025-09-16		KCY				최초 생성
 */
public class UserDetailsVO implements UserDetails {
	private static final long serialVersionUID = 7802127817738492259L;
	
	private String username;
	private String password;
	private List<GrantedAuthority> authorities;
	
	private UserVO userVO;
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true	;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setAuthorities(List<String> authList) {
		
		List<GrantedAuthority> authorities = new ArrayList<>();
			
		for (int i=0; i< authList.size(); i++) {
			authorities.add(new SimpleGrantedAuthority(authList.get(i)));
		}
		
		this.authorities = authorities;
	}
	
	//0930_야매
	public void setAuthoritiesForVO(List<UserRoleVO> authList) {
		
		List<GrantedAuthority> authorities = new ArrayList<>();
			
		for (int i=0; i< authList.size(); i++) {
			authorities.add(new SimpleGrantedAuthority(authList.get(i).getUserRole()));
		}
		
		this.authorities = authorities;
	}

	public void setVo(UserVO userVO) {
		this.userVO = userVO;
	}

	/**
	 * @param username
	 * @param password
	 * @param authorities
	 * @param userVO
	 */
	public UserDetailsVO() {}
	public UserDetailsVO(String username, String password, List<GrantedAuthority> authorities, UserVO userVO) {
		super();
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.userVO = userVO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userVO == null) ? 0 : userVO.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetailsVO other = (UserDetailsVO) obj;
		if (authorities == null) {
			if (other.authorities != null)
				return false;
		} else if (!authorities.equals(other.authorities))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userVO == null) {
			if (other.userVO != null)
				return false;
		} else if (!userVO.equals(other.userVO))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDetailVO [username=" + username + ", password=" + password + ", authorities=" + authorities
				+ ", userVO=" + userVO + "]";
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	
	
}
