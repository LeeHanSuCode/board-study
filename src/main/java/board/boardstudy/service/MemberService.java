package board.boardstudy.service;

import board.boardstudy.dto.LoginDTO;
import board.boardstudy.dto.members.MemberUpdateDTO;
import board.boardstudy.entity.Member;
import board.boardstudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    
    private final MemberRepository memberRepository;
    
    
    //가입
    @Transactional
    public Long join(Member member){
        
        memberRepository.save(member);
        return member.getId();
    }

    //전체 조회
    public List<Member> findAll(){
        return memberRepository.findAll();
    }



    //아이디로 조회
    public Member findById(Long id){
        Member findById = memberRepository.findById(id);

        if(findById==null){
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }

        return findById;
    }



    //유저 아이디로 조회
    public Member findByUserId(String userId){
        List<Member> findByUserId = memberRepository.findByUserId(userId);

        memberNullCheck(findByUserId);

        return findByUserId.get(0);
    }

    //전화번호로 조회
    public Member findByTel(String tel){
        List<Member> findByTel = memberRepository.findByTel(tel);

        memberNullCheck(findByTel);

        return findByTel.get(0);
    }

    //이메일로 조회
    public Member findByEmail(String email){
        List<Member> findByEmail = memberRepository.findByEmail(email);

        memberNullCheck(findByEmail);

        return findByEmail.get(0);
    }

    //회원 존재 여부 체크.
    private boolean memberNullCheck(List<Member> list){
        if(list.size()==0){
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        return true;
    }



    //증복 회원 체크
    public String checkUserId(String userId){
        List<Member> findByUserId = memberRepository.findByUserId(userId);

        log.info("size = {}" , findByUserId.size());

        if(findByUserId.size()==0) return "true";

        return "false";
    }

    //비밀번호 변경
    @Transactional
    public String changePw(LoginDTO loginDTO){
        Member member = memberRepository.findByUserId(loginDTO.getUserId()).get(0);
        member.changePw(loginDTO.getPassword());
        return member.getUserId();
    }



    @Transactional
    public Long updateMember(Long id , MemberUpdateDTO updateMemberDTO){
        Member findMember = memberRepository.findById(id);

        findMember.changeMemberInfo(updateMemberDTO.getPassword(),updateMemberDTO.getEmail(),updateMemberDTO.getTel());

        return findMember.getId();
    }

    @Transactional
    public void removeMember(Long id){
        memberRepository.removeMember(id);
    }
    
}
