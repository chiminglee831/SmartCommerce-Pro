package com.smartcommerce.api_gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 為每個請求生成唯一ID
        String requestId = UUID.randomUUID().toString();
        
        LOGGER.info("Request {} started: {} {}", 
            requestId,
            exchange.getRequest().getMethod(),
            exchange.getRequest().getURI()
        );
        
        // 設置請求開始時間
        long startTime = System.currentTimeMillis();
        
        // 添加請求標頭
        exchange.getRequest().mutate()
            .header("X-Request-ID", requestId)
            .build();
        
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // 計算請求處理時間
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            LOGGER.info("Request {} completed: {} ms, Status: {}", 
                requestId,
                executionTime,
                exchange.getResponse().getStatusCode()
            );
        }));
    }

    @Override
    public int getOrder() {
        // 設置過濾器順序為最高優先級
        return Ordered.HIGHEST_PRECEDENCE;
    }
}