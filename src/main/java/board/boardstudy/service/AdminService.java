package board.boardstudy.service;

import board.boardstudy.dto.admin.AdminMember;
import board.boardstudy.entity.Board;
import board.boardstudy.entity.Member;
import board.boardstudy.repository.BoardRepository2;
import board.boardstudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;
    private final BoardRepository2 boardRepository;

   /* //모든 게시글 조회
    public List<Board> allBoard(int start , int end){
        return adminRepository.allBoard(start,end);
    }

    //게시글 단건 조회(조회수 증가x)
    public Board findBoard(Long boardId){
        List<Board> board = adminRepository.findBoard(boardId);

        isExistBoard(board);

        return board.get(0);
    }

    //게시글 삭제
    @Transactional
    public void removeBoard(Long boardId){
        List<Board> board = adminRepository.findBoard(boardId);

        isExistBoard(board);

        adminRepository.removeBoard(board.get(0));

    }


    //특정 회원 게시글 목록
    public List<Board> allBoardMember(Long memberId){
        List<Member> member = adminRepository.findMember(memberId);

        isExistMember(member);

        return adminRepository.allBoardMember(member.get(0));
    }*/


    //모든 회원 조회
    public Page<AdminMember> allMember(Pageable pageable){
        return memberRepository.findAll(pageable).map(m -> new AdminMember(m.getId() ,m.getUsername(),m.getTel()
        ,m.getEmail(),m.getUserId(),m.getKnownRoot(),m.getCreatedDate(),m.getUpdatedDate()));
    }

 /*   //회원 보기
    public Member findMember(Long memberId){
        List<Member> member = adminRepository.findMember(memberId);

        isExistMember(member);

        return member.get(0);
    }


    //회원 삭제
    @Transactional
    public int removeMember(Long memberId){
        List<Member> member = adminRepository.findMember(memberId);

        isExistMember(member);

        adminRepository.removeMember(member.get(0));

        return 1;
    }*/



    //게시글 존재 여부
    private void isExistBoard(List<Board> board){
        if(board.size() == 0){
            throw new IllegalArgumentException("존재하지 않는 게시글 입니다.");
        }
    }


    //회원 존재 여부
    private void isExistMember(List<Member> members){
        if(members.size() == 0){
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }
}
