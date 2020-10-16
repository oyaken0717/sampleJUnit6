package com.example;

import java.util.List;

import org.apache.tomcat.jni.User;

public class UserDao {

	 /**
     * DBから全てのユーザレコードを取得する
     * 今回はテストのため、処理を簡単なものとした。
     * @return ユーザエンティティのリスト
     */
    public List<User> findAllUser() {

        QueryBuilder query = new QueryBuilder();

        query.append("select user_id, user_name from tm_user");

        return findResultList(query.createQuery(User.class, getEm()));
    }
}
