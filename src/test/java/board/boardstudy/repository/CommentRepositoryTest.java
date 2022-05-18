package board.boardstudy.repository;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.Comments;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void getComment(){
        Board findBoard = boardRepository.findOne(3L);
        List<Comments> comments = commentRepository.allComment(findBoard);

        for (Comments comment : comments) {
            System.out.print("comment.getId : " + comment.getId());
            System.out.println("comment.getContent : " + comment.getCommentContent());
        }

    }

}