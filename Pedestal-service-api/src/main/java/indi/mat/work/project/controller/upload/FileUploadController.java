package indi.mat.work.project.controller.upload;

import indi.mat.work.project.util.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mat
 * @version : FileUploadController, v 0.1 2023-01-14 22:38 Yang
 */
@Controller
@RequestMapping("/api/upload")
public class FileUploadController {

    @Autowired
    private FileUploadUtils fileUploadUtils;

    @GetMapping
    public String index(Model model) {
        return "/upload.html";
    }

    @GetMapping("/filesInfo")
    @ResponseBody
    public List<String> filesInfo() {
        List<String> ans =   fileUploadUtils
                .list()
                .map(path -> MvcUriComponentsBuilder
                        .fromMethodName(FileUploadController.class, "download", path.getFileName().toString())
                        .build()
                        .toString())
                .collect(Collectors.toList());
        return ans;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        Resource file = fileUploadUtils.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file) {
        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            return "redirect:/api/upload";
        }
        fileUploadUtils.upload(file);
        return "redirect:/api/upload";
    }


    @PostMapping("/multipleUpload")
    public String multipleUpload(@RequestParam("files") MultipartFile[] files) {
        if(files == null || files.length == 0) return "redirect:/api/upload";
        for(MultipartFile file : files) {
            if (StringUtils.isEmpty(file.getOriginalFilename())) {
                continue;
            }
            fileUploadUtils.upload(file);
        }

        return "redirect:/api/upload";
    }
}
