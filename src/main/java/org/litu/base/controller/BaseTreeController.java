package org.litu.base.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.litu.base.service.IBaseTreeService;
import org.litu.core.base.TreeVo;
import org.litu.core.login.TokenCheck;
import org.litu.core.base.BaseTreeEntity;
import org.litu.core.base.ITreeNode;
import org.litu.core.base.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@TokenCheck
public abstract class BaseTreeController<T extends BaseTreeEntity & ITreeNode<T>, S extends IBaseTreeService<T>> extends BaseFormController<T, S> {

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
    @Override
    public List<T> list(T entity, @RequestParam(defaultValue = "") String keyword) {
        Map<String, String> params = requestParams();

        try {
            List<T> list = service.tree(entity, keyword, params);
            return list;
        } catch (Exception e) {
            logger.warn("列表查询中出现异常", e);
            return new ArrayList<>();
        }
    }

    /**
     * 树列表
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
        try {
            List<T> list = service.tree(entity, keyword, params);
            // 将列表转化成树结构
            List<TreeVo> trees = new ArrayList<>();
            for (T node : list) {
                TreeVo treeVo = new TreeVo();
                treeVo.setId(node.getId());
                treeVo.setParentId(node.getParentId());
                treeVo.setText(node.getName());
                trees.add(treeVo);
            }
            return TreeUtil.build(trees);
        } catch (Exception e) {
            logger.warn("列表查询中出现异常", e);
            return new ArrayList<>();
        }
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

        try {
            List<T> trees = service.tree(entity, keyword, params);
            return TreeUtil.build(trees);
        } catch (Exception e) {
            logger.warn("树列表查询出现异常", e);
            return new ArrayList<>();
        }
    }
}
