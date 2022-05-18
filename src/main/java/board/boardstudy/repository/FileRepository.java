package board.boardstudy.repository;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileRepository {

    private final EntityManager em;

    public Long save(FileStore fileStore){
        em.persist(fileStore);
        return fileStore.getId();
    }

    public void delete(Long id){
        FileStore fileStore = em.find(FileStore.class, id);
        em.remove(fileStore);
    }

    public List<FileStore> getList(Board board){
        return em.createQuery("select f from FileStore f where f.board=:board")
                .setParameter("board",board)
                .getResultList();
    }

}
