package board.boardstudy.controller;

import board.boardstudy.dto.FileDTO;
import board.boardstudy.entity.Board;
import board.boardstudy.entity.FileStore;
import board.boardstudy.file.FileProcess;
import board.boardstudy.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileProcess fileProcess;
    private final FileService fileService;





    //파일 다운로드
    @GetMapping("/download/{uploadFilename}/{serverFilename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String uploadFilename,
                                                 @PathVariable String serverFilename)  throws MalformedURLException {
        UrlResource resource = new UrlResource("file:" + fileProcess.getFullPath(serverFilename));
        String encodeOriginalFileName = UriUtils.encode(uploadFilename, StandardCharsets.UTF_8);

        //다운을 위한 헤더.
        String contentDisposition = "attachment; filename=\"" +
                encodeOriginalFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION ,contentDisposition)
                .body(resource);
    }


}
