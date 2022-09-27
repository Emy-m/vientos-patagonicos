package ar.unrn.tp.cache;

import redis.clients.jedis.Jedis;

public class CacheServiceRedis implements CacheService {
    private String url;

    public CacheServiceRedis(String url) {
        this.url = url;
    }

    @Override
    public void deleteCache(String key) {
        Jedis jedis = new Jedis(this.url);
        jedis.del(key);
        jedis.close();
    }

    @Override
    public String getCache(String key) {
        Jedis jedis = new Jedis(this.url);
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    @Override
    public void setCache(String key, String value) {
        Jedis jedis = new Jedis(this.url);
        jedis.set(key, value);
        jedis.close();
    }
}
