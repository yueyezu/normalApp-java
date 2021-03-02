package org.litu.base.controller;

import org.apache.commons.lang3.StringUtils;
import org.litu.base.service.IBaseService;
import org.litu.core.base.BaseEntity;
import org.litu.core.login.TokenCheck;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public abstract class BaseViewFormController<T extends BaseEntity, S extends IBaseService<T>> extends BaseFormController<T, S> {

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
    @TokenCheck(check = false)
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
    @TokenCheck(check = false)
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
    @TokenCheck(check = false)
    public String view(Model model, @PathVariable(value = "id") String id) {
        // 获取详情信息
        T obj = service.detail(id);
        model.addAttribute("data", obj);

        beforeView(model, obj);

        PageBasePath basePath = this.getClass().getAnnotation(PageBasePath.class);
        return basePath.basePath() + "/view";
    }
}
