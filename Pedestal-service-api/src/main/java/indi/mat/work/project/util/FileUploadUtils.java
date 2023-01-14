package indi.mat.work.project.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mat
 * @version : FileUploadUtils, v 0.1 2023-01-14 22:35 Yang
 */

@Component
public class FileUploadUtils {
    private final Path path = Paths.get("D:\\BaiduNetdiskDownload");

    public void upload(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                System.out.println("文件为空！");
                return;
            }
            Files.copy(file.getInputStream(), path.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stream<Path> list() {
        try {
            return Files.walk(this.path, 1)
                    .filter(path -> !path.equals(this.path))
                    .map(this.path::relativize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = path.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                System.out.println("读取文件失败！");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
