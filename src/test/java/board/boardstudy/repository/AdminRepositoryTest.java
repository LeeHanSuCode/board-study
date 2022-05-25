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



}