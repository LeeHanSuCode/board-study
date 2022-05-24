package board.boardstudy.controller;

import board.boardstudy.dto.FileDTO;
import board.boardstudy.dto.admin.AdminMember;
import board.boardstudy.dto.board.BoardReadDTO;

import board.boardstudy.dto.paging.Pagination;
import board.boardstudy.entity.Board;

import board.boardstudy.entity.Member;
import board.boardstudy.repository.BoardRepository;
import board.boardstudy.repository.MemberRepository;
import board.boardstudy.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

/*    //전체 게시글 보기
    @GetMapping("/board")
    public String boardManagement(Model model , @RequestParam(defaultValue = "1") int pageNum){
        List<BoardReadDTO> boardList = new ArrayList<>();

        Pagination page = boardService.getPage(pageNum);

        adminService.allBoard(page.getStartNum(),page.getEndNum()).stream().forEach(b -> boardList.add(changeToBoardReadDTO(b,b.getMember().getId())));

        model.addAttribute("page",page);
        model.addAttribute("allBoard",boardList);

        return "admin/board_main";
    }*/



 /*   //게시글 보기
    @GetMapping("/read/{boardId}/{memberId}")
    public String boardRead(@PathVariable Long boardId,@PathVariable Long memberId , Model model){
        Board board = boardService.readOne(boardId);

        model.addAttribute("boardReadDTO" , changeToBoardReadDTO(board , memberId));
        model.addAttribute("fileDTO" , isFileStore(board));

        return "/admin/board_read";
    }*/


    //FileStore -> FileDTO
    private List<FileDTO> isFileStore(Board board){
        List<FileDTO> files = new ArrayList<>();

        if(board.getFileStores().size()!=0) {
            board.getFileStores().stream().forEach(f -> files.add(new FileDTO(f.getId(),f.getUploadFilename(),f.getServerFilename())));
        }
        return files;
    }

    //Board -> BoardReadDTO
    private BoardReadDTO changeToBoardReadDTO(Board board , Long memberId){
        return new BoardReadDTO(board.getId(),memberId , board.getSubject()
                , board.getBoardContent() , board.getWriter() , board.getCreatedDate()
                , board.getReadCount());
    }



    //게시글 삭제
    @PostMapping("/board/delete/{boardId}")
    public String boardRemove(@PathVariable Long boardId){
        return "admin/board_main";
    }

    //해당 회원  보기.
    @GetMapping("/board/{boardId}")
    public String memberBoard(@PathVariable Long boardId , RedirectAttributes redirectAttributes){
        return "redirect:/admin/member/info/{memberId}";
    }


    //회원 전체 보기
    @GetMapping("/member")
    public String memberList(@RequestParam(defaultValue = "0") int pageNum , Model model){

        Page<AdminMember> memberList = adminService.allMember(PageRequest.of(pageNum, 10, Sort.by("id").descending()));

        model.addAttribute("allMember" , memberList.getContent());
        model.addAttribute("page",new Pagination(memberList.getNumber()+1, memberList.getTotalPages(), memberList.getTotalElements(), 10));
        return "/admin/memberList";
    }



  /* @PostConstruct
   public void test(){
        adminService.MemberList();
   }
*/

/*    //회원 정보
    @GetMapping("/member/info/{memberId}")
    public String memberInfo(@PathVariable Long memberId , Model model){

        Member member = adminService.findMember(memberId);

        model.addAttribute("memberInfoDTO" ,changeToAdminMember(member));

        return "/admin/memberInfo";
    }*/

 /*   //Member -> MemberInfoDTO
    private AdminMember changeToAdminMember(Member member){

        String root= " ";

        if(member.getKnownRoot() == null) {
            root = "선택하지 않음";
        } else{
            root= member.getKnownRoot();
        }

        return new AdminMember(member.getId(),member.getUsername() ,member.getTel() ,member.getEmail() , member.getUserId()
        ,root);
    }
*/

    //회원이 작성한 게시글 보기
    @GetMapping("/member/{memberId}")
    public String memberBoard(@PathVariable Long memberId){
        return "/admin/board_main";
    }


    //회원 삭제
    @GetMapping("/member/delete/{memberId}")
    public String removeMember(@PathVariable Long memberId){
        return "/admin/memberList";
    }

}
