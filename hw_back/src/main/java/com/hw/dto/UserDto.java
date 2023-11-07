package com.hw.dto;

import java.sql.Timestamp;

import com.hw.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseDto {

	private Long userId;
	private String password;
	private String loginId;
	private String userName;
	private String role;
	private Timestamp insertDate;
	private Timestamp updateDate;
	
	public static UserDto toUserDto (UserEntity userEntity) {
		return UserDto.builder()
				.userId(userEntity.getUserId())
				.loginId(userEntity.getLoginId())
				.password(userEntity.getPassword())
				.userName(userEntity.getUserName())
				.role(userEntity.getRole())
				.insertDate(userEntity.getInsertDate())
				.updateDate(userEntity.getUpdateDate())
				.build();
	}
	
}
