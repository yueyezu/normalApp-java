package org.litu.base.controller;

import org.apache.commons.lang3.StringUtils;
import org.litu.base.service.IBaseTreeService;
import org.litu.core.annotation.PageBasePath;
import org.litu.core.base.BaseTreeEntity;
import org.litu.core.base.ITreeNode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class BaseViewTreeController<T extends BaseTreeEntity & ITreeNode<T>, S extends IBaseTreeService<T>> extends BaseTreeController<T, S> {

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
        if (obj == null || StringUtils.isBlank(obj.getParentId()) || obj.getParentId().equals("0")) {
            model.addAttribute("parentId", "0");
            model.addAttribute("parentName", "根节点");
        } else {
            T parent = service.getById(obj.getParentId());
            model.addAttribute("parentId", parent.getId());
            model.addAttribute("parentName", parent.getName());
        }
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
        if (StringUtils.isBlank(obj.getParentId()) || obj.getParentId().equals("0")) {
            model.addAttribute("parentId", "0");
            model.addAttribute("parentName", "根节点");
        } else {
            T parent = service.detail(obj.getParentId());
            model.addAttribute("parentId", parent.getId());
            model.addAttribute("parentName", parent.getName());
        }
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
}
