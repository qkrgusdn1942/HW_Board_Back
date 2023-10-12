package com.hw.board;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name = "board")
@Builder
public class BoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long board_id;
	
	private String writer;
	private String content;
	private int is_delete;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "IMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp insert_date;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "IMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp update_date;
	
}
