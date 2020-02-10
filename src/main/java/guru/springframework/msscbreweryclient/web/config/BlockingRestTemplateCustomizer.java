package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {
    private final int poolingMaxTotal;
    private final int poolingMaxPerRoute;

    private final int requestConnectionTimeout;
    private final int requestSocketTimeout;

    public BlockingRestTemplateCustomizer(@Value("${pooling.maxTotal}") int poolingMaxTotal,
                                          @Value("${pooling.maxPerRoute}") int poolingMaxPerRoute,
                                          @Value("${request.connectionTimeout}") int requestConnectionTimeout,
                                          @Value("${request.socketTimeout}") int requestSocketTimeout) {
        this.poolingMaxTotal = poolingMaxTotal;
        this.poolingMaxPerRoute = poolingMaxPerRoute;
        this.requestConnectionTimeout = requestConnectionTimeout;
        this.requestSocketTimeout = requestSocketTimeout;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(poolingMaxTotal);
        connectionManager.setDefaultMaxPerRoute(poolingMaxPerRoute);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(requestConnectionTimeout)
                .setSocketTimeout(requestSocketTimeout)
                .build();

        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }
}
