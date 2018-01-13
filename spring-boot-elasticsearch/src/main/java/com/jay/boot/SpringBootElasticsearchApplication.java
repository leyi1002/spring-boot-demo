package com.jay.boot;

import com.jay.boot.utils.ESUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootElasticsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootElasticsearchApplication.class, args);

	}
}
