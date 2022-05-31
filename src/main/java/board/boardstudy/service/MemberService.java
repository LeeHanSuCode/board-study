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
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    
    private final MemberRepository memberRepository;
    private final FileStoreRepository fileStoreRepository;
    private final BoardRepository boardRepository;
    private final CommentsRepository commentsRepository;
    
    //회원 가입
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

    /*로그인시 유저 아이디로 조회
      로그인시 회원이 없는 경우 properties에 등록된 메세지로 처리해주기 위한 용도.*/
    public Optional<Member> loginFindUserId(String userId){
        return memberRepository.findByUserId(userId);
    }


    //전화번호로 조회(아이디 & 비밀번호 찾기 용도)
    public FindDTO findByTel(String tel){
        return  memberRepository.findByTel(tel)
                .orElseGet(FindDTO::new);
    }

    //이메일로 조회(아이디 & 비밀번호 찾기 용도)
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


    /*
    비밀번호 변경
    비밀번호 찾기에서 인증된 회원이 비밀번호 변경할 때 사용.
    */
    @Transactional
    public String changePw(LoginDTO loginDTO){
        Member findMember = memberRepository.findByUserId(loginDTO.getUserId())
                .orElseThrow(() -> new MemberException("존재하지 않는 회원 입니다."));

        findMember.changePw(loginDTO.getPassword());

        return findMember.getUserId();
    }


    //수정 처리
    @Transactional
    public Long updateMember(Long id , MemberUpdateDTO updateMemberDTO){
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException("존재하지 않는 회원 입니다."));

        findMember.updateEntity(updateMemberDTO.getPassword(), updateMemberDTO.getEmail(), updateMemberDTO.getTel());

        return findMember.getId();
    }

    /*
    * 삭제 작업
    * 게시글 같이 삭제.
    * 게시글에 작성된 댓글 및 파일 같이 삭제
    * 회원이 작성한 댓글 또한 같이 삭제.
    * */
    @Transactional
    public void removeMember(Long id){
        Member member = memberRepository.findByFetchId(id)
                .orElseThrow(() -> new MemberException("존재하지 않는 회원 입니다."));

        //회원이 작성항 게시글의 파일과 ,
        //그 게시글에 작성한 다른 유저들의 댓글 삭제.
        for(Board b :  member.getBoardList()){
            deletedByBoard(b);
            boardRepository.delete(b);
        }

        //회원이 작성한 댓글 삭제
        deletedByMember(member);

        memberRepository.delete(member);
    }

    //삭제되는 게시글과 연관된 파일과 댓글 삭제
    private void deletedByBoard(Board board){
        //게시글 삭제
        if(board.getFileStores().size()>0){
            fileStoreRepository.deletedByBoard(board);
        }

        //댓글 삭제
        if(board.getComments().size() > 0){
            commentsRepository.deletedByBoard(board);
        }
    }


    //삭제되는 회원과 연관된 댓글 삭제
    private void deletedByMember(Member member){
        if(member.getCommentsList().size() > 0){
            commentsRepository.deletedByMember(member);
        }
    }

}
