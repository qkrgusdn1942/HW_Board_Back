package com.hw.board;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/board/*")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping(value = "/findByBoardId")
	public Optional<BoardEntity> getBoardByBoardId (BoardEntity entity) {
		return boardService.findByBoardId(entity);
	}
}
