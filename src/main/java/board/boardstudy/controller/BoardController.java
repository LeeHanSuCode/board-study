package board.boardstudy.controller;


import board.boardstudy.argument.Login;
import board.boardstudy.dto.*;
import board.boardstudy.dto.board.BoardReadDTO;
import board.boardstudy.dto.board.BoardUpdateDTO;
import board.boardstudy.dto.board.BoardWriteDTO;
import board.boardstudy.dto.paging.Pagination;
import board.boardstudy.entity.Board;
import board.boardstudy.service.BoardService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;



    @Value("${file.dir}")
    private String fileDir;


    //게시판 메인(전체 글 목록)
    @GetMapping
    public String boardHome(@RequestParam(defaultValue = "0") int pageNum , Model model){

        Page<BoardReadDTO> boardList = boardService.findAll(PageRequest.of(pageNum, 10, Sort.by("id").descending()));

        model.addAttribute("allBoard" ,boardList.getContent());
        model.addAttribute("page",new Pagination(boardList.getNumber()+1, boardList.getTotalPages(), boardList.getTotalElements(), 10));

        return "/board/board_main";
    }



    //내가 작성한 게시글 목록 보기
    @GetMapping("/myBoard")
    public String myBoard(@Login LoginDTO loginDTO , Model model, @RequestParam(defaultValue = "0") int pageNum){

        Page<BoardReadDTO> boardList = boardService.findAllMyBoard(loginDTO.getId(), PageRequest.of(pageNum, 10, Sort.by("id").descending()));

        model.addAttribute("allMyBoard", boardList.getContent());
        model.addAttribute("page",new Pagination(boardList.getNumber()+1, boardList.getTotalPages(), boardList.getTotalElements(), 10));

        return "/board/board_myBoard";
    }


    //게시글 보기
    @GetMapping("/read/{boardId}/{memberId}")
    public String boardRead(@PathVariable Long boardId,@PathVariable Long memberId , Model model){
        Board board = boardService.readOne(boardId);

        model.addAttribute("boardReadDTO" ,changeToBoardReadDTO(board));
        model.addAttribute("fileDTO" , isFileStore(board));

        return "/board/board_read";
    }



    //글쓰기 페이지
    @GetMapping("/write")
    public String boardWriteForm(Model model){
        model.addAttribute("boardWriteDTO" , new BoardWriteDTO());
        return "/board/board_write";
    }


    //글쓰기 등록.
    @PostMapping("/write")
    public String boardWriteProcess(@Validated @ModelAttribute BoardWriteDTO boardWriteDTO
            , BindingResult bs , @Login LoginDTO loginDTO, RedirectAttributes redirectAttributes){

        if(bs.hasErrors()){
            return "/board/board_write";
        }

        Long boardId = boardService.register(boardWriteDTO, loginDTO.getId());

        redirectAttributes.addAttribute("boardId",boardId);
        redirectAttributes.addAttribute("memberId",loginDTO.getId());

        return "redirect:/board/read/{boardId}/{memberId}";
    }
 


    //글 수정페이지 이동.
    @GetMapping("/update/{boardId}")
    public String updateForm(@PathVariable Long boardId ,  Model model){
        Board findBoard = boardService.findOne(boardId);

        model.addAttribute("boardUpdateDTO", changeToBoardUpdateDTO(findBoard));
        model.addAttribute("fileDTO",isFileStore(findBoard));

        return "/board/board_modify";
    }
    

    //글 수정 처리
    @PostMapping("/update/{boardId}")
    public String updatePro(@Validated BoardUpdateDTO boardUpdateDTO,BindingResult bs , @PathVariable Long boardId ,
                         @Login LoginDTO loginDTO, RedirectAttributes redirectAttributes){
        if(bs.hasErrors()){
            return "/board/board_modify";
        }


        boardService.update(boardUpdateDTO,boardId);

        redirectAttributes.addAttribute("boardId",boardId);
        redirectAttributes.addAttribute("memberId",loginDTO.getId());

        return "redirect:/board/read/{boardId}/{memberId}";
    }


    //글 삭제
    @GetMapping("/remove/{boardId}")
    public String removeBoard(@PathVariable Long boardId , RedirectAttributes redirectAttributes){
        boardService.delete(boardId);

        return "redirect:/board";
    }



    //Board -> BoardUpdateDTO
    private BoardUpdateDTO changeToBoardUpdateDTO(Board board){
        return new BoardUpdateDTO(board.getId(),board.getWriter(),board.getSubject(),board.getBoardContent());
    }

    //Board -> BoardReadDTO
    private BoardReadDTO changeToBoardReadDTO(Board board){
        return new BoardReadDTO(board.getId(),board.getMember().getId() , board.getSubject()
                , board.getBoardContent() , board.getWriter() , board.getCreatedDate()
                , board.getReadCount());
    }
    
    //FileStore -> FileDTO
    private List<FileDTO> isFileStore(Board board){
        List<FileDTO> files = new ArrayList<>();

        if(board.getFileStores().size()!=0) {
            board.getFileStores().stream().forEach(f -> files.add(new FileDTO(f.getId(),f.getUploadFilename(),f.getServerFilename())));
        }
        return files;
    }

}
