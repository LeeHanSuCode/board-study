package board.boardstudy.repository;

import board.boardstudy.dto.find.FindDTO;
import board.boardstudy.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member , Long> {

    public Optional<Member> findByUserId(String userId);

    @Query("select new board.boardstudy.dto.find.FindDTO(m.userId, m.username , m.tel , m.email) from Member m where m.tel=:tel")
    public Optional<FindDTO> findByTel(@Param("tel") String tel);

    @Query("select new board.boardstudy.dto.find.FindDTO(m.userId ,m.username , m.tel , m.email) from Member m where m.email=:email")
    public Optional<FindDTO> findByEmail(@Param("email")String email);


}
