package board.boardstudy.repository;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.Comments;
import board.boardstudy.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments,Long> {

    @Query("select c from Comments c  where c.board=:board order by c.createdDate desc ")
    public List<Comments> findByBoardId(@Param("board") Board board);

    @Modifying(clearAutomatically = true)
    @Query("delete from Comments c where c.member =:member")
    public int deletedByMember(@Param("member")Member member);

    @Modifying(clearAutomatically = true)
    @Query("delete from Comments c where c.board =:board")
    public int deletedByBoard(@Param("board")Board board);
}
