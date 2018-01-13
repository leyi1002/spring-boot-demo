package com.jay.boot.utils;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

/**
 * Created by Administrator on 2017/12/28.
 */
public class ESUtils {

    private static final String HOST = "localhost";
    private static final int PORT = 9200;
    private static final String SCHEMA = "http";
    private static final String USERNAME="elastic";
    private static final String PASSWORD="******";


    public static RestHighLevelClient getInstance() {
        //设置ssl身份验证
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(HOST, PORT, SCHEMA)).setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)));
        return client;
    }

    /**
     * 添加索引文档
     * @param index
     * @param type
     * @param id
     * @param jsonSource
     * @return
     */
    public static IndexResponse postIndex(String index, String type, String id, String jsonSource) {
        RestHighLevelClient client = getInstance();
        // 构建请求
        IndexRequest request = new IndexRequest(index, type, id);
        // 将保存数据以JSON格式关联到请求
        request.source(jsonSource, XContentType.JSON);
        // Java客户端发起保存数据请求
        IndexResponse response = null;
        try {
            response = getInstance().index(request);
            // 等待结果
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public static SearchResponse search(String index, String type, String id){
        RestHighLevelClient client = getInstance();

        SearchRequest searchRequest = new SearchRequest();
        //若不存在索引库，设置后不报异常
        searchRequest.indicesOptions(IndicesOptions.lenientExpandOpen());
        searchRequest.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("address","mill"));
        builder.sort(new FieldSortBuilder("account_number").order(SortOrder.ASC));
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("group_by_gender")
                .field("gender.keyword");
        aggregation.subAggregation(AggregationBuilders.avg("avg_balance")
                .field("balance"));
        builder.aggregation(aggregation);

        searchRequest.source(builder);
        SearchResponse search= null;
        try {
             search = client.search(searchRequest);
            System.out.println(search);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return search;
    }


}
