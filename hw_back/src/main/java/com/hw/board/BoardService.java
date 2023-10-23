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
	
	public BoardEntity findByBoardId (Long boardId) {
		return boardRepository.findByBoardId(boardId);
	}
	
	public List<BoardEntity> findBoardAllByOrderByUpdateDateDesc () {
		return boardRepository.findAll();
	}
	
	public BoardEntity saveBoard (BoardEntity boardEntity) {
		return boardRepository.save(boardEntity);
	}
	
	@Transactional
	public void modifyBoardByBoardId (BoardEntity boardEntity) {
		BoardEntity entity = findByBoardId(boardEntity.getBoardId());
		NullProperty.copyProperty(boardEntity, entity);
	}
	
	@Transactional
	public void deleteBoardByBoardId (BoardEntity boardEntity) {
		BoardEntity entity = findByBoardId(boardEntity.getBoardId());
		NullProperty.copyProperty(boardEntity, entity);
		entity.setIsDelete(1);
	}
}
