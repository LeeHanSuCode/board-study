package board.boardstudy.service;

import board.boardstudy.dto.LoginDTO;
import board.boardstudy.dto.find.FindDTO;
import board.boardstudy.dto.members.MemberUpdateDTO;
import board.boardstudy.entity.Board;
import board.boardstudy.entity.Comments;
import board.boardstudy.entity.FileStore;
import board.boardstudy.entity.Member;
import board.boardstudy.exception.MemberException;
import board.boardstudy.repository.*;
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
    private final FileStoreRepository fileStoreRepository;
    private final CommentsRepository commentRepository;
    private final BoardRepository boardRepository;
    
    
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
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException("존재하지 않는 회원 입니다."));
    }


    //유저 아이디로 조회
    public Member findByUserId(String userId){
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new MemberException("존재하지 않는 회원 입니다."));
    }

    //전화번호로 조회
    public FindDTO findByTel(String tel){
        return  memberRepository.findByTel(tel)
                .orElseGet(FindDTO::new);
    }

    //이메일로 조회
    public FindDTO findByEmail(String email){
        return memberRepository.findByEmail(email)
                .orElseGet(FindDTO::new);
    }



    //증복 회원 체크
    public String checkUserId(String userId){
       if(memberRepository.findByUserId(userId).isEmpty()){
           return "true";
        }
        return "false";
    }

    //비밀번호 변경
    @Transactional
    public String changePw(LoginDTO loginDTO){
        Member findMember = memberRepository.findByUserId(loginDTO.getUserId())
                .orElseThrow(() -> new MemberException("존재하지 않는 회원 입니다."));

        findMember.changePw(loginDTO.getPassword());

        return findMember.getUserId();
    }


    //수정
    @Transactional
    public Long updateMember(Long id , MemberUpdateDTO updateMemberDTO){
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException("존재하지 않는 회원 입니다."));

        findMember.changeMemberInfo(updateMemberDTO.getPassword(), updateMemberDTO.getEmail(), updateMemberDTO.getTel());

        return findMember.getId();
    }

    //삭제
    @Transactional
    public void removeMember(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException("존재하지 않는 회원 입니다."));

        for(Board b :  member.getBoardList()){
            deletedByMember(b);
            boardRepository.delete(b);
        }

        memberRepository.delete(member);
    }

    //게시글삭제시 -> 댓글,파일 같이 삭제.
    private void deletedByMember(Board board){

        if(board.getFileStores().size()>0){
            for(FileStore f : board.getFileStores()){
                fileStoreRepository.delete(f);
            }
        }
        if(board.getComments().size() > 0){
            for(Comments c : board.getComments()){
                commentRepository.delete(c);
            }
        }
    }
    
    
}
