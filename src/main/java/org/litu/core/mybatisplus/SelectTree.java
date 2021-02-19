package org.litu.core.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.litu.base.entity.BaseTreeEntity;

/**
 * selectPage 加表别名
 */
public class SelectTree extends AbstractMethod {
    private static String treeSelect = "<script>\n SELECT %s FROM %s tb1 WHERE EXISTS (SELECT tb2.id FROM %s tb2 %s) ORDER BY sort_num ASC\n</script>";

    /**
     * selectTree注入到mybatis-plus
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        if (!BaseTreeEntity.class.isAssignableFrom(modelClass)) {
            return null;
        }

        String columns = sqlSelectColumns(tableInfo, true);
        String tableName = tableInfo.getTableName();
        String tempWhere = sqlWhereTreeEntityWrapper(tableInfo);

        String sql = String.format(treeSelect, columns, tableName, tableName, tempWhere);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatement(mapperClass, "selectTree", sqlSource, modelClass, tableInfo);
    }

    // 查询条件
    protected String sqlWhereTreeEntityWrapper(TableInfo table) {
        String sqlScript = table.getAllSqlWhere(false, true, WRAPPER_ENTITY_DOT);
        sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", WRAPPER_ENTITY), true);
        sqlScript += NEWLINE;
        String segmentAnd = SqlScriptUtils.convertIf(" AND", String.format("%s and %s", WRAPPER_NONEMPTYOFENTITY, WRAPPER_NONEMPTYOFNORMAL), false);
        sqlScript += SqlScriptUtils.convertIf(String.format(segmentAnd + " ${%s}", WRAPPER_SQLSEGMENT), String.format("%s != null and %s != '' and %s", WRAPPER_SQLSEGMENT, WRAPPER_SQLSEGMENT, WRAPPER_NONEMPTYOFWHERE), true);

        // 前置的AND的处理
        String treeWhereAnd = SqlScriptUtils.convertIf(" AND", String.format("(%s and %s) or %s", WRAPPER_NONEMPTYOFENTITY, WRAPPER_NONEMPTYOFNORMAL, WRAPPER_NONEMPTYOFWHERE), false);
        // 树状操作相关处理
        String treeWhere = " layers like concat(tb1.layers,'%')";
        if (table.getDbType().equals(DbType.SQL_SERVER)) {
            treeWhere = " layers like tb1.layers+'%'";
        }
        sqlScript += treeWhereAnd + treeWhere;
        sqlScript = SqlScriptUtils.convertWhere(sqlScript) + NEWLINE;
        sqlScript += SqlScriptUtils.convertIf(String.format(" ${%s}", WRAPPER_SQLSEGMENT), String.format("%s != null and %s != '' and %s", WRAPPER_SQLSEGMENT, WRAPPER_SQLSEGMENT, WRAPPER_EMPTYOFWHERE), true);
        sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", WRAPPER), true);
        return sqlScript;
    }
}