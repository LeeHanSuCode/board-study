package board.boardstudy.service;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.FileStore;
import board.boardstudy.repository.BoardRepository;
import board.boardstudy.repository.FileStoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional

class FileServiceTest {

    @Autowired
    private FileStoreRepository fileStoreRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void 파일삭제(){
        Board board = boardRepository.findById(301L).get();

        FileStore fileStore = board.getFileStores().get(0);

        fileStoreRepository.delete(fileStore);
    }
}