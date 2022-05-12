package board.boardstudy.repository;

import board.boardstudy.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LoginRepository {

    private final EntityManager em;


}
