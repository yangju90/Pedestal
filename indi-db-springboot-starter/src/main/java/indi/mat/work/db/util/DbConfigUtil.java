package indi.mat.work.db.util;

import indi.mat.work.db.constants.DbUrlTemplate;
import indi.mat.work.db.core.DataSourceConfig;

public class DbConfigUtil {

    public static DataSourceConfig fillUrl(DataSourceConfig config){
        String urlPattern = DbUrlTemplate.valueOf(config.getDbType().toUpperCase()).getUrlPattern();
        String url = urlPattern.replace("${hostName}",config.getHostName()).
                replace("${instance}",config.getInstance())
                .replace("${port}",config.getHostPort());
        config.setUrl(url);
        return config;
    }
}
