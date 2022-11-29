package cc.rits.membership.console.api_gateway.client;

import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import cc.rits.membership.console.api_gateway.model.UserModel;
import cc.rits.membership.console.api_gateway.property.ProductUrlsProperty;
import lombok.RequiredArgsConstructor;

/**
 * IAM Client
 * 
 * TODO: WebClientに移行
 */
@RequiredArgsConstructor
@Component
public class IamClient {

    private final ProductUrlsProperty productUrlsProperty;

    /**
     * ログインユーザを取得
     *
     * @param cookies cookies
     * @return ログインユーザ
     */
    public Optional<UserModel> getLoginUser(final MultiValueMap<String, HttpCookie> cookies) {
        final var restTemplate = new RestTemplate();

        final var headers = new HttpHeaders();
        final var sessionCookie = cookies.getFirst("SESSION");
        if (Objects.nonNull(sessionCookie)) {
            headers.set("Cookie", String.format("%s=%s", sessionCookie.getName(), sessionCookie.getValue()));
        }

        try {
            final var response = restTemplate.exchange( //
                String.format("%s/api/users/me", this.productUrlsProperty.getIam()), //
                HttpMethod.GET, //
                new HttpEntity<String>(headers), //
                UserModel.class //
            );
            return Optional.ofNullable(response.getBody());
        } catch (final RestClientException ignored) {
            return Optional.empty();
        }
    }

}
