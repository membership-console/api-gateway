package cc.rits.membership.console.api_gateway.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザモデル
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements Serializable {

    /**
     * ユーザID
     */
    Integer id;

    /**
     * ファーストネーム
     */
    String firstName;

    /**
     * ラストネーム
     */
    String lastName;

    /**
     * メールアドレス
     */
    String email;

    /**
     * 入学年度
     */
    Integer entranceYear;

    /**
     * ユーザグループリスト
     */
    List<UserGroupModel> userGroups;

}
