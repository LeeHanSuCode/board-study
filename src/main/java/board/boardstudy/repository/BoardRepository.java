package board.boardstudy.repository;

import board.boardstudy.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        return em.createQuery("select b from board b").getResultList();
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
            for(int i=0 ; i<removeBoard.getFileStores().size() ; i++){
                em.remove(removeBoard.getFileStores().get(i));
            }
        }

        //댓글 같이 삭제
        if(removeBoard.getBoardComments().size()!=0){
            for(int i=0 ; i<removeBoard.getBoardComments().size() ; i++){
                em.remove(removeBoard.getBoardComments().get(i));
            }
        }


        em.remove(removeBoard);
    }

}
