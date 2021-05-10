package scau.Allen_HJL.realtimebackend.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import scau.Allen_HJL.realtimebackend.comment.RedisReceiver;

@Configuration
public class RedisSubListenerConfiguration {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory,
                                            MessageListenerAdapter listenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("TEST"));
        container.addMessageListener(listenerAdapter, new PatternTopic("USER:OUTCOME:SORT"));
        container.addMessageListener(listenerAdapter, new PatternTopic("USER:OUTCOME"));
        container.addMessageListener(listenerAdapter, new PatternTopic("USER:SOURCE:NUM"));
        container.addMessageListener(listenerAdapter, new PatternTopic("ITEM:TYPE:SUMNUM:SUMPRICE"));
        container.addMessageListener(listenerAdapter, new PatternTopic("ITEM:TYPE:SUMNUM:SUMPRICE:SORTSUMNUM"));
        container.addMessageListener(listenerAdapter, new PatternTopic("ITEM:TYPE:SUMNUM:SUMPRICE:SORTSUMPRICE"));
        container.addMessageListener(listenerAdapter, new PatternTopic("HISTORY:ITEM"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RedisReceiver redisReceiver){
        return new MessageListenerAdapter(redisReceiver, "receiveMessage");
    }
}
