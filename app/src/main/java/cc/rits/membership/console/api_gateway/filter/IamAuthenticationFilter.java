package cc.rits.membership.console.api_gateway.filter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cc.rits.membership.console.api_gateway.client.IamClient;

/**
 * IAM Authentication Filter
 */
@Component
public class IamAuthenticationFilter extends AbstractGatewayFilterFactory<IamAuthenticationFilter.Config> {

    private final IamClient iamClient;

    private final ObjectMapper objectMapper;

    public IamAuthenticationFilter(final IamClient iamClient) {
        super(Config.class);
        this.iamClient = iamClient;
        this.objectMapper = new ObjectMapper().configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    }

    @Override
    public GatewayFilter apply(final Config config) {
        return (exchange, chain) -> {
            final var cookies = exchange.getRequest().getCookies();
            final var loginUser = this.iamClient.getLoginUser(cookies);

            // X-Membership-Console-Userヘッダにログインユーザ情報を入れる
            final var httpRequestBuilder = exchange.getRequest().mutate();
            if (loginUser.isPresent()) {
                try {
                    httpRequestBuilder.header( //
                        "X-Membership-Console-User",
                        new String(Base64.encodeBase64(this.objectMapper.writeValueAsString(loginUser).getBytes(), true)) //
                    );
                } catch (final JsonProcessingException ignored) {
                }
            }

            return chain.filter(exchange.mutate().request(httpRequestBuilder.build()).build());
        };
    }

    public static class Config {
    }

}
