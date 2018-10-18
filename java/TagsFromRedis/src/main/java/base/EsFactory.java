package base;

import dto.EsHost;
import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * ES的工厂
 *
 * @author David Yin
 * @date 2018/09/11
 */
@Data
public class EsFactory {

    private EsConfig esConfig = new EsConfig();

    private static volatile RestHighLevelClient restHighLevelClient;

    public RestHighLevelClient getRestHighLevelClient() {
        if (restHighLevelClient == null) {
            synchronized (EsFactory.class) {
                if (restHighLevelClient == null) {
                    HttpHost[] httpHostArray = this.buildHttpHostList();
                    RestClientBuilder restClientBuilder = RestClient.builder(httpHostArray);
                    restHighLevelClient = new RestHighLevelClient(restClientBuilder);
                }
            }
        }
        return restHighLevelClient;
    }

    /**
     * 构造HttpPost列表
     *
     * @return
     */
    private HttpHost[] buildHttpHostList() {
        HttpHost[] httpHostList = new HttpHost[esConfig.initEsHostList().size()];
        for (int i = 0; i < esConfig.getEsHostList().size(); i++) {
            EsHost esHost = esConfig.getEsHostList().get(i);
            HttpHost httpHost = new HttpHost(esHost.getHostAddress(), esHost.getHostPort(), "http");
            httpHostList[i] = httpHost;
        }
        return httpHostList;
    }

}
