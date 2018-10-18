package dto;

import lombok.Data;

/**
 * ES的HOST的定义
 *
 * @author DAVIDYIN
 * @date 2018/09/14
 */
@Data
public class EsHost {

    // 主机的地址
    private String hostAddress;

    // 主机的端口号
    private int hostPort;
}
