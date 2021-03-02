package org.litu.app.service;

import org.litu.app.entity.SysFiles;
import org.litu.base.service.IBaseService;
import org.litu.core.login.UserInfo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件管理表 服务类
 * </p>
 *
 * @author ltgk
 * @since 2019-12-09
 */
public interface ISysFilesService extends IBaseService<SysFiles> {

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    SysFiles uploadFile(UserInfo user, @RequestParam("file") MultipartFile file);

    /**
     * 其他接口接收文件的保存接口
     *
     * @param file 保存到本地的文件
     * @return 添加成功返回保存后的文件实体信息
     */
    SysFiles save(UserInfo user,File file);

    /**
     * 加载获取文件
     *
     * @param filePath
     * @param response
     */
    void load(String filePath, HttpServletResponse response);

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param response web反馈实体
     */
    void download(String filePath, String fileName, HttpServletResponse response);

    /**
     * 根据文件路径删除文件记录
     *
     * @param location
     */
    void deleteFile(String location);

    /**
     * 根据id列表，获取id对应的文件路径列表
     *
     * @param idList id列表
     * @return
     */
    Map<String, String> getIdToPathMap(List<String> idList);
}

