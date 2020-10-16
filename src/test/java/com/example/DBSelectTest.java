package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DBSelectTest {

	@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
	@TestExecutionListeners({
	      DependencyInjectionTestExecutionListener.class,
	      TransactionalTestExecutionListener.class,
	      DbUnitTestExecutionListener.class
	    })
	@SpringBootTest(classes = {DaoTestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
	public class DBSelectTest {

	    @Autowired
	    private UserDao userDao;

	    // DatabaseSetupのvalueにCSVファイルのパスを設定することで、「table-ordering.txt」を参照し、
	    // 順次テーブルを作成することでテスト用のテーブル群を作成する
	    // このとき、valueのパスは「src/test/recources」配下を起点とし、CSVファイルのファイル名は
	    // テーブルの名前と対応させることとする
	    //
	    // また、@Transactionalアノテーションを付与することで、テストが終わるとトランザクションをロールバックすることで
	    // 環境を汚すことなくテストができる
	    @Test
	    @DatabaseSetup(value = "/testData/")
	    @Transactional
	    public void contextLoads() throws Exception {
	        List<User> userList = userDao.findAllUser();

	        // Daoで正常にテーブルからレコードを取得できたか
	        assertThat(userList.size(), is(2));
	    }

	}
}
