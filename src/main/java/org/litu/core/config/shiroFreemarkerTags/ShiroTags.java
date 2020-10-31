package org.litu.core.config.shiroFreemarkerTags;

import freemarker.template.SimpleHash;

/**
 * 在 shiro-freemarker-tags 中提取有需要的标签，进行保留
 * <p>
 * Shortcut for injecting the tags into Freemarker
 *
 * <p>Usage: cfg.setSharedVeriable("shiro", new ShiroTags());</p>
 */
public class ShiroTags extends SimpleHash {
    public ShiroTags() {
        put("authenticated", new AuthenticatedTag());
        put("hasAnyPermissions", new HasAnyPermissionsTag());
        put("hasAnyRoles", new HasAnyRolesTag());
        put("hasPermission", new HasPermissionTag());
        put("hasRole", new HasRoleTag());
        put("notAuthenticated", new NotAuthenticatedTag());
    }
}