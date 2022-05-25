package board.boardstudy.service;


import board.boardstudy.entity.Board;
import board.boardstudy.entity.Comments;
import board.boardstudy.exception.CommentsException;
import board.boardstudy.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentRepository;
    private final BoardService boardService;

    //등록
    @Transactional
    public Long registry(Comments comment){
        commentRepository.save(comment);

        return comment.getId();
    }



    //전체 조회
    public List<Comments> allComment(Long boardId){
        Board findBoard = boardService.findOne(boardId);
        return commentRepository.findByBoardId(findBoard);
    }



    //단건 조회(댓글 수정때  필요)
    public Comments findById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(()-> new CommentsException("존재하지 않는 댓글 입니다."));
    }

    //수정
    @Transactional
    public void updateComment(String content , Long id){
        Comments findComment = commentRepository.findById(id)
                .orElseThrow(()-> new CommentsException("존재하지 않는 댓글 입니다."));

        findComment.changeContent(content);
    }

    //삭제
    @Transactional
    public void removeComment(Long id){
        Comments findComment = commentRepository
                .findById(id).orElseThrow(() -> new CommentsException("존재하지 않는 댓글 입니다."));

        commentRepository.delete(findComment);
    }



}
