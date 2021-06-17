package com.example.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author colin
 */
@Configuration
@EnableConfigurationProperties(ElasticSearchProperties.class)
public class ElasticSearchConfig {
    private final ElasticSearchProperties elasticSearchProperties;
    private List<HttpHost> httpHosts = new ArrayList<>();

    public ElasticSearchConfig(ElasticSearchProperties elasticSearchProperties) {
        this.elasticSearchProperties = elasticSearchProperties;
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }
//        @Bean
//        @ConditionalOnMissingBean
//        public RestHighLevelClient restHighLevelClient() {
//            List<String> clusterNodes = elasticSearchProperties.getClusterNodes();
//            if (clusterNodes.isEmpty()) {
//                throw new RuntimeException("集群节点不允许为空");
//            }
//            clusterNodes.forEach(node -> {
//                try {
//                    String[] parts = StringUtils.split(node, ":");
//                    Assert.notNull(parts, "Must defined");
//                    Assert.state(parts.length == 2, "Must be defined as 'host:port'");
//                    httpHosts.add(new HttpHost(parts[0], Integer.parseInt(parts[1]), elasticSearchProperties.getSchema()));
//                } catch (Exception e) {
//                    throw new IllegalStateException("Invalid ES nodes " + "property '" + node + "'", e);
//                }
//            });
//            RestClientBuilder builder = RestClient.builder(httpHosts.toArray(new HttpHost[0]));
//            return getRestHighLevelClient(builder, elasticSearchProperties);
//        }

    private static RestHighLevelClient getRestHighLevelClient(RestClientBuilder builder, ElasticSearchProperties elasticSearchProperties) {
        // Callback used the default {@link RequestConfig} being set to the {@link CloseableHttpClient}
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(elasticSearchProperties.getConnectTimeout());
            requestConfigBuilder.setSocketTimeout(elasticSearchProperties.getSocketTimeout());
            requestConfigBuilder.setConnectionRequestTimeout(elasticSearchProperties.getConnectionRequestTimeout());
            return requestConfigBuilder;
        });
        // Callback used to customize the {@link CloseableHttpClient} instance used by a {@link RestClient} instance.
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(elasticSearchProperties.getMaxConnectTotal());
            httpClientBuilder.setMaxConnPerRoute(elasticSearchProperties.getMaxConnectPerRoute());
            return httpClientBuilder;
        });
        // Callback used the basic credential auth
        ElasticSearchProperties.Account account = elasticSearchProperties.getAccount();
        if (!StringUtils.isEmpty(account.getUsername()) && !StringUtils.isEmpty(account.getUsername())) {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(account.getUsername(), account.getPassword()));
        }
        return new RestHighLevelClient(builder);
    }
}
