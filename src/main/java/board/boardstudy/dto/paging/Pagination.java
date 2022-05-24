package board.boardstudy.dto.paging;

import lombok.Getter;

@Getter
public class Pagination {
    private long totalBoardCount; //전체 데이터수
    private int totalPageCount;   //전체 페이지수

    private int pageSize;       //페이지 버튼 수
    private int pageNum;        //현재 페이지 번호

    private int startPage;         //첫페이지 번호
    private int endPage;        //끝페이지 번호
    private boolean existPrevPage;  //이전페이지 존재 여부
    private boolean existNextPage;   //다음페이지 존재 여부



    public Pagination(int pageNum ,int totalPageCount, long totalBoardCount , int pageSize){
       this.pageNum = pageNum;
       this.totalPageCount = totalPageCount;
        this.totalBoardCount = totalBoardCount;
        this.pageSize = pageSize;

       calculation(pageNum, totalPageCount, pageSize);
    }

    private void calculation(int pageNum , int totalPageCount,int pageSize){

        if(pageNum % 10 != 0){
            startPage = (pageNum/pageSize)*pageSize+1;
        }else{
            startPage = (pageNum/pageSize-1)*pageSize+1;
        }

        endPage = startPage + pageSize-1;
        if(endPage > totalPageCount) endPage = totalPageCount;



        // 이전 페이지 존재 여부 확인
        if(startPage > pageSize) existPrevPage = true;

        // 다음 페이지 존재 여부 확인
        if(endPage < totalPageCount) existNextPage = true;

    }
}
