package board.boardstudy.service;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.FileStore;
import board.boardstudy.file.FileProcess;
import board.boardstudy.repository.BoardRepository2;
import board.boardstudy.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FileService {

    private final FileRepository fileStoreRepository;
    private final FileProcess fileProcess;


    @Transactional
    public void remove(Long fileId){
      fileStoreRepository.delete(fileId);
    }

    public List<FileStore> getList(Board board){
        return fileStoreRepository.getList(board);
    }


    //파일 저장
    public void ioFileSave(List<MultipartFile> multipartFiles, Board board) {
        try {
            List<FileStore> fileStores = fileProcess.storeFiles(multipartFiles);

            fileStores.stream().forEach(f -> f.addBoardToFile(board));
            fileStores.stream().forEach(f -> fileStoreRepository.save(f));

        } catch (IOException e) {
            log.info("boardService ioFileSave Exception");
            log.info("Exception Message ={} ", e.getMessage());

        }
    }



    //파일 삭제
    public void removeFile(List<Long> removeList) {
        removeList.stream().forEach(f -> fileStoreRepository.delete(f));
    }


    //파일 삭제시 비교작업.
    public int removeCompareId(List<Long> exist, List<Long> remove) {

        if (exist.size() != remove.size()) {

            if (remove.size() == 0) {
                removeFile(exist);
                return exist.size();
            } else {

                List<Long> collect = exist.stream()
                        .filter(e -> remove.stream().noneMatch(Predicate.isEqual(e)))
                        .collect(Collectors.toList());

                removeFile(collect);
                return collect.size();
            }
        }
        return 0;
    }

}
