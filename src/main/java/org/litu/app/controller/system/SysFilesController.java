package org.litu.app.controller.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.entity.SysFiles;
import org.litu.app.service.ISysFilesService;
import org.litu.base.controller.BaseViewFormController;
import org.litu.base.controller.PageBasePath;
import org.litu.base.log.LtLog;
import org.litu.base.log.LtLogOperation;
import org.litu.base.log.LtLogOperationEnum;
import org.litu.core.base.BaseRes;
import org.litu.core.enums.ResultEnum;
import org.litu.core.exception.LtParamException;
import org.litu.core.login.TokenCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文件管理表 前端控制器
 *
 * @author ltgk
 * @since 2019-12-09
 */
@Controller
@LtLog(module = "文件管理表")
@RequestMapping("/sysFiles")
@PageBasePath(basePath = "system/sysFiles")
@Api(value = "文件管理模块", tags = {"对系统的文件进行管理。"}, protocols = "http,https")
public class SysFilesController extends BaseViewFormController<SysFiles, ISysFilesService> {

    @Autowired
    private ISysFilesService sysFilesService;

    /**
     * 上传文件
     *
     * @param file 要上传的文件
     * @return 成功则返回提交成功和相应信息
     */
    @PostMapping("/upload")
    @ResponseBody
    @LtLogOperation(operation = LtLogOperationEnum.UPLOAD)
    @ApiOperation(value = "文件上传", notes = "文件上传到服务器，返回上传文件的信息。", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件信息", paramType = "form", required = true, dataType = "file")
    })
    public BaseRes uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return BaseRes.error(ResultEnum.ParamError, "文件不能为空!");
        }

        SysFiles result = sysFilesService.uploadFile(nowUser(), file);
        if (result == null) {
            return BaseRes.error(ResultEnum.UpdateError, "文件上传失败!");
        }
        return BaseRes.ok(result);
    }

    /**
     * 加载文件通过名称
     *
     * @param file 要下载的文件
     */
    @GetMapping(value = "/loadFile")
    @ApiOperation(value = "文件加载", notes = "根据文件名称加载文件信息。", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件路径名称", paramType = "query", required = true, dataType = "String"),
    })
    @TokenCheck(check = false)
    public void loadFile(String file) {
        if (StringUtils.isBlank(file)) {
            throw new LtParamException("文件信息不能为空!");
        }
        sysFilesService.load(file, response);
    }

    /**
     * 加载文件通过id
     *
     * @param fileId 要加载的文件id
     */
    @GetMapping(value = "/loadFileById")
    @ApiOperation(value = "文件加载依据文件ID", notes = "根据文件ID加载文件信息。", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "文件ID", paramType = "query", required = true, dataType = "String"),
    })
    @TokenCheck(check = false)
    public void loadFileById(String fileId) {
        if (StringUtils.isBlank(fileId)) {
            throw new LtParamException("文件信息不能为空!");
        }
        SysFiles fileObj = sysFilesService.getById(fileId);
        sysFilesService.load(fileObj.getLocation(), response);
    }

    /**
     * 下载文件
     *
     * @param file 文件路径
     */
    @GetMapping("/download")
    @ResponseBody
    @ApiOperation(value = "文件下载，依据文件路径", notes = "根据文件名称下载文件信息。", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件路径", paramType = "query", required = true, dataType = "String"),
    })
    @TokenCheck(check = false)
    public void download(String file) {
        if (StringUtils.isBlank(file)) {
            throw new LtParamException("文件名称不能为空!");
        }
        String fileName = file.substring(file.lastIndexOf("/") + 1);
        sysFilesService.download(file, fileName, response);
    }

    /**
     * 下载文件通过Id
     *
     * @param fileId 文件Id
     */
    @GetMapping("/downloadById")
    @ResponseBody
    @ApiOperation(value = "文件下载，依据文件ID", notes = "根据文件ID下载文件信息。", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "文件ID", paramType = "query", required = true, dataType = "String"),
    })
    @TokenCheck(check = false)
    public void downloadById(String fileId) {
        if (StringUtils.isBlank(fileId)) {
            throw new LtParamException("文件信息不能为空!");
        }
        SysFiles fileObj = sysFilesService.getById(fileId);
        String filePath = fileObj.getLocation();
        sysFilesService.download(filePath, fileObj.getOriginName(), response);
    }

    /**
     * 删除文件，根据文件名称
     *
     * @param fileName
     */
    @PostMapping(value = "/delFile")
    @ResponseBody
    @ApiOperation(value = "文件删除，依据文件名称", notes = "根据文件名称删除文件,支持多个文件删除，如果多个文件，使用逗号分割", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "文件名称", paramType = "query", required = true, dataType = "String"),
    })
    public boolean delFile(String fileName) {
        try {
            if (!fileName.contains(",")) {
                sysFilesService.deleteFile(fileName);
            } else {
                String[] fileNames = fileName.split(",");
                for (int i = 0; i < fileNames.length; i++) {
                    sysFilesService.deleteFile(fileNames[i]);
                }
            }
        } catch (Exception e) {
            logger.error("文件删除执行错误", e);
            return false;
        }
        return true;
    }
}