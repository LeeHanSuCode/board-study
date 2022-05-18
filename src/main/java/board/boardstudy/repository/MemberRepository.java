package board.boardstudy.repository;

import board.boardstudy.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    //회원 가입
    public Long save(Member member){
            em.persist(member);
            return member.getId();
    }

    //회원 단건 조회(db id)
    public Member findById(Long id){
        return em.find(Member.class, id);
    }

    //회원 단건 조회(userId)
    public List<Member> findByUserId(String userId){

       return em.createQuery("select m from Member m where m.userId = :userId", Member.class)
                .setParameter("userId", userId).getResultList();
    }

    //회원 단건 조회(tel)
    public List<Member> findByTel(String tel){
        return em.createQuery("select m from Member m where m.tel = :tel" , Member.class)
                .setParameter("tel" , tel)
                .getResultList();
    }

    //회원 단건 조회(email)
    public List<Member> findByEmail(String email){
        return em.createQuery("select m from Member m where m.email = :email" , Member.class)
                .setParameter("email", email)
                .getResultList();
    }


    //회원 전체 조회
    public List<Member> findAll(){
        return em.createQuery("select m from Member m" , Member.class).getResultList();
    }

    //회원 삭제
    public Long removeMember(Long id){
        Member findMember = findById(id);
        em.remove(findMember);

        //나중에 java 복습하다가 Optional로 감싸서 값을 리턴하는 것으로 리팩토링 할 떄,
        //해당 id값이 db에 남아있는지 아닌지 확인하는 과정을 처리해 주자.

        return id;
    }



    //동적쿼리 -> 관리자모드로 접근시 사용
    //정렬 기준과 조건을 이용한 회원 조회
    //정렬 -> 수정 날짜 기준 , 가입 날짜 기준
    //조건 -> 검색창에 입력한 아이디와 일치하는 회원 , 혹은 시퀀스 번호.


}
