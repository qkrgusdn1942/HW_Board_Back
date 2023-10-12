package com.hw.board;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

	@Autowired
	BoardRepository boardRepository;
	
	public Optional<BoardEntity> findByBoardId (BoardEntity entity) {
		return boardRepository.findById(entity.getBoard_id());
	}
	
}
