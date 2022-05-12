package board.boardstudy.entity;

import board.boardstudy.entity.mappedEntity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@SequenceGenerator(
        name="FILE_SEQ_GENERATOR",
        sequenceName = "FILE_SEQ",
        initialValue = 1 , allocationSize = 1
)
public class FileStore extends BaseEntity {

    public FileStore(String uploadFilename , String serverFilename){
        this.uploadFilename = uploadFilename;
        this.serverFilename = serverFilename;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    @Id @GeneratedValue(generator = "FILE_SEQ_GENERATOR")
    @Column(name = "FILE_ID")
    private  Long id;

    private String uploadFilename;

    private String serverFilename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    //연관관계 메서드
    public void addBoardToFile(Board board){
        this.board = board;
        board.getFileStores().add(this);
    }
}
