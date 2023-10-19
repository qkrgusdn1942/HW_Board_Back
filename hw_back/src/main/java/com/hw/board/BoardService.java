package com.hw.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hw.common.NullProperty;

import jakarta.transaction.Transactional;

@Service
public class BoardService {

	@Autowired
	BoardEntityRepository boardRepository;
	
	public BoardEntity findByBoardId (BoardEntity boardEntity) {
		return boardRepository.findByBoardId(boardEntity.getBoardId());
	}
	
	public List<BoardEntity> findBoardAllByOrderByUpdateDateDesc () {
		return boardRepository.findAll();
	}
	
	public BoardEntity saveBoard (BoardEntity boardEntity) {
		return boardRepository.save(boardEntity);
	}
	
	@Transactional
	public void modifyBoardByBoardId (BoardEntity boardEntity) {
		BoardEntity entity = findByBoardId(boardEntity);
		NullProperty.copyProperty(boardEntity, entity);
	}
	
	@Transactional
	public void deleteBoardByBoardId (BoardEntity boardEntity) {
		BoardEntity entity = findByBoardId(boardEntity);
		NullProperty.copyProperty(boardEntity, entity);
		entity.setIsDelete(1);
	}
}
