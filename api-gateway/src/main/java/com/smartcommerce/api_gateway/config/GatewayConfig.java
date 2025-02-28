package com.smartcommerce.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            // 用戶服務路由
            .route("user-service-route", r -> r.path("/api/users/**")
                .uri("lb://user-service"))
            // 產品服務路由
            .route("product-service-route", r -> r.path("/api/products/**")
                .uri("lb://product-service"))
            // 訂單服務路由
            .route("order-service-route", r -> r.path("/api/orders/**")
                .uri("lb://order-service"))
            // 庫存服務路由
            .route("inventory-service-route", r -> r.path("/api/inventory/**")
                .uri("lb://inventory-service"))
            // 支付服務路由
            .route("payment-service-route", r -> r.path("/api/payments/**")
                .uri("lb://payment-service"))
            .build();
    }
}