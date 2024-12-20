package be.pxl.services.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class ProxyApi {
    @Autowired
    ZuulProperties properties;
    @Primary
    @Bean
    public SwaggerResourcesProvider
    swaggerResourcesProvider() {
        return () -> {
            List resources = new ArrayList();
            properties.getRoutes().values().stream().forEach(route ->
                    resources.add(createResource(route.getServiceId(), route.getId(), "2.0")));
            return resources;
        };
    }
    private SwaggerResource createResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new
                SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/" + location + "/v2/apidocs");
        swaggerResource.setSwaggerVersion(version);return swaggerResource;
    }
}