package org.litu.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.litu.base.entity.BaseEntity;
import org.litu.base.service.IBaseService;
import org.litu.base.vo.BaseRes;
import org.litu.base.vo.SelectVo;
import org.litu.core.annotation.LtLogOperation;
import org.litu.core.annotation.PageBasePath;
import org.litu.core.enums.ErrorEnum;
import org.litu.core.enums.LtLogOperationEnum;
import org.litu.core.exception.LtParamException;
import org.litu.util.common.FieldUtil;
import org.litu.util.common.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import java.util.*;

/**
 * 通用单表操作controller
 */
public abstract class BaseFormController<T extends BaseEntity, S extends IBaseService<T>> extends BaseController {

    /*---------  界面部分  Start ---------*/

    /**
     * 返回主界面前的处理方法
     */
    protected void beforeIndex(Model model) {

    }

    /**
     * 列表界面
     *
     * @param model 实体类
     * @return 对应该类的index页面
     */
    @GetMapping("/index")
    public String index(Model model) {
        beforeIndex(model);

        PageBasePath basePath = this.getClass().getAnnotation(PageBasePath.class);
        return basePath.basePath() + "/index";
    }

    /**
     * 在返回添加、修改界面之前处理的方法
     *
     * @param model 前后端交互实体
     * @param obj   当前对象，返回的修改界面时，为当前对象；返回添加界面时，为null
     */
    protected void beforeForm(Model model, T obj) {

    }

    /**
     * 表单界面
     *
     * @param model 实体类
     * @return 对应该类的form页面
     */
    @GetMapping(value = {"/form", "/form/{id}"})
    public String form(Model model, @PathVariable(value = "id", required = false) String id) {
        // 如果ID不为空，则认为是编辑界面，对界面的数据进行获取
        if (StringUtils.isNotBlank(id)) {
            T obj = service.detail(id);
            model.addAttribute("data", obj);
            beforeForm(model, obj);
        } else {
            beforeForm(model, null);
        }
        PageBasePath basePath = this.getClass().getAnnotation(PageBasePath.class);
        return basePath.basePath() + "/form";
    }

    /**
     * 在返回查询界面之前，进行数据处理的方法
     *
     * @param model 返回前端的实体信息
     * @param obj   传入的实体信息
     */
    protected void beforeView(Model model, T obj) {
        // 查询界面返回之前所需要的操作
    }

    /**
     * 查看界面
     *
     * @param model 实体类
     * @return 对应该类的view页面
     */
    @GetMapping("/view/{id}")
    public String view(Model model, @PathVariable(value = "id") String id) {
        // 获取详情信息
        T obj = service.detail(id);
        model.addAttribute("data", obj);

        beforeView(model, obj);

        PageBasePath basePath = this.getClass().getAnnotation(PageBasePath.class);
        return basePath.basePath() + "/view";
    }

    /*---------  界面部分 End ---------*/

    @Autowired
    protected S service;

    /**
     * 列表，不分页
     *
     * @param entity 实体类
     * @return 列表
     */
    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "列表查询", notes = "查询列表信息，所有数据", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字，模糊检索，字段默认未使用", paramType = "query", dataType = "String")
    })
    public List<T> list(T entity, @RequestParam(defaultValue = "") String keyword) {
        try {
            Map<String, String> params = requestParams();

            List<T> list = service.list(entity, keyword, params);
            return list;
        } catch (Exception e) {
            log.warn("列表查询中出现异常", e);
            return new ArrayList<>();
        }
    }

    /**
     * 请求处理方法会调用此方法， 子类可根据情况重写该方法
     *
     * @param entity 集合
     * @return 树结构集合
     */
    protected void toSelect(T entity, SelectVo selectVo) {
        // 记录显示值
        if (FieldUtil.hasProperty(entity, "fName")) {
            String text = Objects.requireNonNull(FieldUtil.read(entity, "fName")).toString();
            selectVo.setText(text);
        }
    }

    /**
     * 下拉列表
     *
     * @param entity 实体类
     * @return 下拉列表
     */
    @GetMapping("/select")
    @ResponseBody
    @ApiOperation(value = "获取下拉选择框列表", notes = "获取下拉选择框列表", httpMethod = "GET")
    public List<SelectVo> select(T entity) {
        Map<String, String> params = requestParams();
        List<T> list = service.list(entity, "", params);

        // 将列表转化成树结构
        List<SelectVo> selectVos = new ArrayList<>();
        for (T node : list) {
            SelectVo selectVo = new SelectVo();
            selectVo.setId(node.getfId());  // 对id赋值
            toSelect(node, selectVo);

            selectVos.add(selectVo);
        }
        return selectVos;
    }

    /**
     * 分页查询的排序处理
     *
     * @param page      分页实体
     * @param sortName  排序的字段名称
     * @param sortOrder 排序的方式
     */
    protected void pageSort(Page<T> page, String sortName, String sortOrder) {
        if (StringUtils.isNotBlank(sortName)) {
            if (sortOrder.equals("asc"))
                page.setAsc(sortName);
            else
                page.setDesc(sortName);
        }
    }

    /**
     * 列表，分页
     *
     * @param entity 实体类
     * @return 列表，分页
     */
    @GetMapping("/page")
    @ResponseBody
    @ApiOperation(value = "获取分页列表", notes = "获取分页列表数据", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "第几页，第一页为0", paramType = "query", required = true, defaultValue = "0", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query", required = true, defaultValue = "10", dataType = "String"),
            @ApiImplicitParam(name = "sortName", value = "排序列,目前仅支持一个列，传入多列无效", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sortOrder", value = "排序方式: asc-升序,默认，desc-降序", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "keyword", value = "关键字，模糊检索，字段默认未使用", paramType = "query", dataType = "String")
    })
    public BaseRes page(T entity, @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, String sortName,
                        String sortOrder, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<T> page = new Page<>(pageNumber, pageSize);
        pageSort(page, sortName, sortOrder);

        try {
            Map<String, String> params = requestParams();
            IPage<T> pages = service.page(entity, keyword, page, params);
            return BaseRes.page(pages.getTotal(), pages.getRecords());
        } catch (Exception e) {
            log.warn("分页查询中出现异常", e);
            return BaseRes.error(ErrorEnum.SearchError);
        }
    }

    /**
     * 获取实体详细信息
     *
     * @param id 实体id
     * @return 实体信息
     */
    @GetMapping("/one/{id}")
    @ResponseBody
    @ApiOperation(value = "查看详情", notes = "根据ID获取实体的详情信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要获取的实体对象的id", paramType = "path", required = true, dataType = "String")
    })
    public BaseRes detail(@PathVariable(value = "id") String id) {
        T obj = service.detail(id);
        return BaseRes.ok(obj);
    }

    /**
     * 添加、修改时，进行传入数据字段的校验
     *
     * @param entity 对象实体
     * @param params 其他参数
     */
    protected void validate(T entity, Map<String, String> params) {
        // 使用javax中的验证，对字段进行验证
        Set<ConstraintViolation<T>> constraintViolations = ValidateUtil.validate(entity);
        if (constraintViolations != null && constraintViolations.size() != 0) {
            throw new LtParamException(constraintViolations.iterator().next().getMessage());
        }
    }

    /**
     * 保存
     *
     * @param entity 实体类
     * @return 保存成功或者是保存错误枚举
     */
    @PostMapping("/save")
    @ResponseBody
    @LtLogOperation(operation = LtLogOperationEnum.ADD)
    @ApiOperation(value = "保存，新增", notes = "将传入的数据，新增到数据库", httpMethod = "POST")
    public BaseRes save(T entity) {
        Map<String, String> params = requestParams();

        validate(entity, params);

        boolean res = service.save(entity, params);
        return res ? BaseRes.ok("保存成功！") : BaseRes.error(ErrorEnum.SaveError);
    }

    /**
     * 更新
     *
     * @param entity 实体类
     * @return 更新成功或更新错误枚举
     */
    @PostMapping("/update")
    @ResponseBody
    @LtLogOperation(operation = LtLogOperationEnum.UPDATE)
    @ApiOperation(value = "更新", notes = "根据传入的更新的实体信息，进行数据更新，默认根据id进行更新", httpMethod = "POST")
    public BaseRes update(T entity) {
        Map<String, String> params = requestParams();
        validate(entity, params);

        boolean res = service.update(entity, params);
        return res ? BaseRes.ok("更新成功！") : BaseRes.error(ErrorEnum.UpdateError);
    }

    /**
     * 物理删除
     *
     * @param id 传入要删除的记录的id
     * @return 删除成功或删除错误枚举
     */
    @PostMapping("/delete")
    @ResponseBody
    @LtLogOperation(operation = LtLogOperationEnum.DELETE)
    @ApiOperation(value = "物理删除", notes = "根据ID物理删除相关数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要获取的实体对象的主键", paramType = "query", required = true, dataType = "String")
    })
    public BaseRes delete(String id) {
        if (StringUtils.isBlank(id)) {
            return BaseRes.error(ErrorEnum.ParamError, "id不能为空");
        }

        Map<String, String> params = requestParams();

        boolean res = service.delete(id, params);
        return res ? BaseRes.ok("删除成功！") : BaseRes.error(ErrorEnum.DeleteError);
    }

    /**
     * 批量物理删除
     *
     * @param ids 要批量删除的记录id集合字符串  用，隔开
     * @return 删除成功或者删除错误枚举
     */
    @PostMapping("/batchDelete")
    @ResponseBody
    @LtLogOperation(operation = LtLogOperationEnum.BATCHDELETE)
    @ApiOperation(value = "批量物理删除", notes = "根据传入的要删除数据的id，全部删除掉", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "要批量删除的记录id集合字符串,，隔开", paramType = "query", required = true, dataType = "String")
    })
    public BaseRes batchDelete(String ids) {
        if (StringUtils.isBlank(ids)) {
            return BaseRes.error(ErrorEnum.ParamError, "删除信息不能为空。");
        }

        Map<String, String> params = requestParams();

        // 将id字符串转为数组
        String[] idArray = ids.split(",");
        List<String> idList = new ArrayList<>();
        Collections.addAll(idList, idArray);

        boolean res = service.batchDelete(idList, params);
        return res ? BaseRes.ok("删除成功！") : BaseRes.error(ErrorEnum.DeleteError);
    }

    /**
     * 逻辑删除
     *
     * @param id 需要逻辑删除的记录id
     * @return 删除成功或者删除错误枚举
     */
    @PostMapping("/logicalDelete")
    @ResponseBody
    @LtLogOperation(operation = LtLogOperationEnum.LOGICDELETE)
    @ApiOperation(value = "逻辑删除", notes = "根据ID，逻辑删除对象，对象需要有字段:fEnabledelete、F_DeleteFlag、F_DeleteUserId、F_DeleteTime", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要获取的实体对象的主键", paramType = "query", required = true, dataType = "String")
    })
    public BaseRes logicalDelete(String id) {
        if (StringUtils.isBlank(id)) {
            return BaseRes.error(ErrorEnum.ParamError, "id不能为空");
        }
        Map<String, String> params = requestParams();
        boolean res = service.logicalDelete(id, params);
        return res ? BaseRes.ok("删除成功！") : BaseRes.error(ErrorEnum.DeleteError);
    }

    /**
     * 逻辑恢复
     *
     * @param id 实体的ID
     * @return 删除成功返回ok
     */
    @PostMapping("/logicalRestore")
    @ResponseBody
    @LtLogOperation(operation = LtLogOperationEnum.LOGICRESTORE)
    @ApiOperation(value = "逻辑删除恢复", notes = "根据已逻辑删除数据的id，进行恢复", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "已逻辑删除数据的id", paramType = "query", required = true, dataType = "String")
    })
    public BaseRes logicalRestore(String id) {
        if (StringUtils.isBlank(id)) {
            return BaseRes.error(ErrorEnum.ParamError, "id不能为空");
        }
        Map<String, String> params = requestParams();
        boolean res = service.logicalRestore(id, params);
        return res ? BaseRes.ok("恢复成功！") : BaseRes.error(ErrorEnum.DeleteError);
    }

    /**
     * 唯一性校验，判断某个字段的值在数据库是否存在
     *
     * @param id    需要判断的id
     * @param value 属性值
     * @param field 属性名称
     * @return
     */
    @GetMapping("/exists/{field}")
    @ResponseBody
    @ApiOperation(value = "唯一性校验", notes = "唯一性校验，判断字段的值是否已经存在，如果存在，data为true", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要校验实体对象的主键", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "value", value = "新录入的值", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "field", value = "要进行校验对象中的字段，对应数据库字段名", paramType = "path", required = true, dataType = "String")
    })
    public BaseRes exists(String id, String value, @PathVariable(value = "field") String field) {
        if (StringUtils.isBlank(value)) {
            return BaseRes.error(ErrorEnum.ParamError, "value不能为空!");
        }
        boolean res = service.exists(id, value, field);
        return BaseRes.ok(res);
    }

    /**
     * 唯一性校验，判断字段值在数据库中不存在
     *
     * @param id    需要判断的id
     * @param value 属性值
     * @param field 属性名称
     * @return
     */
    @GetMapping("/notExists/{field}")
    @ResponseBody
    @ApiOperation(value = "表单提交唯一校验", notes = "表单唯一校验，存在返回false，校验不通过,配合validator使用", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要校验实体对象的主键", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "value", value = "新录入的值", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "field", value = "要进行校验对象中的字段，对应数据库字段名", paramType = "path", required = true, dataType = "String")
    })
    public boolean notExists(String id, String value, @PathVariable(value = "field") String field) {
        if (StringUtils.isBlank(value)) {
            log.warn("判断字段是否存在，传入参数为");
            return true;
        }
        boolean res = service.exists(id, value, field);
        return !res;
    }
}
