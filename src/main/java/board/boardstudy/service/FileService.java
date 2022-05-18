package board.boardstudy.service;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.FileStore;
import board.boardstudy.repository.BoardRepository;
import board.boardstudy.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final FileRepository fileStoreRepository;
    private final BoardRepository boardRepository;


    @Transactional
    public void remove(Long fileId){
      fileStoreRepository.delete(fileId);
    }

    public List<FileStore> getList(Board board){
        return fileStoreRepository.getList(board);
    }
}
