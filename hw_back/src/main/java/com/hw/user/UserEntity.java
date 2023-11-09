package com.hw.user;

import java.sql.Timestamp;
import java.util.Collection;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hw.dto.UserDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Builder
public class UserEntity implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	
	private String password;
	
	@Column(name = "login_id")
	private String loginId;
	
	private String name;
	private String role;
	
	@UpdateTimestamp
	@Column(name = "insert_date")
	private Timestamp insertDate;
	@UpdateTimestamp
	@Column(name = "update_date")
	private Timestamp updateDate;
	
	public static UserEntity toUserEntity (UserDto userDto) {
		return UserEntity.builder()
				.userId(userDto.getUserId())
				.loginId(userDto.getLoginId())
				.password(userDto.getPassword())
				.name(userDto.getName())
				.role(userDto.getRole())
				.insertDate(userDto.getInsertDate())
				.updateDate(userDto.getUpdateDate())
				.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
