package com.hang.controller;


import com.hang.common.enums.VideoStatusEnum;
import com.hang.common.utils.JSONResult;
import com.hang.common.utils.PagedResult;
import com.hang.pojo.Bgm;
import com.hang.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("video")
public class VideoController {

    @Autowired
    private VideoService videoService;


    @GetMapping("/showReportList")
    public String showReportList() {
        return "video/reportList";
    }

    @PostMapping("/reportList")
    @ResponseBody
    public PagedResult reportList() {
        System.out.println(1);
        PagedResult result = videoService.queryReportList(1, 10);
        return result;
    }

    @PostMapping("/forbidVideo")
    @ResponseBody
    public JSONResult forbidVideo(String videoId) {
        return videoService.updateVideoStatus(videoId, VideoStatusEnum.FORBID.value);
    }

    @GetMapping("/showBgmList")
    public String showBgmList() {
        return "video/bgmList";
    }

    @PostMapping("/queryBgmList")
    @ResponseBody
    public PagedResult queryBgmList(Integer page) {
        return videoService.queryBgmList(page, 10);
    }

    @GetMapping("/showAddBgm")
    public String showAddBgm() {
        return "video/addBgm";
    }

    @PostMapping("/addBgm")
    @ResponseBody
    public JSONResult addBgm(Bgm bgm) {
        bgm.setPath(bgm.getPath().replace("\\","/"));
        int i = videoService.addBgm(bgm);
        return i==1?JSONResult.ok():JSONResult.errorMsg("上传失败");
    }

    @PostMapping("/delBgm")
    @ResponseBody
    public JSONResult delBgm(String bgmId) {
        return videoService.deleteBgm(bgmId);
    }

    @PostMapping("/bgmUpload")
    @ResponseBody
    public JSONResult bgmUpload(@RequestParam("file") MultipartFile[] files) throws Exception {
        return videoService.bgmUpload(files);
    }
}
