package org.litu.base.controller;

import org.apache.commons.lang3.StringUtils;
import org.litu.base.service.IBaseTreeService;
import org.litu.core.base.BaseTreeEntity;
import org.litu.core.base.ITreeNode;
import org.springframework.ui.Model;

public class BaseViewTreeController<T extends BaseTreeEntity & ITreeNode<T>, S extends IBaseTreeService<T>> extends BaseViewFormController<T, S> {

    @Override
    protected void beforeForm(Model model, T obj) {
        super.beforeForm(model, obj);
        if (obj == null || StringUtils.isBlank(obj.getParentId()) || obj.getParentId().equals("0")) {
            model.addAttribute("parentId", "0");
            model.addAttribute("parentName", "根节点");
        } else {
            T parent = service.getById(obj.getParentId());
            model.addAttribute("parentId", parent.getId());
            model.addAttribute("parentName", parent.getName());
        }
    }

    @Override
    protected void beforeView(Model model, T obj) {
        super.beforeView(model, obj);

        if (StringUtils.isBlank(obj.getParentId()) || obj.getParentId().equals("0")) {
            model.addAttribute("parentId", "0");
            model.addAttribute("parentName", "根节点");
        } else {
            T parent = service.detail(obj.getParentId());
            model.addAttribute("parentId", parent.getId());
            model.addAttribute("parentName", parent.getName());
        }
    }
}
