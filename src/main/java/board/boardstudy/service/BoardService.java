package board.boardstudy.service;

import board.boardstudy.dto.board.BoardReadDTO;
import board.boardstudy.dto.board.BoardUpdateDTO;
import board.boardstudy.dto.board.BoardWriteDTO;
import board.boardstudy.entity.Board;
import board.boardstudy.entity.FileStore;
import board.boardstudy.entity.Member;
import board.boardstudy.file.FileProcess;
import board.boardstudy.repository.BoardRepository;
import board.boardstudy.repository.FileRepository;
import board.boardstudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final FileRepository fileStoreRepository;
    private final FileProcess fileProcess;

    //게시글 등록 -> 파일이 있으면 같이 등록
    @Transactional
    public Long register(BoardWriteDTO boardWriteDTO , Long memberId){
        Member findMember = memberRepository.findById(memberId);
        Board board = changeToBoard(boardWriteDTO, findMember);

        Long boardId = boardRepository.save(board);

            if (!boardWriteDTO.getFiles().isEmpty()) {
                ioFileSave(boardWriteDTO.getFiles(), board);
             }

            return boardId;
        }


        //전체목록 조회
        public List<Board> findAllBoard () {
            return boardRepository.findAllBoard();
        }

        //나만의 게시글 조회
        public List<Board> findAllMyBoard(Long memberId){
            Member findMember = memberRepository.findById(memberId);
            return boardRepository.findAllMyBoard(findMember);
        }


        //단순 단건 조회
        public Board findOne (Long boardId){
            Board board = boardRepository.findOne(boardId);

            return board;
        }

        //조회수 증가 단건 조회
        @Transactional
        public Board readOne(Long boardId){
            Board readBoard = boardRepository.findOne(boardId);
            readBoard.addReadCount();
            return readBoard;
        }


        //BoardWriteDTO -> Board
        private Board changeToBoard (BoardWriteDTO boardWriteDTO, Member member){
            return new Board(boardWriteDTO.getSubject(),member.getUsername() ,boardWriteDTO.getBoardContent(), member);
        }



      //MultipartFile -> FileStore -> 파일 저장
      private void ioFileSave(List<MultipartFile> multipartFiles , Board board){
        try{
            List<FileStore> fileStores = fileProcess.storeFiles(multipartFiles);

            fileStores.stream().forEach(f -> f.addBoardToFile(board));
            fileStores.stream().forEach(f -> fileStoreRepository.save(f));

        }catch (IOException e){

            log.info("boardService ioFileSave Exception");
            log.info("Exception Message ={} " , e.getMessage());

        }
      }


      //파일 삭제
    private void removeFile(List<Long> removeList){
            removeList.stream().forEach(f -> fileStoreRepository.delete(f));
    }



    //파일 삭제시 비교작업.
    private int removeCompareId(List<Long> exist , List<Long> remove){

        if(exist.size() != remove.size()){

            if(remove.size() == 0) {
                removeFile(exist);
                return exist.size();
            }

            else{

                List<Long> collect = exist.stream()
                        .filter(e -> remove.stream().noneMatch(Predicate.isEqual(e)))
                        .collect(Collectors.toList());

                removeFile(collect);
                return collect.size();
            }
        }
        return 0;
    }


      //수정
      @Transactional
      public Long update(BoardUpdateDTO boardUpdateDTO , Long boardId){
          Board findBoard = boardRepository.findOne(boardId);

          if(findBoard.getFileStores().size() > 0){
              List<Long> exist = new ArrayList<>();

              findBoard.getFileStores().stream().forEach(f -> exist.add(f.getId()));
              removeCompareId(exist , boardUpdateDTO.getRemoveFiles());
          }


          if (!boardUpdateDTO.getFiles().isEmpty()) {
              ioFileSave(boardUpdateDTO.getFiles() , findBoard);
          }

          findBoard.updateBoard(boardUpdateDTO.getContent() , boardUpdateDTO.getSubject());

          return boardId;
      }



      //삭제
      @Transactional
      public void delete(Long boardId){
        boardRepository.remove(boardId);
      }



    }


