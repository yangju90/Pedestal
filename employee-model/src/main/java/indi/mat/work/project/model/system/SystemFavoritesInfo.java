package indi.mat.work.project.model.system;

import indi.mat.work.project.model.BaseModel;

public class SystemFavoritesInfo extends BaseModel {

    private Long accountId;

    private String businessFavoritesType;

    private Long favoriteId;

    private Integer score;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getBusinessFavoritesType() {
        return businessFavoritesType;
    }

    public void setBusinessFavoritesType(String businessFavoritesType) {
        this.businessFavoritesType = businessFavoritesType;
    }

    public Long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
