package board.boardstudy.repository;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments,Long> {

    @Query("select c from Comments c where c.board=:board")
    public List<Comments> findByBoardId(@Param("board") Board board);
}
