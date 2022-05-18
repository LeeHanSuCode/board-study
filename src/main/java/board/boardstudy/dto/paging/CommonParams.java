package board.boardstudy.dto.paging;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommonParams {



    private int page;           // 현재 페이지 번호
    private int pageData;  // 페이지당 출력할 데이터 개수
    private int pageNum;       // 화면 하단에 출력할 페이지 개수


    public CommonParams(int pageData ,int pageNum){
        this.pageData=pageData;
        this.pageNum=pageNum;
    }
}
