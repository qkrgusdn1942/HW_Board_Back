package com.hw.dto;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.hw.board.BoardEntity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class BoardDto extends BaseDto{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardId;
	
	private String title;
	private String content;
	private int isDelete;
	@CreationTimestamp
	private Timestamp insertDate;
	@UpdateTimestamp
	private Timestamp updateDate;
	private Long writer;
	
	public BoardEntity todEntity () {
		
		return BoardEntity.builder()
				.boardId(boardId)
				.writer(writer)
				.content(content)
				.isDelete(isDelete)
				.insertDate(insertDate)
				.updateDate(updateDate)
				.title(title)
				.build();
	}
}
