package board.boardstudy.repository;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {

        @Query("select b from Board b where b.member=:member")
        public Page<Board> findMyBoardAll(@Param("member") Member member , Pageable pageable);
}
