package com.hang.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hang.common.enums.BGMOperatorTypeEnum;
import com.hang.common.org.n3r.idworker.Sid;
import com.hang.common.utils.JSONResult;
import com.hang.common.utils.JsonUtils;
import com.hang.common.utils.PagedResult;
import com.hang.mapper.BgmDao;
import com.hang.mapper.UsersReportDao;
import com.hang.mapper.VideosDao;
import com.hang.pojo.Bgm;
import com.hang.pojo.UsersReport;
import com.hang.pojo.Videos;
import com.hang.pojo.vo.Reports;
import com.hang.service.VideoService;
import com.hang.service.web.util.ZKCurator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("videoService")
public class VideoServiceImpl implements VideoService {
    @Value("E:/hang-videos-dev")
    private String FILE_SPACE;

    @Autowired
    private UsersReportDao usersReportDao;

    @Autowired
    private BgmDao bgmDao;

    @Autowired
    private ZKCurator zkCurator;

    @Autowired
    private Sid sid;

    @Autowired
    private VideosDao videosDao;

    @Override
    public PagedResult queryReportList(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        System.out.println(2);
        List<Reports> reportsList = usersReportDao.selectAllVideoReport();
        System.out.println(3);
        PageInfo<Reports> pageList = new PageInfo<Reports>(reportsList);

        System.out.println(reportsList.size());
        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(reportsList);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());

        return grid;
    }

    @Override
    public JSONResult updateVideoStatus(String videoId, Integer value) {
        Videos videos=new Videos();
        videos.setId(videoId);
        videos.setStatus(value);
        videosDao.updateByPrimaryKey(videos);
        return JSONResult.ok();
    }

    @Override
    public PagedResult queryBgmList(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Bgm> list = bgmDao.selectAll();

        PageInfo<Bgm> pageList = new PageInfo<>(list);

        PagedResult result = new PagedResult();
        result.setTotal(pageList.getPages());
        result.setRows(list);
        result.setPage(page);
        result.setRecords(pageList.getTotal());

        return result;
    }

    @Override
    public int addBgm(Bgm bgm) {

        String bgmId = sid.nextShort();
        System.out.println(bgmId);
//        Map<String, String> map = new HashMap<>();
//        map.put("operType", BGMOperatorTypeEnum.ADD.type);
//        map.put("path", bgm.getPath());
//        zkCurator.sendBgmOperator(bgmId, JsonUtils.objectToJson(map));
        bgm.setId(bgmId);
        int insert = bgmDao.insert(bgm);
        return insert;
    }

    @Override
    public JSONResult deleteBgm(String bgmId) {
        int i = bgmDao.deleteByPrimaryKey(bgmId);
        return i==1?JSONResult.ok():JSONResult.errorMsg("删除失败");
    }

    @Override
    public JSONResult bgmUpload(MultipartFile[] files) throws Exception {

        String uploadPathDB = File.separator + "bgm";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (files != null && files.length > 0) {

                String fileName = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    // 文件上传的最终保存路径
                    System.out.println(FILE_SPACE.length());
                    System.out.println(FILE_SPACE);
                    String finalPath = FILE_SPACE + uploadPathDB + File.separator + fileName;
                    System.out.println(finalPath);
                    // 设置数据库保存的路径
                    uploadPathDB += (File.separator + fileName);

                    File outFile = new File(finalPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }

            } else {
                return JSONResult.errorMsg("上传出错...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.errorMsg("上传出错...");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        return JSONResult.ok(uploadPathDB);

    }
}
