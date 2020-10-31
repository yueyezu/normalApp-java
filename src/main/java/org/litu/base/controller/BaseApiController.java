package org.litu.base.controller;

import io.swagger.annotations.Api;

/**
 * swagger controller模板
 */
@Api(value = "/api")
public abstract class BaseApiController extends BaseController {

    // 一个示例
    // @ApiOperation(value = "测试接口", notes = "接口说明", httpMethod = "post", produces =
    // "application/json;charset=utf-8")
    // @ApiImplicitParams({
    // @ApiImplicitParam(name = "path", required = true, defaultValue = "1",
    // dataType = "string", paramType = "path", example = "localhost:8080"),
    // @ApiImplicitParam(name = "e", required = true, defaultValue = "1", dataType =
    // "string", paramType = "path", example = "a") })
    // @RequestMapping(value = "/test/{na}", method = RequestMethod.POST, produces =
    // { "application/json;charset=utf-8" })
    // public CommonResult swaggerDemo(@PathVariable("path") String path,
    // CommonResult params) {
    // CommonResult result = new CommonResult();
    // result.setResult(params);
    // return result;
    // }
}
