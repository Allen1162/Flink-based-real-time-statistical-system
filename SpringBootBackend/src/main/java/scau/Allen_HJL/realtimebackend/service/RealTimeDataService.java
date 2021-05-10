package scau.Allen_HJL.realtimebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;
import scau.Allen_HJL.realtimebackend.model.RedisMessageWarp;

@Service
public class RealTimeDataService {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private RedisMessageWarp redisMessageWarp;

    public RedisMessageWarp getRedisValueByKey(String key){
        byte[] bytes = redisConnectionFactory.getConnection().get(key.getBytes());
        redisMessageWarp.setTopic(key);
        redisMessageWarp.setMessage(new String(bytes));
        return redisMessageWarp;
    }

}
