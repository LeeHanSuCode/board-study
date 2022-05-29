package board.boardstudy.dto.board;

import board.boardstudy.dto.CommentReadDTO;
import board.boardstudy.dto.FileDTO;
import board.boardstudy.entity.Board;
import board.boardstudy.entity.FileStore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class BoardReadDTO {

    //기본 생성자
    public BoardReadDTO(){}

    //사용자 용.
    public BoardReadDTO(Long boardId , Long memberId , String subject , String boardContent , String writer , LocalDateTime createdDate ,
                        Integer readCount){
        this.id = boardId;
        this.memberId = memberId;
        this.subject =subject;
        this.boardContent =boardContent;
        this.writer =writer;
        this.createdDate = createdDate;
        this.readCount =readCount;
    }



    private Long id;

    private String subject;

    private String boardContent;

    private String writer;

    private Long memberId;

    private LocalDateTime createdDate;

    private Integer readCount;

    private LocalDateTime updatedDate;

    private List<FileDTO> fileDTO = new ArrayList<>();


    //fileDTO 유뮤 확인 후 주입
    public void checkFileList(Board board){
        if(board.getFileStores().size() > 0){
            this.fileDTO=board.getFileStores()
                            .stream()
                            .map(b -> new FileDTO(b.getId(), b.getUploadFilename(), b.getServerFilename()))
                            .collect(Collectors.toList());


        }
    }


    //Board -> BoardReadDTO
    public BoardReadDTO changeToBoardReadDTO(Board board){
        return new BoardReadDTO(board.getId(),board.getMember().getId() , board.getSubject()
                , board.getBoardContent() , board.getWriter() , board.getCreatedDate()
                , board.getReadCount());
    }
}
