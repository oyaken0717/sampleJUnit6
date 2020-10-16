package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

class CRUDDaoTest {

	// テストメソッド実行後の状態をデータベースに反映させるための処理
    // 通常、更新系の処理はトランザクションがコミットされるタイミングでデータベースと同期化されるが、
    // テスト処理ではコミットしないため、明示的に同期化を行う
    @AfterEach
    void tearDown() {
        userDao.getEm().flush();
    }

    /**
     * create処理で新規レコードが作成されるか検証する
     * エンティティによってDBが想定通りに書き換えられたかExpectedDatabaseと比較することで検証
     */
    @Test
    @DatabaseSetup(value = "/CRUD/setUp/forCreate")
    @ExpectedDatabase(value = "/CRUD/create/", assertionMode=DatabaseAssertionMode.NON_STRICT)
    void createメソッドでユーザが新しく生成される() {
        User user = new User();
        user.setUserName("test3");

        userDao.saveOrUpdate(user);
    }
    
    /**
     * update処理で既存レコードがupdateされるか検証する
     * エンティティによってDBが想定通りに書き換えられたかExpectedDatabaseと比較することで検証
     */
    @Test
    @DatabaseSetup(value = "/CRUD/setUp/")
    @ExpectedDatabase(value = "/CRUD/update/", assertionMode=DatabaseAssertionMode.NON_STRICT)
    void updateメソッドでユーザ1を書き換えられる() {
        User user = new User();
        user.setUserId(1L);
        user.setUserName("test1mod");

        userDao.saveOrUpdate(user);
    }

    /**
     * delete処理でレコードが削除されるか検証する
     * 処理前後のDBを用意し、削除後に想定結果となるか比較することで妥当性を検証
     */
    @Test
    @DatabaseSetup(value = "/CRUD/setUp/")
    @ExpectedDatabase(value = "/CRUD/delete/", assertionMode=DatabaseAssertionMode.NON_STRICT)
    void deleteメソッドでユーザ1を削除できる() {
        userDao.delete(1);
    }
}
