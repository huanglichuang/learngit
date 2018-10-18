package EsMethod;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Map;

/**
 * ES搜索的服务实现
 *
 * @author DAVIDYIN
 * @date 2018/09/15
 */
@Data
public class EsQueryService {

    /**
     * 根据文档ID查询ES索引
     *
     * @param rhlClient
     * @param index     索引名
     * @param type      索引类型
     * @param id        文档id
     * @return 查询结果
     */
    public GetResponse queryIndexById(RestHighLevelClient rhlClient, String index, String type, String id) throws
            ElasticsearchException {
        GetRequest getRequest = new GetRequest(index, type, id);
        GetResponse getResponse = null;
        try {
            getResponse = rhlClient.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
//            log.error("IOException in queryIndexById", e);
            throw new ElasticsearchException(e);
        }
        return getResponse;
    }

    /**
     * 根据文档ID查询源文档
     *
     * @param rhlClient
     * @param index     索引名
     * @param type      索引类型
     * @param id        文档id
     * @return 源文档Map
     */
    public Map<String, Object> getSource(RestHighLevelClient rhlClient, String index, String type, String id) {
        return queryIndexById(rhlClient, index, type, id).getSource();
    }

    /**
     * 根据Field名称获取源文档的value
     *
     * @param rhlClient
     * @param index     索引名
     * @param type      索引类型
     * @param id        文档id
     * @param name      Field名
     * @return 不存在返回null
     */
    public Object getSourceValue(RestHighLevelClient rhlClient, String index, String type, String id, String name) {
        return MapUtils.getObject(getSource(rhlClient, index, type, id), name);
    }

    /**
     * 数据根据条件搜索
     *
     * @param rhlClient
     * @param searchRequest
     * @return
     */
    public SearchResponse search(RestHighLevelClient rhlClient, SearchRequest searchRequest) {
        SearchResponse response = null;
        try {
            response = rhlClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
//            log.error("IOException in search", e);
            throw new ElasticsearchException(e);
        }
        return response;
    }

}
