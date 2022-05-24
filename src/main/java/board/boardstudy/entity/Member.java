package board.boardstudy.entity;

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
        sequenceName = "MEMBER_SEQ",
        initialValue = 1 , allocationSize = 1)

public class Member extends BaseEntity {

    public Member(String username , String userId , String password , String email , String tel , @Nullable String knownRoot){
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.memberGrade = MemberGrade.NORMAL;

        if(knownRoot == null){
            this.knownRoot = "선택 안함";
        } else{
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

    @OneToMany(mappedBy = "member" , orphanRemoval = true , cascade = CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();


    //비밀번호 변경
    public void changePw(String password){
        this.updatedDate=LocalDateTime.now();
        this.password = password;
    }


    //회원 정보 수정
    public void changeMemberInfo(String password , String email , String tel){
        this.email = email;
        this.tel = tel;
        this.password = password;
        this.updatedDate = LocalDateTime.now();
    }



}
