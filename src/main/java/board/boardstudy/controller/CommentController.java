package board.boardstudy.controller;

import board.boardstudy.argument.Login;
import board.boardstudy.dto.CommentReadDTO;
import board.boardstudy.dto.LoginDTO;
import board.boardstudy.dto.board.BoardReadDTO;
import board.boardstudy.entity.Board;
import board.boardstudy.entity.Comments;
import board.boardstudy.entity.Member;
import board.boardstudy.service.BoardService;
import board.boardstudy.service.CommentService;
import board.boardstudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final BoardService boardService;

       //등록
      @PostMapping("/{boardId}")
      public String writeComment(@PathVariable Long boardId, @RequestParam String comment,
                                 @Login LoginDTO loginDTO) {

          Member findMember = memberService.findById(loginDTO.getId());

          Board findBoard = boardService.findOne(boardId);

          if (commentService.registry(new Comments(comment, findMember.getUserId(), findBoard, findMember)) == null) {
              return "false";
          }

          return "true";
      }


      //댓글 조회
      @GetMapping("/{boardId}")
      public List<CommentReadDTO> commentList(@PathVariable Long boardId){
          return changeToCommentReadDTO_List(commentService.allComment(boardId));
      }



      //댓글 수정
    @PatchMapping("/{commentId}")
    public String updateComment(@PathVariable Long commentId ,@RequestParam String content){
          commentService.updateComment(content,commentId);
          return "true";
    }


    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Long commentId){
          commentService.removeComment(commentId);
        return "true";
    }


    //CommentsList -> CommentReadDTOList
    private List<CommentReadDTO> changeToCommentReadDTO_List(List<Comments> comments){
        List<CommentReadDTO> list = new ArrayList<>();

        if(comments.size() > 0) {
            comments.stream().forEach(c -> list.add(new CommentReadDTO(c.getId(),
                    c.getMember().getId(),c.getWriter(), c.getCommentContent(), c.getCreatedDate())));
        }

        return list;
    }



}
