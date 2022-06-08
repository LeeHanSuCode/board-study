package board.boardstudy.repository;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.FileStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileStoreRepository extends JpaRepository<FileStore,Long> {


    @Query("select f from FileStore f where f.board=:board")
    public List<FileStore> findAllFile(@Param("board") Board board);

    @Modifying(clearAutomatically = true)
    @Query("delete from FileStore f where f.board = :board")
    public int deletedByBoard(@Param("board") Board board);


}
