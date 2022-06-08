package board.boardstudy.controller;


import board.boardstudy.argument.Login;
import board.boardstudy.dto.*;
import board.boardstudy.dto.board.BoardReadDTO;
import board.boardstudy.dto.board.BoardUpdateDTO;
import board.boardstudy.dto.board.BoardWriteDTO;
import board.boardstudy.dto.paging.Pagination;
import board.boardstudy.service.BoardService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;





    //게시판 메인(전체 글 목록)
    @GetMapping
    public String boardHome(@PageableDefault(size = 10 , page = 0 , sort = "id",direction = Sort.Direction.DESC) Pageable pageable, Model model){

        Page<BoardReadDTO> boardList = boardService.findAll(pageable);

        model.addAttribute("allBoard" ,boardList.getContent());
        model.addAttribute("page",new Pagination(boardList.getNumber()+1, boardList.getTotalPages(), boardList.getTotalElements(), 10));

        return "/board/board_main";
    }

    //게시글 목록(조건)
    @GetMapping("/condition")
    public String boardCondition(@PageableDefault(size = 10 , page = 0 , sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                                 BoardSearchCondition boardSearchCondition,
                                 Model model){

        Page<BoardReadDTO> boardList = boardService.findCondition(boardSearchCondition,pageable);

        model.addAttribute("allBoard" ,boardList.getContent());
        model.addAttribute("page",new Pagination(boardList.getNumber()+1, boardList.getTotalPages(), boardList.getTotalElements(), 10));

        return "/board/board_main";
    }



    //내가 작성한 게시글 목록 보기
    @GetMapping("/myBoard")
    public String myBoard(@Login LoginDTO loginDTO , Model model, @PageableDefault(size = 10 , page = 0 , sort = "id",direction = Sort.Direction.DESC) Pageable pageable){

        Page<BoardReadDTO> boardList = boardService.findAllMyBoard(loginDTO.getId(),pageable);

        model.addAttribute("allMyBoard", boardList.getContent());
        model.addAttribute("page",new Pagination(boardList.getNumber()+1, boardList.getTotalPages(), boardList.getTotalElements(), 10));

        return "/board/board_myBoard";
    }


    //게시글 보기
    @GetMapping("/read/{boardId}/{memberId}")
    public String boardRead(@PathVariable Long boardId,@PathVariable Long memberId , Model model){
        BoardReadDTO boardReadDTO = boardService.readOne(boardId);

        model.addAttribute("boardReadDTO" ,boardReadDTO);

        return "/board/board_read";
    }



    //글쓰기 페이지로 이동.
    @GetMapping("/write")
    public String boardWriteForm(Model model){
        model.addAttribute("boardWriteDTO" , new BoardWriteDTO());
        return "/board/board_write";
    }


    //글쓰기 등록.
    @PostMapping("/write")
    public String boardWriteProcess(@Validated @ModelAttribute BoardWriteDTO boardWriteDTO
            , BindingResult bs
            , @Login LoginDTO loginDTO
            ,RedirectAttributes redirectAttributes){

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
        BoardReadDTO boardReadDTO = boardService.readModify(boardId);

        model.addAttribute("boardUpdateDTO", boardReadDTO);

        return "/board/board_modify";
    }
    

    //글 수정 처리
    @PostMapping("/update/{boardId}")
    public String updatePro(@Validated BoardUpdateDTO boardUpdateDTO,BindingResult bs , @PathVariable Long boardId ,
                         @Login LoginDTO loginDTO, RedirectAttributes redirectAttributes){
        //Bean Validation 체크
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
    public String removeBoard(@PathVariable Long boardId){
        boardService.delete(boardId);

        return "redirect:/board";
    }



}
