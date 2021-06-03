package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.litu.app.constant.SysContant;
import org.litu.app.dao.SysFilesMapper;
import org.litu.app.entity.system.SysFiles;
import org.litu.app.service.ISysFilesService;
import org.litu.base.service.impl.BaseServiceImpl;
import org.litu.core.login.UserInfo;
import org.litu.util.file.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 文件管理表 服务实现类
 * </p>
 *
 * @author ltgk
 * @since 2019-12-09
 */
@Service
public class SysFilesServiceImpl extends BaseServiceImpl<SysFilesMapper, SysFiles> implements ISysFilesService {

    /**
     * 上传文件
     */
    @Override
    public SysFiles uploadFile(UserInfo user, MultipartFile file) {
        // 文件名处理
        String originalName = file.getOriginalFilename();
        try {
            String fileType = FileUtil.getFileSuffix(originalName);
            String relativePath = FileUtil.getSavePath(fileType);    // 相对路径
            String realPath = SysContant.FILE_BASE_PATH + relativePath;  // 绝对路径

            // 实际文件保存
            File fileUpload = new File(realPath);
            FileUtil.makeDirectory(fileUpload);
            file.transferTo(fileUpload);
            // 文件信息存储到数据库
            SysFiles sysFiles = new SysFiles();
            sysFiles.setOriginName(originalName);
            sysFiles.setLocation(relativePath);
            sysFiles.setFileType(fileType);
            sysFiles.setFileSize(file.getSize());
            sysFiles.setCreateBy(user.getId());
            sysFiles.setCreateTime(new Date());
            save(sysFiles);
            return sysFiles;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 其他接口接收文件的保存接口
     *
     * @param file 保存到本地的文件
     * @return 添加成功返回保存后的文件实体信息
     */
    @Override
    public SysFiles save(UserInfo user, File file) {
        if (!file.exists()) {
            return null;
        }

        String originalName = file.getName();
        String fileType = FileUtil.getFileSuffix(originalName);
        String relativePath = FileUtil.getSavePath(fileType);    // 相对路径
        String realPath = SysContant.FILE_BASE_PATH + relativePath;  // 绝对路径

        // 实际文件保存
        File fileUpload = new File(realPath);
        file.renameTo(fileUpload);
        // 文件信息存储到数据库
        SysFiles sysFiles = new SysFiles();
        sysFiles.setOriginName(originalName);
        sysFiles.setLocation(relativePath);
        sysFiles.setFileType(fileType);
        sysFiles.setFileSize(file.length());
        sysFiles.setCreateBy(user.getId());
        sysFiles.setCreateTime(new Date());
        save(sysFiles);
        return sysFiles;
    }

    /**
     * 加载获取文件
     *
     * @param filePath
     * @param response
     */
    @Override
    public void load(String filePath, HttpServletResponse response) {
        String realPath = SysContant.FILE_BASE_PATH + filePath;
        try (FileInputStream in = new FileInputStream(realPath); ServletOutputStream output = response.getOutputStream()) {
            response.setContentType(Files.probeContentType(Paths.get(realPath)));
            IOUtils.copy(in, output);
            output.flush();
            in.close();
            output.close();
        } catch (FileNotFoundException e) {
            fileErrorReturn(response, "未找到资源");
        } catch (IOException e) {
            fileErrorReturn(response, "文件读取错误");
        }
    }

    /**
     * 下载文件
     *
     * @param fileName
     * @param response
     */
    @Override
    public void download(String filePath, String fileName, HttpServletResponse response) {
        try (FileInputStream in = new FileInputStream(SysContant.FILE_BASE_PATH + filePath); ServletOutputStream output = response.getOutputStream()) {
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
            long filelen = in.getChannel().size();
            response.setContentLengthLong(filelen);
            IOUtils.copy(in, output);
            if (output != null) {
                output.flush();
                output.close();
            }
        } catch (FileNotFoundException e) {
            fileErrorReturn(response, "未找到资源");
        } catch (IOException e) {
            fileErrorReturn(response, "文件读取错误");
        }
    }

    /**
     * 文件读取错误时返回的信息
     *
     * @param response
     * @param errorMsg
     */
    private void fileErrorReturn(HttpServletResponse response, String errorMsg) {
        PrintWriter out = null;
        try {
            response.setContentType("text/plain;charset=UTF-8");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            out = response.getWriter();
            out.append(errorMsg);
            out.flush();
        } catch (IOException e) {
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private static Map<String, Object[]> type = new HashMap<>();

    static {
        type.put("picture", new String[]{"jpg", "png", "jpeg", "gif", "tif", "bmp", "dwg", "psd", "vsd"});
        type.put("document", new String[]{"doc", "docx", "txt", "wps", "pdf", "html", "htm", "java", "css", "js", "rtf", "eml", "mdb", "ps", "flv", "mpg", "exe", "wav", "mid", "zip", "rar", "ini", "jar", "exe", "jsp", "mf", "xml", "sql", "bat", "gz", "properties", "class", "chm", "mxp", "wsp", "torrent", "mov", "wpd", "dbx", "pst", "qdf", "pwl", "ram", "ftl"});
        type.put("audio", new String[]{"mp3", "mp4"});
        type.put("video", new String[]{"avi", "wmv", "rmvb"});
    }

    @Override
    public void beforeList(UserInfo user,SysFiles entity, String keyword, Map<String, String> params, LambdaQueryWrapper<SysFiles> query) {
        query.like(SysFiles::getOriginName, keyword);
        if (params.containsKey("fileType")) {
            query.in(SysFiles::getFileType, type.get(params.get("fileType")));
        }
        query.orderByDesc(SysFiles::getCreateTime);
    }

    @Override
    public void beforePage(UserInfo user,SysFiles entity, String keyword, IPage<SysFiles> page, Map<String, String> params, LambdaQueryWrapper<SysFiles> query) {
        if (StringUtils.isNotBlank(entity.getFileType())) {
            List<String> fileTypeList = new ArrayList<String>();
            for (String fileType : type.keySet()) {
                if (fileType.equals(entity.getFileType())) {
                    String[] fileFormatArr = (String[]) type.get(fileType);
                    for (String fileFormat : fileFormatArr) {
                        fileTypeList.add(fileFormat);
                    }
                }
            }
            query.in(SysFiles::getFileType, fileTypeList);
        }
        query.and(i -> i.like(SysFiles::getOriginName, keyword).or().like(SysFiles::getFileType, keyword));
        query.orderByDesc(SysFiles::getCreateTime);
    }

    @Override
    public boolean delete(String id, Map<String, String> params) {
        boolean result = false;
        SysFiles nowFile = baseMapper.selectById(id);
        File file = new File(SysContant.FILE_BASE_PATH + nowFile.getLocation());
        // 如果图片路径所对应的图片存在，则直接删除
        result = super.delete(id, params);
        if (result) {
            if (file.exists()) {
                if (file.delete()) {
                }
            }
        }

        return result;
    }

    @Override
    public void deleteFile(String location) {
        File file = new File(SysContant.FILE_BASE_PATH + location);
        if (file.exists()) { // 如果图片路径所对应的图片存在，则直接删除
            file.delete();
        }

        LambdaQueryWrapper<SysFiles> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysFiles::getLocation, location);
        List<SysFiles> list = list(queryWrapper);
        if (list.size() > 0) {
            remove(queryWrapper);
        }
    }

    /**
     * 根据id列表，获取id对应的文件路径列表
     *
     * @param idList id列表
     * @return
     */
    @Override
    public Map<String, String> getIdToPathMap(List<String> idList) {
        idList = idList.stream().filter(obj -> StringUtils.isNotBlank(obj)).collect(Collectors.toList());    // 过滤掉列表中为空的所有id
        if (idList.isEmpty()) return new HashMap<>();

        Map<String, String> idToPath = new HashMap<>();
        LambdaQueryWrapper<SysFiles> wrapperFiles = Wrappers.lambdaQuery();
        wrapperFiles.in(SysFiles::getId, idList);
        List<SysFiles> sysFiles = list(wrapperFiles);
        sysFiles.forEach(sysFile -> {
            idToPath.put(sysFile.getId(), sysFile.getLocation());
        });

        return idToPath;
    }
}
