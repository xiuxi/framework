package com.framework.module.common.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

/**
 * Lucene 工具类
 * 
 * @author qq
 * 
 */
public class LuceneUtils {
	/**
	 * 高亮处理
	 * 
	 * T最好不要是Entity,否则可能导致改变Entity
	 * 
	 * @param luceneQuery
	 *            lucene query
	 * 
	 * @param hightLightDatas
	 *            要高亮处理的数据
	 * 
	 * @param hightLightClazz
	 *            T对象的Class
	 * 
	 * @param analyzer
	 *            分词器
	 * 
	 * @param fields
	 *            要高亮的字段(T对象中要高亮的属性名称)
	 * 
	 * @return 高亮处理过的数据
	 */
	public static <T> List<T> hightLight(Query luceneQuery, List<T> hightLightDatas, Class<T> hightLightClazz, Analyzer analyzer, String... fields) {
		if (hightLightDatas != null && !hightLightDatas.isEmpty()) {
			// 关键字样式
			Formatter formatter = new SimpleHTMLFormatter("<em style=\"color:red\">", "</em>");
			QueryScorer queryScorer = new QueryScorer(luceneQuery);
			Highlighter highlighter = new Highlighter(formatter, queryScorer);

			for (int i = 0; i < hightLightDatas.size(); i++) {
				T item = hightLightDatas.get(i);

				for (String fieldName : fields) {
					Object fieldValue = ReflectionUtils.invokeMethod(BeanUtils.getPropertyDescriptor(hightLightClazz, fieldName).getReadMethod(), item);
					String hightLightFieldValue = null;
					try {
						hightLightFieldValue = highlighter.getBestFragment(analyzer, fieldName, String.valueOf(fieldValue));
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("高亮显示关键字失败");
						// log.error(e, "高亮显示关键字失败");
					}

					// 高亮成功则重新赋值
					if (hightLightFieldValue != null) {
						ReflectionUtils.invokeMethod(BeanUtils.getPropertyDescriptor(hightLightClazz, fieldName).getWriteMethod(), item, hightLightFieldValue);
					}
				}
			}
		}

		return hightLightDatas;
	}

	/**
	 * 获取指定分词器的分词结果
	 * 
	 * @param analyzeStr
	 *            要分词的字符串
	 * 
	 * @param analyzer
	 *            分词器
	 * 
	 * @return 分词结果
	 */
	public List<String> getAnalyseResult(String analyzeStr, Analyzer analyzer) {
		List<String> response = new ArrayList<String>();
		TokenStream tokenStream = null;
		try {
			tokenStream = analyzer.tokenStream("content", new StringReader(analyzeStr));
			CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
			tokenStream.reset();
			while (tokenStream.incrementToken()) {
				response.add(attr.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (tokenStream != null) {
				try {
					tokenStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}
}
