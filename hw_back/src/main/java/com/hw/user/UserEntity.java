package com.hw.user;

import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

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
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	
	private String password;
	
	@Column(name = "login_id")
	private String loginId;
	
	@Column(name = "user_name")
	private String userName;
	
	private String role;
	
	@Column(name = "insert_date")
	private Timestamp insertDate;
	@UpdateTimestamp
	@Column(name = "update_date")
	private Timestamp updateDate;


}
