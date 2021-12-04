package indi.mat.work.db.constants;

public enum DbUrlTemplate {

    SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver","jdbc:sqlserver://${hostName}\\${instance};DatabaseName=tempdb"),
    MYSQL("com.mysql.cj.jdbc.Driver","jdbc:mysql://${hostName}:${port}/${instance}?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true");

    private String driveClassName;

    private String urlPattern;

    DbUrlTemplate(String driveClassName, String urlPattern) {
        this.driveClassName = driveClassName;
        this.urlPattern = urlPattern;
    }

    public String getDriveClassName() {
        return driveClassName;
    }


    public String getUrlPattern() {
        return urlPattern;
    }


    public static DbUrlTemplate value(String dbType){
        DbUrlTemplate []  dbTypes =  DbUrlTemplate.values();
        for (DbUrlTemplate db:dbTypes) {
            if(dbType.toUpperCase().equals(db.name())){
                return db;
            }
        }
        return null;
    }
}
