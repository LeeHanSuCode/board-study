package board.boardstudy.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository

public class CommentRepository {
    @PersistenceContext
    private  EntityManager em;
}
