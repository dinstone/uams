
package com.dinstone.uams.cache.redis;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import com.dinstone.security.Configuration;
import com.dinstone.security.model.Authentication;
import com.dinstone.uams.cache.AuthenCacheService;
import com.dinstone.uams.cache.JacksonSerializer;

@Service
public class RedisAuthenCacheService implements AuthenCacheService {

    private JacksonSerializer serializer = new JacksonSerializer();

    private JedisPool jedisPool;

    public RedisAuthenCacheService(Configuration config) {
        JedisPoolConfig jedisConfig = new JedisPoolConfig();
        jedisConfig.setMaxTotal(config.getInt("redis.pool.maxtotal", 10));
        jedisConfig.setMinIdle(config.getInt("redis.pool.minidle", 2));
        jedisConfig.setMaxWaitMillis(config.getLong("redis.pool.maxwait", 5000));
        jedisConfig.setTestOnBorrow(config.getBoolean("redis.pool.testonborrow", false));

        jedisConfig.setTestWhileIdle(true);
        jedisConfig.setMinEvictableIdleTimeMillis(60000);
        jedisConfig.setTimeBetweenEvictionRunsMillis(30000);
        jedisConfig.setNumTestsPerEvictionRun(-1);

        jedisPool = new JedisPool(jedisConfig, config.get("redis.server.host"),
            config.getInt("redis.server.port", 6379), Protocol.DEFAULT_TIMEOUT, config.get("redis.auth.password"));
    }

    @Override
    public Authentication getAuthenToken(String authenToken) {
        Jedis jedis = jedisPool.getResource();
        try {
            String as = jedis.get(authenToken);
            if (as != null) {
                return serializer.deserialize(as.getBytes("utf-8"), Authentication.class);
            }
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    @Override
    public void addAuthenToken(String authenToken, Authentication authen) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(authenToken.getBytes("utf-8"), 1800, serializer.serialize(authen));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Authentication removeAuthenToken(String authenToken) {
        try (Jedis jedis = jedisPool.getResource()) {
            String as = jedis.get(authenToken);
            if (as != null) {
                jedis.del(authenToken);
                return serializer.deserialize(as.getBytes("utf-8"), Authentication.class);
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public Authentication removeAuthenTicket(String authenTicket) {
        try (Jedis jedis = jedisPool.getResource()) {
            String as = jedis.get(authenTicket);
            if (as != null) {
                jedis.del(authenTicket);
                return serializer.deserialize(as.getBytes("utf-8"), Authentication.class);
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void addAuthenTicket(String authenTicket, Authentication authen) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(authenTicket.getBytes("utf-8"), 60, serializer.serialize(authen));
        } catch (Exception e) {
        }
    }

}
