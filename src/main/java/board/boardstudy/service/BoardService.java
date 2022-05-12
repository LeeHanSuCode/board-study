package board.boardstudy.service;

import board.boardstudy.dto.BoardReadDTO;
import board.boardstudy.dto.BoardWriteDTO;
import board.boardstudy.entity.Board;
import board.boardstudy.entity.FileStore;
import board.boardstudy.entity.Member;
import board.boardstudy.file.FileProcess;
import board.boardstudy.repository.BoardRepository;
import board.boardstudy.repository.FileStoreRepository;
import board.boardstudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final FileStoreRepository fileStoreRepository;
    private final FileProcess fileProcess;

    //게시글 등록 -> 파일이 있으면 같이 등록
    @Transactional
    public Long register(BoardWriteDTO boardWriteDTO , Long memberId){
        //하나의 트랜잭션에서 file이 있을 경우도 동시 처리해줘야 하기 때문에 , 한곳에서 진행.
        Member findMember = memberRepository.findById(memberId);
        Board board = changeToBoard(boardWriteDTO, findMember);

        Long boardId = boardRepository.save(board);

        try {

            if (!boardWriteDTO.getFiles().isEmpty()) {
                fileSave(boardWriteDTO.getFiles(), board);
            }
        }catch (IOException e){
            e.printStackTrace();
            e.getMessage();
        }

        return boardId;
        }

        //전체목록 조회
        public List<Board> findAllBoard () {
            return boardRepository.findAllBoard();
        }


        //단순 조회

        public Board findOne (Long boardId){
            Board board = boardRepository.findOne(boardId);

            return board;
        }

        //조회수 증가 조회
        @Transactional
        public Board readOne(Long boardId){
            Board readBoard = boardRepository.findOne(boardId);
            readBoard.addReadCount();
            return readBoard;
        }


        //BoardWriteDTO -> Board
        private Board changeToBoard (BoardWriteDTO boardWriteDTO, Member member){
            return new Board(boardWriteDTO.getSubject(),member.getUsername() ,boardWriteDTO.getBoardContent(), member);
        }


        //MultipartFile -> FileStore -> 파일 저장
        private void fileSave (List<MultipartFile> multipartFiles , Board board) throws IOException{

        List < FileStore > fileStore = fileProcess.storeFiles(multipartFiles);

        for (int i = 0; i < fileStore.size(); i++) {
            fileStore.get(i).addBoardToFile(board);
            fileStoreRepository.save(fileStore.get(i));
        }
      }


      //수정
      @Transactional
      public Long update(BoardReadDTO boardReadDTO , Long boardId){
          Board findMember = boardRepository.findOne(boardId);

          /*try {

              if (!boardReadDTO.getFiles().isEmpty()) {
                  fileSave(boardReadDTO.getFiles(), findMember);
              }

          }catch (IOException e){
              e.printStackTrace();
              e.getMessage();
          }*/

          findMember.updateBoard(boardReadDTO.getBoardContent() , boardReadDTO.getSubject());

          return boardId;
      }

      //삭제
      @Transactional
      public void delete(Long boardId){
        boardRepository.remove(boardId);
      }

    }


