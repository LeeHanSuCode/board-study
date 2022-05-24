package board.boardstudy.repository;

import board.boardstudy.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private MemberRepository3 memberRepository;

    @Test
    public void 특정회원게시글(){
        Member findMember = memberRepository.findById(1L);
        adminRepository.allBoardMember(findMember);
    }
}