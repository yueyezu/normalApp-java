package org.litu.core.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * sql注入器
 */
public class MybatisPlusSqlInject extends DefaultSqlInjector {
    /**
     * sql注入器
     *
     * @return 方法集合
     */
    @Override
    public List<AbstractMethod> getMethodList() {

        List<AbstractMethod> methodList = super.getMethodList();
        //添加自定义方法
        methodList.add(new SelectTree());
        AbstractMethod[] methods = new AbstractMethod[methodList.size()];
        methodList.toArray(methods);
        return Stream.of(methods).collect(Collectors.toList());
    }

}
