package org.litu.app.apiController;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公共调用的接口
 */
@RestController
@RequestMapping("/api/common")
@Api(value = "公共调用的接口", tags = {"获取字典信息"})
public class CommonController {

}

