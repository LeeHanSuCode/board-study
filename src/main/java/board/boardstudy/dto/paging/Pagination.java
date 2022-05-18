package board.boardstudy.dto.paging;

import lombok.Getter;

@Getter
public class Pagination {
    private int totalCommentCount; //전체 데이터수

    private int totalPageCount;   //전체 페이지수
    private int startPage;         //첫페이지 번호
    private int endPage;        //끝페이지 번호
    private boolean existPrevPage;  //이전페이지 존재 여부
    private boolean existNextPage;   //다음페이지 존재 여부

    private int pagePerComment;//한페이지당 댓글 갯수
    private int pageSize;
    private int pageNum;
    private int startNum;
    private int endNum;



    public Pagination(int pageNum ,int pagePerComment, int totalCommentCount , int pageSize){
       this.pageNum = pageNum;
       this.pagePerComment = pagePerComment;
        this.totalCommentCount = totalCommentCount;
        this.pageSize = pageSize;

       calculation(pageNum, pagePerComment , totalCommentCount, pageSize);
    }

    private void calculation(int pageNum , int pagePerComment , int totalCommentCount,int pageSize){
        //가져올 데이터
        startNum = (pageNum-1)*pagePerComment;
        endNum = pagePerComment;


        //전체 페이지 수 계산
        totalPageCount = totalCommentCount/pagePerComment + (totalCommentCount%pagePerComment == 0 ? 0:1);

        if(pageNum % 10 != 0){
            startPage = (pageNum/pageSize)*pageSize+1;
        }else{
            startPage = (pageNum/pageSize-1)*pageSize+1;
        }

        endPage = startPage + pageSize-1;
        if(endPage > totalPageCount) endPage = totalCommentCount;



        // 이전 페이지 존재 여부 확인
        if(startPage > pageSize) existPrevPage = true;

        // 다음 페이지 존재 여부 확인
        if(endPage < totalPageCount) existNextPage = true;

    }
}
