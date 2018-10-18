package EsMethod;

import Model.UserInfo;
import Utils.JacksonUtils;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.engine.DocumentMissingException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ES写入的服务实现
 *
 * @author DAVIDYIN
 * @date 2018/09/15
 */
@Data
public class EsIndexService {

    private EsQueryService esQueryService;

    /**
     * 插入数据
     *
     * @param rhlClient
     * @param index
     * @param type
     * @param bo
     * @return response of document in ES
     * @throws ElasticsearchException
     */
    public IndexResponse index(RestHighLevelClient rhlClient, String index, String type, Object bo)
            throws ElasticsearchException {
        IndexRequest indexRequest = this.generateRequest(bo, index, type);
        IndexResponse response = null;
        try {
            response = rhlClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
//            log.error("IOException in index", e);
            throw new ElasticsearchException(e);
        }
        return response;
    }

    /**
     * 批量插入
     *
     * @param rhlClient
     * @param index
     * @param type
     * @param boList
     * @throws ElasticsearchException
     */
    public void batchIndex(RestHighLevelClient rhlClient, String index, String type, List<UserInfo>
            boList) throws ElasticsearchException {
        BulkRequest bulkRequest = new BulkRequest();
        List<IndexRequest> requestList = this.generateRequestList(boList, index, type);
        for (IndexRequest indexRequest : requestList) {
            bulkRequest.add(indexRequest);
        }
        try {
            rhlClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
//            log.error("IOException in batchIndex", e);
            throw new ElasticsearchException(e);
        }
    }

    /**
     * 更新ES索引文档, 此种方式是会直接覆盖之前的记录，注意如果文档时不存在则会抛出{@link DocumentMissingException}
     *
     * @param rhlClient 索引客户端
     * @param index     索引
     * @param type      索引类型
     * @param bo        更新成功后，记录中含有的字段值
     * @return 返回UpdateResponse
     */
    public UpdateResponse updateDoc(RestHighLevelClient rhlClient, String index, String type, UserInfo
            bo) throws Exception {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index);
        updateRequest.type(type);
        updateRequest.id(String.valueOf(bo.getUid()));
        updateRequest.doc(FastJsonUtil.toJSON(bo));
        return rhlClient.update(updateRequest, RequestOptions.DEFAULT);
    }

    /**
     * 更新ES索引文档, 如果文档不存在则新加，如果存在则更新
     *
     * @param rhlClient 索引客户端
     * @param index     索引
     * @param type      索引类型
     * @param bo        需要更新的字段值
     * @param recordId
     * @return 返回UpdateResponse
     */
    public UpdateResponse upsertDoc(RestHighLevelClient rhlClient, String index, String type, Object
            bo, String recordId) throws IOException {
        String json = FastJsonUtil.toJSONString(bo);
        IndexRequest indexRequest = new IndexRequest(index, type, recordId);
        indexRequest.source(json, XContentType.JSON);
        UpdateRequest updateRequest = new UpdateRequest(index, type, recordId);
        XContentBuilder content = XContentFactory.jsonBuilder();
        Map<String, Object> map = JacksonUtils.jsonToMap(json);
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        content.startObject();
        for (Map.Entry<String, Object> entry : entrySet) {
            content.field(entry.getKey(), entry.getValue());
        }
        content.endObject();
        updateRequest.doc(content);
        updateRequest.upsert(indexRequest);
        return rhlClient.update(updateRequest, RequestOptions.DEFAULT);
    }

    /**
     * 判断指定的记录是否存在
     *
     * @param rhlClient 索引客户端
     * @param index     索引名
     * @param type      索引类型
     * @param id        文档id
     * @return true存在 false不存在
     */
    public boolean isExists(RestHighLevelClient rhlClient, String index, String type, String id) {
        return esQueryService.queryIndexById(rhlClient, index, type, id).isExists();
    }

    /**
     * 判断源文档的source field名是否存在
     *
     * @param rhlClient 索引客户端
     * @param index     索引名
     * @param type      索引类型
     * @param id        文档id
     * @param name      属性名
     * @return true存在 false不存在
     */
    public boolean isExists(RestHighLevelClient rhlClient, String index, String type, String id, String name) {
        return esQueryService.getSource(rhlClient, index, type, id).containsKey(name);
    }

    /**
     * 构造IndexRequest的List
     *
     * @param boList
     * @param index
     * @param type
     * @return
     */
    private List<IndexRequest> generateRequestList(List<UserInfo> boList, String index, String type) {
        List<IndexRequest> requestList = Lists.newArrayList();
        for (Object bo : boList) {
            IndexRequest indexRequest = this.generateRequest(bo, index, type);
            requestList.add(indexRequest);
        }
        return requestList;
    }

    /**
     * 生成IndexRequest
     *
     * @param bo
     * @param index
     * @param type
     * @return
     */
    private IndexRequest generateRequest(Object bo, String index, String type) {
        IndexRequest indexRequest = new IndexRequest().index(index).type(type);
        indexRequest.source(FastJsonUtil.toJSONString(bo), XContentType.JSON);
        return indexRequest;
    }

}
