package TagFromRedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnection {
    private static String HOST = "127.0.0.1";
    private static int PORT = 6379;
    private static int MAX_ACTIVE = 1024;
    private static int MAX_IDLE = 200;
    private static int MAX_WAIT = 10000;

    private static JedisPool jedisPool = null;

    /*
     * 初始化redis连接池
     * */
    private static void initPool () {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);//最大连接数
            config.setMaxIdle(MAX_IDLE);//最大空闲连接数
            config.setMaxWaitMillis(MAX_WAIT);//获取可用连接的最大等待时间


            jedisPool = new JedisPool(config, HOST, PORT, 2000, "123456", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 获取jedis实例
     * */
    public synchronized static Jedis getJedis () {
        try {
            if (jedisPool == null) {
                initPool();
            }
            Jedis jedis = jedisPool.getResource();
            jedis.auth("123456");//密码
            return jedis;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
