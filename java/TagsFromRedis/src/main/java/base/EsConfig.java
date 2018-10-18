package base;

import com.google.common.collect.Lists;
import dto.EsHost;
import lombok.Data;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * ES的配置信息
 *
 * @author DAVIDYIN
 * @date 2018/09/11
 */
@Data
public class EsConfig {

    // DMP ES hosts, various hosts please split by ";"
    private String hosts = "127.0.0.1:9200";

    private List<EsHost> esHostList = Lists.newArrayList();

    private String clusterName="dmp_search_es_cluster";

    private int connectTimeout=1000;

    private int socketTimeout=30000;

    private int connectionRequestTimeout=500;

    private String adIndexName="dmp_ad_index3";

    private String adTypeName="chuzhi_ad_term";

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        initEsHostList();
    }

    /**
     * 初始化esHostList
     */
    public List<EsHost> initEsHostList() {
        String[] esHostStrArray = hosts.split(";");
        for (String esHostStr : esHostStrArray) {
            String[] esHostPort = esHostStr.split(":");
            String hostAddress = esHostPort[0];
            int hostPort = Integer.parseInt(esHostPort[1]);
            EsHost esHost = new EsHost();
            esHost.setHostAddress(hostAddress);
            esHost.setHostPort(hostPort);
            esHostList.add(esHost);
        }
        return esHostList;
    }

}
