package board.boardstudy.repository;

import board.boardstudy.dto.BoardSearchCondition;
import board.boardstudy.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface BoardRepositoryCustom {

        Page<Board> findSearch(BoardSearchCondition condition , Pageable pageable);
}
