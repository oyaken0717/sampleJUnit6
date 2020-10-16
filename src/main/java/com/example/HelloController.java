//■ https://qiita.com/a-pompom/items/3f834119c756e5286730

package com.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
//@RequestMapping("/hello")
//public class HelloController {
//
//    @RequestMapping("/init")
//    private String init() {
//        return "hello";
//    }
//}

@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/init")
    private String init(Model model) {

//        // ユーザリスト まずは手動で生成
//        List<User> userList = new ArrayList<User>();
//
//        User user = new User();
//        user.setUserId(0L);
//        user.setUserName("test0");
//
//        User user2 = new User();
//        user2.setUserId(1L);
//        user2.setUserName("test1");
//
//        userList.add(user);
//        userList.add(user2);
//
//        // フォームにユーザのリストを設定し、モデルへ追加することでモデルへ正常に追加されたか検証するための土台を整える
//        DbForm form = new DbForm();
//        form.setUserList(userList);
//
//        model.addAttribute("message", "hello!");// 1
//
//        model.addAttribute("user", user);// 2
//
//        model.addAttribute("dbForm", form);// 3

        return "hello";
    }

}