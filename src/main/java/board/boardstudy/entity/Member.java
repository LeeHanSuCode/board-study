package board.boardstudy.entity;

import board.boardstudy.dto.JoinMemberDTO;
import board.boardstudy.dto.UpdateMemberDTO;
import board.boardstudy.entity.mappedEntity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@SequenceGenerator(
        name="MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //DB시퀀스이름
        initialValue = 1 , allocationSize = 1)

//성능 고려해서 allocationSize 을 10을 주어 한번에 10씩 당겨오자.

public class Member extends BaseEntity {

    public Member(String username , String userId , String password , String email , String tel , @Nullable String knownRoot){
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.memberGrade = MemberGrade.NORMAL;

        if(knownRoot != null){
            this.knownRoot = knownRoot;
        }
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }


    @Id @GeneratedValue(generator = "MEMBER_SEQ_GENERATOR")
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    private String userId;

    private String password;

    private String email;

    private String tel;

    private String knownRoot;

    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade;

    @OneToMany(mappedBy = "member")
    private List<Board> boardList = new ArrayList<>();




    //비밀번호 변경
    public void changePw(String password){
        this.password = password;
    }



}
