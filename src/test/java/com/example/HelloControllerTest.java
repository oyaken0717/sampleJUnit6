package com.example;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(classes = SampleJUnit6Application.class)
public class HelloControllerTest {

	// mockMvc TomcatサーバへデプロイすることなくHttpリクエスト・レスポンスを扱うためのMockオブジェクト
	@Autowired
	private MockMvc mockMvc;

	// getリクエストでviewを指定し、httpステータスでリクエストの成否を判定
	@Test
    void init処理が走って200が返る() throws Exception {
        // andDo(print())でリクエスト・レスポンスを表示
        this.mockMvc
        	.perform(get("/hello/init"))
        	.andDo(print())
            .andExpect(status().isOk());
    }
	
//	@Test
//    void init処理でモデルのメッセージにhelloが渡される() throws Exception {
//        this.mockMvc.perform(get("/hello/init"))
//            .andExpect(model().attribute("message", "hello!"));
//    }
//	
//	@Test
//    void init処理でモデルへユーザEntityが格納される() throws Exception {
//		//■ UserオブジェクトはuserNameというプロパティを持っており、値はtest0です。		
//        this.mockMvc.perform(get("/hello/init"))
//            .andExpect(model()
//                    .attribute("user", hasProperty("userName", is("test0"))
//                            )
//                    );
//    }
//	
//	// リスト要素については、hasItemで順番を問わずリストへアクセスし、指定されたプロパティが指定の値となる要素が存在するかを検証。
//    // 存在する場合のみテストをグリーンとする
//    @Test
//    void init処理でモデルのフォームへユーザリストが格納される() throws Exception {
//
//    	//■ hasItemというメソッドが出てきました。リスト形式のオブジェクトに対してのみ使用可能
//    	//  ユーザリストの中の各ユーザ要素について、「userName」プロパティが「test1」であるものが一つでも存在するかが検証したいこと、となります。
//    	//  リストの要素を丸ごと全て検証するのではなく、代表要素として、一部さえ検証できればよい
//        this.mockMvc.perform(get("/hello/init"))
//            .andExpect(model().attribute("dbForm", hasProperty(
//                    "userList", hasItem(
//                        hasProperty(
//                                "userName", is("test1")
//                        )
//                    )
//            )));
}