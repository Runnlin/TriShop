package cn.hstc.trishop.service;

import cn.hstc.trishop.pojo.FileProperties;
import cn.hstc.trishop.result.FileException;
import cn.hstc.trishop.result.UploadFileResponse;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.processing.FilerException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileService {
    private final Path fileStorageLocation; // 文件在本地存储位置

    @Autowired
    public FileService(FileProperties fileProperties) {
        this.fileStorageLocation = Paths.get(fileProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * 存储文件
     *
     * @Param file 文件
     * @return 文件名
     */
    public String storeFile(MultipartFile file) {
        String fileName = RandomStringUtils.randomAlphabetic(6)+StringUtils.cleanPath(file.getOriginalFilename());
        // 设置文件名最大长度10
        if (fileName.length() > 10)
            fileName = fileName.substring(10);

        try {
            if (fileName.contains("..")) {
                throw new FileException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // copy file to the target location (Overwrite the same name file)
            Path targetLoacation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLoacation, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("store File Path： "+fileStorageLocation);
            return fileName;
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new FileException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    /**
     * 加载文件
     * @param fileName 文件名
     * @return 文件
     */
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resoutce = new UrlResource(filePath.toUri());
            if (resoutce.exists()) {
                return resoutce;
            } else {
                throw new FileException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileException("File not found " + fileName, ex);
        }
    }

    /**
     * 上传单个文件
     * @param file 文件对象
     * @return 上传结果
     */
    public UploadFileResponse upload(MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("上传文件为空");
            return null;
        }
        if (file.getSize() > 1024 * 1024) {
            System.out.println("上传文件过大");
            return null;
        }
        String fileName = storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    /**
     * 下载单个文件
     * @param fileName 目标文件名
     * @param request 请求
     * @return 包含目标文件信息的实体
     */
    public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            // 获取到请求的上下文，得到请求文件的类型
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Could not determine file type."+e.getMessage());
        }

        // Fallback to the default content type if type could not be determined
        if (null == contentType) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
