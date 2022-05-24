package board.boardstudy.service;

import board.boardstudy.dto.board.BoardReadDTO;
import board.boardstudy.dto.board.BoardUpdateDTO;
import board.boardstudy.dto.board.BoardWriteDTO;

import board.boardstudy.entity.Board;

import board.boardstudy.entity.Member;
import board.boardstudy.exception.BoardException;

import board.boardstudy.repository.BoardRepository;

import board.boardstudy.repository.FileRepository;
import board.boardstudy.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BoardService {

    
    private final BoardRepository boardRepository;
    private final FileService fileService;
    private final MemberService memberService;


    //게시글 등록
    @Transactional
    public Long register(BoardWriteDTO boardWriteDTO, Long memberId) {
        
        Board board = changeToBoard(boardWriteDTO, memberService.findById(memberId));

        Long boardId = boardRepository.save(board).getId();

        if (!boardWriteDTO.getFiles().isEmpty()) {
            fileService.ioFileSave(boardWriteDTO.getFiles(), board);            //파일 유뮤 -> 있으면 등록.
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
    

    //조회수 증가 x 단건 조회
    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException("존재하지 않는 게시글입니다."));
        
    }

    //조회수 증가 단건 조회
    @Transactional
    public Board readOne(Long boardId) {

        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException("존재하지 않는 게시글입니다."));

        findBoard.addReadCount();

        return findBoard;
    }


    //수정
    @Transactional
    public Long update(BoardUpdateDTO boardUpdateDTO, Long boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException("존재하지 않는 게시글입니다."));

        //기존 파일과 비교하여 삭제.
        if (findBoard.getFileStores().size() > 0) {
            List<Long> exist = new ArrayList<>();

            findBoard.getFileStores().stream().forEach(f -> exist.add(f.getId()));
            fileService.removeCompareId(exist, boardUpdateDTO.getRemoveFiles());
        }

        //새로 넘어온 파일 등록.
        if (!boardUpdateDTO.getFiles().isEmpty()) {
            fileService.ioFileSave(boardUpdateDTO.getFiles(), findBoard);
        }

        findBoard.updateBoard(boardUpdateDTO.getContent(), boardUpdateDTO.getSubject());

        return boardId;
    }


    //삭제
    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException("이미 삭제 되었거나 ,존재하지 않는 게시글입니다."));
        
        boardRepository.delete(board);
    }
    


    
    //BoardWriteDTO -> Board
    private Board changeToBoard(BoardWriteDTO boardWriteDTO, Member member) {
        return new Board(boardWriteDTO.getSubject(), member.getUsername(), boardWriteDTO.getBoardContent(), member);
    }


    


   


}





