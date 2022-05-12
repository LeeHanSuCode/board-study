package board.boardstudy.entity;

import board.boardstudy.entity.mappedEntity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Null;
import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@SequenceGenerator(
        name="BOARD_SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ", //DB시퀀스이름
        initialValue = 1 , allocationSize = 1
)
public class Board extends BaseEntity {

    public Board(String subject ,String writer ,String boardContent , Member member){
        this.writer = writer;
        this.subject = subject;
        this.boardContent = boardContent;
        this.readCount = 0;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();

        //회원 연관관계 메서드 호출
        addMemberToBoard(member);
    }

    @Id @GeneratedValue(generator = "BOARD_SEQ_GENERATOR")
    @Column(name = "BOARD_ID")
    private Long id;

    private String subject;

    private String writer;

    private Integer readCount;


    @Lob
    private String boardContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<BoardComment> boardComments = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<FileStore> fileStores = new ArrayList<>();

    //연관관계 메서드
    public void addMemberToBoard(Member member){
        this.member = member;
        member.getBoardList().add(this);
    }

    //조회수 증가 메서드
    public void addReadCount(){
            this.readCount+=1;
    }


    //수정 메서드
    public void updateBoard(String boardContent, String subject){
        this.updatedDate = LocalDateTime.now();
        this.boardContent = boardContent;
        this.subject = subject;
    }

}
