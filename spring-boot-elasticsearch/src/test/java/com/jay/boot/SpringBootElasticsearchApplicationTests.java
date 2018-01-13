package com.jay.boot;

import com.jay.boot.utils.ESUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootElasticsearchApplicationTests {

	@Test
	public void contextLoads() {
		String jsonString = "{" + "\"user\":\"kimchy\"," + "\"postDate\":\"2013-01-30\","
				+ "\"message\":\"trying out Elasticsearch\"" + "}";
		IndexResponse indexResponse = ESUtils.postIndex("posts", "doc", "1", jsonString);
		System.out.println(indexResponse);
	}


	@Test
	public void search() {
		SearchResponse posts = ESUtils.search("bank", "", "");
		SearchHit[] hits = posts.getHits().getHits();
		for(SearchHit hit:hits){
			String source = hit.getSourceAsString();
			System.out.println(source);
		}
	}


    /**
     * term查询并排序
     */
	@Test
	public void termQuery(){
		RestHighLevelClient client = ESUtils.getInstance();
		SearchRequest searchRequest = new SearchRequest();
		//若不存在索引库，设置后不报异常
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.termQuery("address","mill"));
		builder.sort(new FieldSortBuilder( "account_number").order(SortOrder.ASC));

		searchRequest.source(builder);
		SearchResponse searchResponse = execution(client, searchRequest);

        SearchHit[] hits = searchResponse.getHits().getHits();
        for(SearchHit hit:hits){
            String source = hit.getSourceAsString();
            //打印每个查询结果
            System.out.println(source);
        }
	}

	/**
	 * 聚合查询(字段分组求平均值),打印结果
	 */
	@Test
	public void agg(){
		RestHighLevelClient client = ESUtils.getInstance();
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder builder = new SearchSourceBuilder();

		TermsAggregationBuilder aggregation = AggregationBuilders.terms("group_by_gender")
				.field("gender.keyword");
		aggregation.subAggregation(AggregationBuilders.avg("avg_balance")
				.field("balance"));

		builder.aggregation(aggregation);
		searchRequest.source(builder);
		SearchResponse searchResponse = execution(client, searchRequest);

		Aggregations aggregations = searchResponse.getAggregations();
		Terms group_by_gender = aggregations.get("group_by_gender");
		List<? extends Terms.Bucket> buckets = group_by_gender.getBuckets();
		buckets.forEach(b ->{
            Aggregations agg = b.getAggregations();
            String key = b.getKeyAsString();
            Avg avg =  b.getAggregations().get("avg_balance");
            System.out.println("sex:"+key +",avg:" +avg.getValueAsString());
        });
	}


	/**
	 * 执行搜索
	 * @param client
	 * @param searchRequest
	 * @return
	 */
	public SearchResponse execution(RestHighLevelClient client,SearchRequest searchRequest){
		SearchResponse search = null;
		try {
			search = client.search(searchRequest);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			close(client);
		}
		return search;
	}

	/**
	 * 关闭连接
	 * @param client
	 */
	public void close(RestHighLevelClient client){
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
