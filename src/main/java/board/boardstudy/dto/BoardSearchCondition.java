package board.boardstudy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardSearchCondition {

    private String keyword;
    private String subject;
    private String writer;
}
