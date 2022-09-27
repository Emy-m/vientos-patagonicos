package ar.unrn.tp.cache;

public interface CacheService {
    void deleteCache(String key);

    String getCache(String key);

    void setCache(String key, String value);
}
