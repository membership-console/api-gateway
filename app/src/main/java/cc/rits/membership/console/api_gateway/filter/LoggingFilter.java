package cc.rits.membership.console.api_gateway.filter;

import java.util.stream.Collectors;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Logging Filter
 */
@Slf4j
@Component
public class LoggingFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {
        final var message = new StringBuilder();

        // HTTPリクエスト
        message.append(exchange.getRequest().getMethod()).append(" ");
        message.append(exchange.getRequest().getPath());

        // クエリパラメータ
        final var queryParams = exchange.getRequest().getQueryParams();
        if (!queryParams.isEmpty()) {
            final var queryString = queryParams.entrySet().stream() //
                .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue().get(0))) //
                .collect(Collectors.joining("&"));
            message.append("?").append(queryString);
        }

        // ログ出力
        log.info(message.toString());

        return chain.filter(exchange);
    }

}
