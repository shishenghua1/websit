package com.boco.eoms.base.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**

* 创建时间：2019年6月4日 上午9:01:44

* 项目名称：central-flow-platform-rule

* @author ssh
* 类说明： Swagger配置类

*/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        //设置参数
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("websit_token").description("网站token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.boco.eoms.websit")) //Controller所在包(必须新建包)
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("网站API")  //标题
                .description("Restful API")  //描述
                .termsOfServiceUrl("")  //超链接
                .version("1.0")
                .build();
    }
}

