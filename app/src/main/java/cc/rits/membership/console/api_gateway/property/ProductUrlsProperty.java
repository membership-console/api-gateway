package cc.rits.membership.console.api_gateway.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * プロダクトURLリストプロパティ
 */
@Data
@Configuration
@ConfigurationProperties("product-urls")
public class ProductUrlsProperty {

    /**
     * IAM
     */
    String iam;

}
