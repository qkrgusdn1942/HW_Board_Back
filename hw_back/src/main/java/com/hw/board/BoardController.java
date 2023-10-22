package com.hw.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/board/*")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping(value = "/findBoardByBoardId")
	public BoardEntity getBoardByBoardId (BoardEntity boardEntity) {
		return boardService.findByBoardId(boardEntity);
	}
	
	@GetMapping(value = "/findBoardAll")
	public List<BoardEntity> getBoardAll () {
		return boardService.findBoardAllByOrderByUpdateDateDesc();
	}
	
	@PostMapping(value = "/saveBoard")
	public void saveBoard (@RequestBody BoardEntity boardEntity) {
		boardService.saveBoard(boardEntity);
	}
	
	@PutMapping(value = "/modifyBoardByBoardId")
	public void modifyBoardByBoardId (BoardEntity boardEntity) {
		boardService.modifyBoardByBoardId(boardEntity);
	}
	
	@PutMapping(value = "/deleteBoard")
	public void deleteBoard (BoardEntity boardEntity) {
		boardService.deleteBoardByBoardId(boardEntity);
	}
}
