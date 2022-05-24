package board.boardstudy.repository;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AdminRepository {

    private final EntityManager em;

    //모든 게시글 조회
    public List<Board> allBoard(int start , int end){
            return em.createQuery("select b from Board b order by b.id desc", Board.class)
                    .setFirstResult(start)
                    .setMaxResults(end)
                    .getResultList();

    }

    //게시글 단건 조회(조회수 증가x)
    public List<Board> findBoard(Long boardId){
        return em.createQuery("select b from Board b where b.id =:id",Board.class)
                .setParameter("id",boardId)
                .getResultList();
    }

    //게시글 삭제
    public void removeBoard(Board board){
        em.remove(board);
    }

    //특정 회원 게시글 목록
    public List<Board> allBoardMember(Member member){
        return em.createQuery("select b from Board b where b.member=:member",Board.class)
                .setParameter("member",member)
                .getResultList();
    }



    //모든 회원 조회
    public List<Member> allMember(){
        return em.createQuery("select m from Member m ",Member.class).getResultList();
    }

    //특정 회원 보기
    public List<Member> findMember(Long memberId){
        return em.createQuery("select m from Member m where m.id=:id",Member.class)
                .setParameter("id",memberId)
                .getResultList();
    }

    //회원 삭제
    public void removeMember(Member member){
        em.remove(member);
    }




}
