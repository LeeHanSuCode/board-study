package board.boardstudy.repository;

import board.boardstudy.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.Nullable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {


    @Autowired
    private MemberRepository memberRepository;
    @PersistenceContext
    private EntityManager em;


    @BeforeEach
    public void before_member(){
        Member member1 = new Member("이한수1" ,"hslee0711","1234","hslee@ndk1","010-5456-5465" , null);
        Member member2 = new Member("이한수2" ,"hslee0712","1234","hslee@ndk2","010-5456-5465" , null);
        Member member3 = new Member("이한수3" ,"hslee0713","1234","hslee@ndk3", "010-5456-5465" , null);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

    }


    @Test
    public void 회원저장(){

        Member member = new Member("이한수" ,"hslee","1234","hslee@ndk3", "010-5456-5465" , null);

        Long findById = memberRepository.save(member);

        assertThat(member.getId()).isEqualTo(findById);
    }

    @Test
    public void 회원아이디_조회(){
        Member member = new Member("이한수" ,"hslee","1234","hslee@ndk3", "010-5456-5465" , null);

        Long findId = memberRepository.save(member);

        em.flush();
        em.clear();;

        Member findMember = memberRepository.findById(findId);

        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember.getUserId()).isEqualTo(member.getUserId());
    }

    @Test
    public void 전체회원_조회(){
        List<Member> findAll = memberRepository.findAll();

        assertThat(findAll.size()).isEqualTo(3);
    }

    @Test
    public void 유저아이디_조회(){
        Member findByUserId = memberRepository.findByUserId("hslee0711").get(0);

        assertThat(findByUserId).isNotNull();
        assertThat(findByUserId.getUserId()).isEqualTo("hslee0711");
    }


}