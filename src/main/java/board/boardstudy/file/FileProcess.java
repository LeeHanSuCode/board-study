package board.boardstudy.file;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.FileStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileProcess {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename){

        return fileDir + filename;
    }

    //여러개의 파일저장
    public List<FileStore> storeFiles(List<MultipartFile> multipartFiles) throws IOException{
        List<FileStore> storeFileResult = new ArrayList<>();

        for(MultipartFile multipartFile : multipartFiles){
            if(!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    //한개의 파일만 저장할 경우.
    public FileStore storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();

        String storeFileName = createStoreFileName(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new FileStore(originalFilename , storeFileName);
    }

    //확장자 추출하기.
    //하는 이유? -> 저장된 파일이 어느 형식의 파일인지 파일명만 보고 파악하기 용이하게끔 하기위해서서
   private String extract(String originalFilename){
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    //서버에 저장할 파일명 생성.
    private String createStoreFileName(String originalFilename){
        String uuid = UUID.randomUUID().toString();
        String ext = extract(originalFilename);
        return uuid + "." + ext;
    }
}
