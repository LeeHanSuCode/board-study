package board.boardstudy.service;

import board.boardstudy.dto.CommentReadDTO;
import board.boardstudy.dto.paging.Pagination;
import board.boardstudy.entity.Board;
import board.boardstudy.entity.Comments;
import board.boardstudy.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardService boardService;

    //등록
    @Transactional
    public Long registry(Comments comment){
        return commentRepository.registry(comment);
    }



    //전체 조회
    public List<Comments> allComment(Long boardId){
        Board findBoard = boardService.findOne(boardId);

        return commentRepository.allComment(findBoard);
    }




    //단건 조회
    public Comments findById(Long id){
        return commentRepository.findById(id);
    }

    //수정
    @Transactional
    public void updateComment(String content , Long id){
        Comments findComment = commentRepository.findById(id);
        findComment.changeContent(content);
    }

    //삭제
    @Transactional
    public void removeComment(Long id){
        commentRepository.removeComment(id);
    }



}
