package org.litu.base.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.litu.base.entity.BaseTreeEntity;
import org.litu.base.service.IBaseTreeService;
import org.litu.base.vo.TreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseTreeController<T extends BaseTreeEntity, S extends IBaseTreeService<T>> extends BaseFormController<T, S> {

    @Autowired
    protected S service;

    @Override
    protected void beforeForm(Model model, T obj) {
        super.beforeForm(model, obj);
        if (obj == null || StringUtils.isBlank(obj.getfParentid()) || obj.getfParentid().equals("0")) {
            model.addAttribute("parentId", "0");
            model.addAttribute("parentName", "根节点");
        } else {
            T parent = service.getById(obj.getfParentid());
            model.addAttribute("parentId", parent.getfId());
            model.addAttribute("parentName", parent.getfName());
        }
    }

    @Override
    protected void beforeView(Model model, T obj) {
        super.beforeView(model, obj);

        if (StringUtils.isBlank(obj.getfParentid()) || obj.getfParentid().equals("0")) {
            model.addAttribute("parentId", "0");
            model.addAttribute("parentName", "根节点");
        } else {
            T parent = service.detail(obj.getfParentid());
            model.addAttribute("parentId", parent.getfId());
            model.addAttribute("parentName", parent.getfName());
        }
    }

    /**
     * 树，不分页
     *
     * @param entity  实体类
     * @param keyword 关键词
     * @return 树，不分页
     */
    @GetMapping("/tree")
    @ResponseBody
    @ApiOperation(value = "树结构数据查询", notes = "树结构数据查询，所有数据", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字，模糊检索，字段默认未使用", paramType = "query", dataType = "String")
    })
    public List<TreeVo> treeView(T entity, @RequestParam(defaultValue = "") String keyword) {
        Map<String, String> params = requestParams();
        List<T> list = service.tree(entity, keyword, params);
        // 将列表转化成树结构
        List<TreeVo> trees = new ArrayList<>();
        for (T node : list) {
            TreeVo treeVo = new TreeVo();
            treeVo.setId(node.getfId());
            if (node.getfParentid().equals("0")) {
                treeVo.setParent("#");
            } else {
                treeVo.setParent(node.getfParentid());
            }
            treeVo.setText(node.getfName());
            trees.add(treeVo);
        }
        return trees;
    }

    /**
     * 树表格列表，不分页
     *
     * @param entity  实体类
     * @param keyword 关键词
     * @return 树表格列表
     */
    @GetMapping("/treeList")
    @ResponseBody
    @ApiOperation(value = "树表格列表查询", notes = "树表格列表查询，所有数据", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字，模糊检索，字段默认未使用", paramType = "query", dataType = "String")
    })
    public List<T> treeList(T entity, @RequestParam(defaultValue = "") String keyword) {
        Map<String, String> params = requestParams();
        List<T> list = service.tree(entity, keyword, params);
        return list;
    }
}
