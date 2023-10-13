package com.hw.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardEntityRepository extends JpaRepository<BoardEntity, Long>{

	BoardEntity findByBoardId (Long boardId);
}
