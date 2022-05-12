package board.boardstudy.repository;

import board.boardstudy.entity.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class FileStoreRepository {

    private final EntityManager em;

    public Long save(FileStore fileStore){
        em.persist(fileStore);
        return fileStore.getId();
    }

    public void delete(Long id){
        FileStore fileStore = em.find(FileStore.class, id);
        em.remove(fileStore);
    }

}
