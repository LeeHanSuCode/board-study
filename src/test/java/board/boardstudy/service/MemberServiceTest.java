package board.boardstudy.service;

import board.boardstudy.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void before_member() {
        Member member1 = new Member("이한수1" ,"hslee0710","1234","hslee@ndk3", "010-5456-5465" , null);;

        memberService.join(member1);
    }


    @Test
    public void 가입(){
        Member member = new Member("이한수" ,"1111","1234","hslee@ndk3", "010-5456-5465" , null);

        Long memberId = memberService.join(member);

        assertThat(member.getId()).isEqualTo(memberId);
    }

    @Test
    public void 전체조회(){
        List<Member> allMember = memberService.findAll();

        assertThat(allMember.size()).isEqualTo(1);
    }

    @Test
    public void 아이디_조회(){
        Member member = new Member("이한수" ,"1111","1234","hslee@ndk3", "010-5456-5465" , null);
        memberService.join(member);
        
        em.flush();
        em.clear();

        Member findById = memberService.findById(member.getId());

        assertThat(findById).isNotNull();
        assertThat(findById.getId()).isEqualTo(member.getId());
        assertThat(findById.getUsername()).isEqualTo(member.getUsername());
        assertThat(findById.getUserId()).isEqualTo(member.getUserId());
        assertThat(findById.getPassword()).isEqualTo(member.getPassword());
    }

    @Test
    public void 조회_예외(){
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.findById(2L));
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 회원입니다.");
    }

    @Test
    public void 유저아이디_조회(){
        Member findByUserId = memberService.findByUserId("hslee0710");

        assertThat(findByUserId).isNotNull();
        assertThat(findByUserId.getId()).isEqualTo(1L);
        assertThat(findByUserId.getUsername()).isEqualTo("이한수1");
        assertThat(findByUserId.getUserId()).isEqualTo("hslee0710");
        assertThat(findByUserId.getPassword()).isEqualTo("1234");
    }

    @Test
    public void 유저아이디_조회_예외(){
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.findByUserId("hslee"));
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 회원입니다.");
    }



}