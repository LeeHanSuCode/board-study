package board.boardstudy.repository;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.Comments;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository{

    private final EntityManager em;


    //등록
    public Long registry(Comments comment){
        em.persist(comment);
        return comment.getId();
    }

    //전체 조회
    public List<Comments> allComment(Board board){

        return em.createQuery("select c  from Comments c where c.board=:board order by c.id" , Comments.class)
                .setParameter("board",board)
                .getResultList();
    }

   /* //데이터 갯수 조회
    public Integer getCount() {
        return em.createQuery("select count(c) from Comments c", Integer.class).getSingleResult();
    }*/


    //단건 조회
    public Comments findById(Long id){
        return em.find(Comments.class,id);
    }

    //삭제
    public void removeComment(Long id){
        Comments findComment = findById(id);
        em.remove(findComment);
    }
}
