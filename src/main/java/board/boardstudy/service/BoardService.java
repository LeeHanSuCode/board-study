package board.boardstudy.service;

import board.boardstudy.dto.BoardSearchCondition;
import board.boardstudy.dto.board.BoardReadDTO;
import board.boardstudy.dto.board.BoardUpdateDTO;
import board.boardstudy.dto.board.BoardWriteDTO;

import board.boardstudy.entity.Board;

import board.boardstudy.entity.Comments;
import board.boardstudy.entity.FileStore;
import board.boardstudy.entity.Member;
import board.boardstudy.exception.BoardException;

import board.boardstudy.exception.FileException;
import board.boardstudy.exception.MemberException;
import board.boardstudy.controller.file.FileProcess;
import board.boardstudy.repository.*;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    
    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final FileStoreRepository fileStoreRepository;
    private final FileProcess fileProcess;
    private final CommentsRepository commentRepository;



    //게시글 등록
    @Transactional
    public Long register(BoardWriteDTO boardWriteDTO, Long memberId) {

        //dto -> entity
        Board board = boardWriteDTO.changeToBoard(memberService.findById(memberId));

        Long boardId = boardRepository.save(board).getId();

        //파일 유뮤 -> 있으면 등록.
        if (!boardWriteDTO.getFiles().isEmpty()) {
            ioFileSave(boardWriteDTO.getFiles(), board);
        }

        return boardId;
    }


    //전체목록 조회
    public Page<BoardReadDTO> findAll(Pageable pageable) {
      return boardRepository.findAll(pageable)
                .map(b -> new BoardReadDTO(b.getId(),b.getMember().getId(),b.getSubject(),b.getBoardContent(),
                        b.getWriter(),b.getCreatedDate(),b.getReadCount()));
    }


    //나만의 게시글 조회
    public Page<BoardReadDTO> findAllMyBoard(Long memberId , Pageable pageable) {
        Member member = memberService.findById(memberId);

        return boardRepository.findMyBoardAll(member,pageable).map(b -> new BoardReadDTO(b.getId(),b.getMember().getId(),b.getSubject(),b.getBoardContent(),
                b.getWriter(),b.getCreatedDate(),b.getReadCount()));
    }

    //게시글 검색 조회
    public Page<BoardReadDTO> findCondition(BoardSearchCondition boardSearchCondition , Pageable pageable){
         return boardRepository.findSearch(boardSearchCondition, pageable)
                 .map(b -> new BoardReadDTO(b.getId(),b.getMember().getId(),b.getSubject(),b.getBoardContent(),
                b.getWriter(),b.getCreatedDate(),b.getReadCount()));
    }


    //조회수 증가 x 단건 조회
    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException("존재하지 않는 게시글입니다."));

    }

    //조회수 증가 단건 조회
    @Transactional
    public BoardReadDTO readOne(Long boardId) {

        Board findBoard = boardRepository.findByIdAndFile(boardId)
                .orElseThrow(() -> new BoardException("존재하지 않는 게시글입니다."));

        findBoard.addReadCount();

        BoardReadDTO boardReadDTO = new BoardReadDTO().changeToBoardReadDTO(findBoard);

        boardReadDTO.checkFileList(findBoard);


        return boardReadDTO;
    }

    //조회수 증가 x 단건 조회(수정 페이지 이동)
    public BoardReadDTO readModify(Long boardId){
        Board findBoard = boardRepository.findByIdAndFile(boardId)
                .orElseThrow(() -> new BoardException("존재하지 않는 게시글입니다."));

        BoardReadDTO boardReadDTO = new BoardReadDTO().changeToBoardReadDTO(findBoard);

        boardReadDTO.checkFileList(findBoard);

        return boardReadDTO;
    }


    //수정
    @Transactional
    public Long update(BoardUpdateDTO boardUpdateDTO , Long boardId) {

        Board findBoard = boardRepository.findByIdAndFile(boardId)
                .orElseThrow(() -> new BoardException("존재하지 않는 게시글입니다."));

        //파일 전부 삭제해야 할 경우.
        existFileRemove(boardUpdateDTO , findBoard);

        //일부 파일만 삭제할 경우
       removeFileByUpdate(boardUpdateDTO,findBoard);

        //새로 넘어온 파일 등록.
        if (boardUpdateDTO.getFiles().size() > 0) {
            ioFileSave(boardUpdateDTO.getFiles(), findBoard);
        }

        findBoard.updateBoard(boardUpdateDTO.getBoardContent(), boardUpdateDTO.getSubject());

        return findBoard.getId();
    }



    //전부 삭제해야 하는 경우.
    //살아남은 파일 x , 기존 게시글에는 파일 존재하는 경우.
    private void existFileRemove(BoardUpdateDTO boardUpdateDTO, Board findBoard){
        if(boardUpdateDTO.getAliveFiles().size() == 0 && findBoard.getFileStores().size() > 0) {
          
            for (FileStore f : findBoard.getFileStores()) {

                fileStoreRepository.delete(f);
            }
        }
    }


    //파일 수정
    //살아남은 파일 o , 기존의 파일과 살아남은 파일의 갯수가 같지않을 경우
    //비교하여 기존 파일에서 살아남지 못한 파일 삭제.
    private void removeFileByUpdate(BoardUpdateDTO boardUpdateDTO , Board findBoard){
        if(boardUpdateDTO.getAliveFiles().size() > 0 && findBoard.getFileStores().size() != boardUpdateDTO.getAliveFiles().size()){

            //기존 파일 목록 -> Long(id값만 추출)
            List<Long> exist = findBoard.getFileStores().stream()
                    .map(f -> f.getId().longValue())
                    .collect(Collectors.toList());

            //살아남은 파일 , 기존의 파일 비교.
            List<Long> longs = compareFile(exist, boardUpdateDTO.getAliveFiles());

            for(Long x : longs) {
                fileStoreRepository.delete(
                        fileStoreRepository.findById(x)
                                .orElseThrow(() -> new FileException("이미 삭제되었거나 존재하지 않는 파일 입니다.")));
            }
        }
    }


    //살아남은 파일과 기존의 파일 비교.
    //일치하지 않는 파일 ID 목록 반환(제거되어야 할 파일)
    private List<Long> compareFile(List<Long> exist , List<Long> remove){
        return exist.stream().filter(f -> remove.stream().noneMatch(Predicate.isEqual(f)))
                .collect(Collectors.toList());
    }



    /*파일 저장
    - MultipartFile -> FileStore
    - 파일 저장.
    */
    private void ioFileSave(List<MultipartFile> multipartFiles, Board board) {
        try {
            for(FileStore f : fileProcess.storeFiles(multipartFiles)) {
                f.addBoardToFile(board);
                fileStoreRepository.save(f);
            }

        } catch (IOException e) {
            log.info("boardService ioFileSave Exception");
            log.info("Exception Message ={} ", e.getMessage());

        }
    }



    //삭제
    //게시글에 있는 댓글과 파일 같이 삭제.
    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new FileException("이미 삭제 되었거나 ,존재하지 않는 게시글입니다."));

        deletedByBoard(board);

        boardRepository.delete(board);
    }



    //게시글 연관관계 엔티티 삭제(파일 ,댓글)
    private void deletedByBoard(Board board){
        //파일 유무
        if(board.getFileStores().size() > 0){
           fileStoreRepository.deletedByBoard(board);
        }

        //댓글 유무
        if(board.getComments().size() > 0){
            commentRepository.deletedByBoard(board);
        }
    }











}





