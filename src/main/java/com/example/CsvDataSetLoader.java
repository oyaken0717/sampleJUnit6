package com.example;

import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;

//■ CSVファイルをテストで利用するためのクラスを作成

//■　AbstractDataSetLoader > データセットを読みこむための抽象クラス
public class CsvDataSetLoader extends AbstractDataSetLoader {

	@Override
	//■ resourceオブジェクトは処理対象のCSVファイルのパスが格納されている
	protected IDataSet createDataSet(Resource resource) throws Exception {
		//■　resourceオブジェクトをもとにCSVの実ファイルを取得し、データセットオブジェクトへ変換
		return new CsvURLDataSet(resource.getURL());
	}
}
