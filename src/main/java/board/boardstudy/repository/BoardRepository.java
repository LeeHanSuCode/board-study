package board.boardstudy.repository;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {


    private final EntityManager em;

    //게시글 저장
    public Long save(Board board){
        em.persist(board);
        return board.getId();
    }

    //게시글 목록
    public List<Board> findAllBoard(){
        return em.createQuery("select b from Board b order by b.id desc", Board.class).getResultList();
    }

    //나만의 게시글 목록
    public List<Board> findAllMyBoard(Member member){
        return em.createQuery("select b from Board b where b.member=:member order by b.id desc ")
                .setParameter("member",member)
                .getResultList();
    }


    //게시글 보기
    public Board findOne(Long boardId){
        return em.find(Board.class , boardId);
    }

    //게시글 삭제
    public void remove(Long boardId){
        Board removeBoard = findOne(boardId);

        //파일 같이 삭제
        if(removeBoard.getFileStores().size()!=0){
            removeBoard.getFileStores().stream().forEach(f -> em.remove(f));
        }

        //댓글 같이 삭제
        if(removeBoard.getComments().size()!=0){
            removeBoard.getComments().stream().forEach(c -> em.remove(c));
        }

        em.remove(removeBoard);
    }

}
