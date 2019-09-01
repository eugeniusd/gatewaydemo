package com.adidas.gatewaydemo.route.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

@Component
public class XRequestFilter extends AbstractGatewayFilterFactory<XRequestFilter.Config> {

    private static final String X_REQUEST_ID_HEADER = "X-Request-ID";
    public static final String TEST_HEADER = "X-Test-Header";

    public XRequestFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            checkHeader(request);
            return chain.filter(exchange.mutate()
                    .request(request)
                    .build());
        };
    }

    private void checkHeader(ServerHttpRequest request) {
        List<String> xIdHeaders = request.getHeaders().get(X_REQUEST_ID_HEADER);

        if (CollectionUtils.isEmpty(xIdHeaders)) {
            request.mutate()
                    .header(X_REQUEST_ID_HEADER, UUID.randomUUID().toString())
                    .build();
        }

        List<String> testHeaders = request.getHeaders().get(TEST_HEADER);
        if (CollectionUtils.isEmpty(testHeaders)) {
            request.mutate()
                    .header(TEST_HEADER, "TEST_HEADER")
                    .build();
        }
    }

    static class Config {}
}
