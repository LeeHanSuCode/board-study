package board.boardstudy.repository;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.Comments;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired
    private CommentsRepository commentRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void getComment(){
        Board findBoard = boardRepository.findById(3L).get();
        List<Comments> comments = commentRepository.findByBoardId(findBoard);

        for (Comments comment : comments) {
            System.out.print("comment.getId : " + comment.getId());
            System.out.println("comment.getContent : " + comment.getCommentContent());
        }

    }

}