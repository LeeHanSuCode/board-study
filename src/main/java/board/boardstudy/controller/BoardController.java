package board.boardstudy.controller;


import board.boardstudy.dto.BoardReadDTO;
import board.boardstudy.dto.BoardWriteDTO;
import board.boardstudy.dto.CommentReadDTO;
import board.boardstudy.dto.FileDTO;
import board.boardstudy.entity.Board;
import board.boardstudy.entity.FileStore;
import board.boardstudy.file.FileProcess;
import board.boardstudy.service.BoardService;
import board.boardstudy.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;

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
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final FileProcess fileProcess;
    private final FileController fileController;


    @Value("${file.dir}")
    private String fileDir;

    //메인이자 전체 글 목록.
    @GetMapping
    public String boardHome(){
        return "/board/board_main";
    }

    //글 작성 페이지
    @GetMapping("/write")
    public String boardWriteForm(Model model){
        model.addAttribute("boardWriteDTO" , new BoardWriteDTO());
        return "/board/board_write";
    }

    //글 작성.
    //memberId를 어떻게 넘겨줄것인가
    @PostMapping("/write/{memberId}")
    public String boardWriteProcess(@Validated @ModelAttribute BoardWriteDTO boardWriteDTO
            , BindingResult bs , @PathVariable Long memberId , RedirectAttributes redirectAttributes){

        if(bs.hasErrors()){
            return "/board/board_write";
        }

        Long boardId = boardService.register(boardWriteDTO ,memberId);
        redirectAttributes.addAttribute("boardId",boardId);


        return "redirect:/board/read/{boardId}";
    }



    //게시글 보기
    @GetMapping("/read/{boardId}")
    public String boardRead(@PathVariable Long boardId , Model model){
        Board board = boardService.readOne(boardId);

        isFileStore(board , model);

        BoardReadDTO boardReadDTO = changeToBoardReadDTO(board);


        model.addAttribute("boardReadDTO" , boardReadDTO);
        return "/board/board_read";
    }

    //댓글 유무에 따른 model저장
    private void isComments(Board board , Model model){
        List<CommentReadDTO> comments = new ArrayList<>();
        if(board.getBoardComments().size()!=0){
            board.getBoardComments().stream().forEach(c -> comments.add(new CommentReadDTO(c.getId() , c.getWriter() , c.getCommentContent())));

            model.addAttribute("comments" , comments);
        }else{
            model.addAttribute("comments" , comments);
        }
    }


    //파일 유무에 따른 model저장
    private void isFileStore(Board board , Model model){
        if(board.getFileStores().size()!=0) {
            List<FileDTO> files = changeToFileDTO(board.getFileStores());
            model.addAttribute("files" , files);
        }else{
            model.addAttribute("files",new ArrayList<FileDTO>());
        }
    }


    //entity -> BoardReadDTO
    private BoardReadDTO changeToBoardReadDTO(Board board){
        BoardReadDTO boardReadDTO = new BoardReadDTO(board.getId() , board.getSubject() , board.getBoardContent() ,
                board.getWriter() , board.getCreatedDate() , board.getReadCount());
        return boardReadDTO;
    }

    //entity -> FileDTO
    private List<FileDTO> changeToFileDTO(List<FileStore> fileStores){
        List<FileDTO> file = new ArrayList<>();
        fileStores.stream().forEach(f -> file.add(new FileDTO(f.getId(),f.getUploadFilename() , f.getServerFilename())));
        return file;
    }




    @GetMapping("/update/{boardId}")
    public String updateForm(@PathVariable Long boardId ,  Model model){
        Board findBoard = boardService.findOne(boardId);

        isFileStore(findBoard,model);

        BoardReadDTO boardReadDTO = changeToBoardReadDTO(findBoard);

        model.addAttribute("boardReadDTO" , boardReadDTO);
        return "/board/board_modify";
    }


    @PostMapping("/update/{boardId}")
    public String updatePro(@Validated BoardReadDTO boardReadDTO,BindingResult bs , @PathVariable Long boardId ,
                          Model model , RedirectAttributes redirectAttributes){
        if(bs.hasErrors()){
            return "/board/board_modify";
        }

        boardService.update(boardReadDTO , boardId);

        redirectAttributes.addAttribute("boardId",boardId);
        return "redirect:/board/read/{boardId}";
    }


    @GetMapping("/remove/{boardId}")
    public String removeBoard(@PathVariable Long boardId , RedirectAttributes redirectAttributes){
        boardService.delete(boardId);

        //회원 아이디 덧붙여줘야 한다.
        return "redirect:/board";
    }



}
