package com.hw.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hw.common.NullProperty;
import com.hw.dto.BoardDto;

import jakarta.transaction.Transactional;

@Service
public class BoardService {

	@Autowired
	BoardEntityRepository boardRepository;
	
	public BoardEntity findByBoardId (Long boardId) {
		return boardRepository.findByBoardId(boardId);
	}
	
	public List<BoardEntity> findBoardAll () {
		Sort sort = Sort.by(Sort.Direction.DESC, "updateDate");
		return boardRepository.findAll(sort);
	}
	
	public BoardEntity saveBoard (BoardDto boardDto) {
		return boardRepository.save(boardDto.todEntity());
	}
	
	@Transactional
	public void modifyBoardByBoardId (BoardEntity boardEntity) {
		BoardEntity entity = findByBoardId(boardEntity.getBoardId());
		NullProperty.copyProperty(boardEntity, entity);
	}
	
	public void deleteBoardByBoardId (Long boardId) {
		boardRepository.deleteById(boardId);
	}
}
