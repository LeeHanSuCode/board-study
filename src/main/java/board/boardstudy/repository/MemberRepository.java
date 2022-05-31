package board.boardstudy.repository;

import board.boardstudy.dto.find.FindDTO;
import board.boardstudy.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member , Long> {

    //회원 아이디로 찾기
    public Optional<Member> findByUserId(String userId);


    //전화번호로 조회
    @Query("select new board.boardstudy.dto.find.FindDTO(m.userId, m.username , m.tel , m.email) from Member m where m.tel=:tel")
    public Optional<FindDTO> findByTel(@Param("tel") String tel);


    //이메일로 조회
    @Query("select new board.boardstudy.dto.find.FindDTO(m.userId ,m.username , m.tel , m.email) from Member m where m.email=:email")
    public Optional<FindDTO> findByEmail(@Param("email")String email);


    //회원 조회시 게시글목록 한번에 조회 용도.
    @Query("select m from Member m left join fetch m.boardList where m.id=:id")
    public Optional<Member> findByFetchId(@Param("id") Long id);

}
