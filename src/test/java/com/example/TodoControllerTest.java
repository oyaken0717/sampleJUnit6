package com.example;

import java.awt.PageAttributes.MediaType;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@AutoConfigureMockMvc
@SpringBootTest(classes = { DbMvcTestApplication.class })
@Transactional
public class TodoControllerTest {

	// mockMvc TomcatサーバへデプロイすることなくHttpリクエスト・レスポンスを扱うためのMockオブジェクト
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TodoDao todoDao;

	@AfterEach
	void tearDown() {
		todoDao.getEm().flush();
	}

	/**
	 * viewが正しく返されるか検証
	 * 
	 * @throws Exception
	 */
	@Test
	void init処理でviewとしてtodoが渡される() throws Exception {
		this.mockMvc.perform(get("/todo/init")).andExpect(status().isOk()).andExpect(view().name("todo"));
	}

	/**
	 * モデルへDBから取得したレコードが設定されたか検証する
	 * 今回は複雑な処理でもないので、DBの中の1レコードがモデルに渡されていれば正常に動作しているとみなした
	 * 
	 * @throws Exception
	 */
	@Test
	@DatabaseSetup(value = "/TODO/setUp/")
	void init処理で既存のタスクがモデルへ渡される() throws Exception {

		// mockMvcで「/todo/init」へgetリクエストを送信
		this.mockMvc.perform(get("/todo/init"))
				// モデルへDBのレコードがリストとして渡される
				.andExpect(model().attribute("todoForm",
						hasProperty("todoList", hasItem(hasProperty("task", is("task1"))))));
	}

	/**
	 * 画面の入力から新規レコードがDBへ登録されるか検証
	 * 
	 * @throws Exception
	 */
	@Test
	@DatabaseSetup(value = "/TODO/setUp/create")
	@ExpectedDatabase(value = "/TODO/create/", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void save処理で新規タスクがDBへ登録される() throws Exception {

		this.mockMvc.perform(
				post("/todo/save").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("newTask", "newTask"));

	}

	/**
	 * 画面の入力で既存レコードが更新されるか検証 今回は画面情報を利用しないので、対象の自動採番されるIDを取得することができない。
	 * そのため、今回はアップデート対象を手動で指定する。
	 * 基本的には、リストの順番は保証されないので、SELECT時にソートしておく等の処理は必要になると思われる
	 * 
	 * @throws Exception
	 */
	@Test
	@DatabaseSetup(value = "/TODO/setUp/")
	@ExpectedDatabase(value = "/TODO/update/", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void update処理で既存タスクが更新される() throws Exception {

		// mockMvcで「todo/update」へpostリクエストを送信
		long updateTargetId = 3L;
		int updateTargetIndex = 2;

		this.mockMvc.perform(post("/todo/update/" + updateTargetIndex + "/" + updateTargetId)
				.param("todoList[" + updateTargetIndex + "].task", "task3mod")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED));

	}

	/**
	 * 画面で選択したタスクが削除されるかどうか検証する
	 * 
	 * @throws Exception
	 */
	@Test
	@DatabaseSetup(value = "/TODO/setUp/")
	@ExpectedDatabase(value = "/TODO/delete/", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void delete処理で既存タスクが消去される() throws Exception {
		long deleteTargetId = 3L;

		this.mockMvc.perform(post("/todo/delete/" + deleteTargetId).contentType(MediaType.APPLICATION_FORM_URLENCODED));

	}
}