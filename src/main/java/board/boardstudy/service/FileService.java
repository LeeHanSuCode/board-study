package board.boardstudy.service;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.FileStore;
import board.boardstudy.exception.MemberException;
import board.boardstudy.repository.FileStoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FileService {


    private final FileStoreRepository fileStoreRepository;


    //파일 단건 조회
    public FileStore findById(Long id){
        return fileStoreRepository.findById(id)
                .orElseThrow(() -> new MemberException("파일이 존재하지 않거나 이미 삭제된 파일 입니다."));
    }


    //파일 삭제
    @Transactional
    public void remove(Long fileId){
        fileStoreRepository.delete(findById(fileId));
    }


    //파일 목록 조회
    public List<FileStore> getList(Board board){
        return fileStoreRepository.findAllFile(board);
    }




}
