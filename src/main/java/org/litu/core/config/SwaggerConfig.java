package org.litu.core.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UrlPathHelper;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiResourceController;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.web.Swagger2Controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * swagger配置
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String DEFAULT_PATH = "/api";

    /**
     * SwaggerUI资源访问
     *
     * @param servletContext ServletContext对象
     * @param order          调整排序
     * @return 新的路径和新的排序
     * @throws Exception 抛出异常
     */
    @Bean
    public SimpleUrlHandlerMapping swaggerUrlHandlerMapping(ServletContext servletContext, @Value("${swagger.mapping.order:10}") int order) throws Exception {
        SimpleUrlHandlerMapping urlHandlerMapping = new SimpleUrlHandlerMapping();
        Map<String, ResourceHttpRequestHandler> urlMap = new HashMap<>();
        {
            PathResourceResolver pathResourceResolver = new PathResourceResolver();
            pathResourceResolver.setAllowedLocations(new ClassPathResource("META-INF/resources/webjars/"));
            pathResourceResolver.setUrlPathHelper(new UrlPathHelper());

            ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
            resourceHttpRequestHandler.setLocations(Arrays.asList(new ClassPathResource("META-INF/resources/webjars/")));
            resourceHttpRequestHandler.setResourceResolvers(Arrays.asList(pathResourceResolver));
            resourceHttpRequestHandler.setServletContext(servletContext);
            resourceHttpRequestHandler.afterPropertiesSet();
            // 设置新的路径
            urlMap.put(DEFAULT_PATH + "/webjars/**", resourceHttpRequestHandler);
        }
        {
            PathResourceResolver pathResourceResolver = new PathResourceResolver();
            pathResourceResolver.setAllowedLocations(new ClassPathResource("META-INF/resources/"));
            pathResourceResolver.setUrlPathHelper(new UrlPathHelper());

            ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
            resourceHttpRequestHandler.setLocations(Arrays.asList(new ClassPathResource("META-INF/resources/")));
            resourceHttpRequestHandler.setResourceResolvers(Arrays.asList(pathResourceResolver));
            resourceHttpRequestHandler.setServletContext(servletContext);
            resourceHttpRequestHandler.afterPropertiesSet();
            // 设置新的路径
            urlMap.put(DEFAULT_PATH + "/**", resourceHttpRequestHandler);
        }
        urlHandlerMapping.setUrlMap(urlMap);
        // 调整DispatcherServlet关于SimpleUrlHandlerMapping的排序
        urlHandlerMapping.setOrder(order);
        return urlHandlerMapping;
    }

    /**
     * SwaggerUI接口访问
     */
    @Controller
    @ApiIgnore
    @RequestMapping(DEFAULT_PATH)
    public static class SwaggerResourceController implements InitializingBean {

        @Autowired
        private ApiResourceController apiResourceController;

        @Autowired
        private Environment environment;

        @Autowired
        private DocumentationCache documentationCache;

        @Autowired
        private ServiceModelToSwagger2Mapper mapper;

        @Autowired
        private JsonSerializer jsonSerializer;

        private Swagger2Controller swagger2Controller;

        @Override
        public void afterPropertiesSet() {
            swagger2Controller = new Swagger2Controller(environment, documentationCache, mapper, jsonSerializer);
        }

        /**
         * 首页
         *
         * @return 新的模型，视图，指定返回页面名称
         */
        @RequestMapping
        public ModelAndView index() {
            ModelAndView modelAndView = new ModelAndView("redirect:" + DEFAULT_PATH + "/swagger-ui.html");
            return modelAndView;
        }

        @RequestMapping("/swagger-resources/configuration/security")
        @ResponseBody
        public ResponseEntity<SecurityConfiguration> securityConfiguration() {
            return apiResourceController.securityConfiguration();
        }

        @RequestMapping("/swagger-resources/configuration/ui")
        @ResponseBody
        public ResponseEntity<UiConfiguration> uiConfiguration() {
            return apiResourceController.uiConfiguration();
        }

        @RequestMapping("/swagger-resources")
        @ResponseBody
        public ResponseEntity<List<SwaggerResource>> swaggerResources() {
            return apiResourceController.swaggerResources();
        }

        @RequestMapping(value = "/v2/api-docs", method = RequestMethod.GET, produces = {"application/json", "application/hal+json"})
        @ResponseBody
        public ResponseEntity<Json> getDocumentation(@RequestParam(value = "group", required = false) String swaggerGroup, HttpServletRequest servletRequest) {
            return swagger2Controller.getDocumentation(swaggerGroup, servletRequest);
        }
    }

    /*---------------  华丽的分割线：以上为修改rest接口访问路径的方法，以下为系统配置的内容  -------------*/

    @Value("${ltsystem.sysmsg.title}")
    public String sysTitle = "系统名称";

    @Value("${ltsystem.sysmsg.desp}")
    public String sysDiscription = "系统描述";

    @Value("${ltsystem.sysmsg.host}")
    public String syshost = "localhost:8080";

    @Value("${ltsystem.swagger.serviceUrl}")
    public String serviceUrl = "localhost:8080/public";

    @Value("${ltsystem.swagger.serviceVersion}")
    public String serviceVersion = "v1.0";

    @Value("${spring.profiles.active}")
    public String nowProfiles = "prod";

    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .host(syshost) // host路径说明
                .pathMapping("/").apiInfo(apiInfo())   // spring disptacherServlet url-mapping前缀
                // .genericModelSubstitutes(CommonResult.class)        // TODO 这里暂时注释掉，有时间了再来研究这个。
                .directModelSubstitute(Date.class, String.class).useDefaultResponseMessages(false);         // 类型相关配置

        // ApiSelectorBuilder配置
        if (nowProfiles.equals("dev")) {
            docket.select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                    .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    .apis(RequestHandlerSelectors.basePackage("org.litu.app"))
                    .paths(PathSelectors.any()).build();
        } else {
            docket.select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                    .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    .apis(RequestHandlerSelectors.basePackage("org.litu.app.apiController"))
                    .paths(PathSelectors.any()).build();
        }


        return docket.enable(true);    // 启用
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 标题
                .title(sysTitle)
                // 描述
                .description(sysDiscription)
                // 服务地址
                .termsOfServiceUrl(serviceUrl)
                // 联系人
                .contact(new Contact("yueye", "https://github.com/yueyezu", "350221302@qq.com"))
                // 版本
                .version(serviceVersion).build();
    }
}