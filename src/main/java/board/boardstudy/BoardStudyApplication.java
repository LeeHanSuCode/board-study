package board.boardstudy;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
public class BoardStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardStudyApplication.class, args);
	}

	@PersistenceContext
	private EntityManager em;


	//queryDSL
	@Bean
	public JPAQueryFactory jpaQueryFactory(){
		return new JPAQueryFactory(em);
	}
}
