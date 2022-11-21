package cc.rits.membership.console.api_gateway.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザグループモデル
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupModel implements Serializable {

    /**
     * ユーザグループID
     */
    Integer id;

    /**
     * ユーザグループ名
     */
    String name;

    /**
     * ロールIDリスト
     */
    List<Integer> roles;

}
