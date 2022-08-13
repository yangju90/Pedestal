package indi.mat.work.project.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("javax.cache")
public class CacheAttribute {

    private String cacheName;
    /**
     * ehcache heap大小
     * jvm内存中缓存的key数量
     */
    private Long maxHeap;
    /**
     * 缓存空闲过期时间, 单位: 秒
     */
    private Long tti;


    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public Long getMaxHeap() {
        return maxHeap;
    }

    public void setMaxHeap(Long maxHeap) {
        this.maxHeap = maxHeap;
    }

    public Long getTti() {
        return tti;
    }

    public void setTti(Long tti) {
        this.tti = tti;
    }

    public List<String> getNameList(){
        List<String> ans =new ArrayList<>();
        if(StringUtils.isBlank(this.cacheName)){
            return ans;
        }
        String[] strs = this.cacheName.split(",");
        for (String s: strs) {
            if(StringUtils.isNoneBlank(s.trim())) {
                ans.add(s.trim());
            }
        }

        return ans;
    }

}
