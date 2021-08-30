package indi.mat.work.project.service.system;

import indi.mat.work.project.model.system.SystemFavoritesInfo;

import java.util.List;

public interface SystemFavoritesInfoService {

    List<SystemFavoritesInfo> selectAll();

    List<SystemFavoritesInfo> getBusinessTypeFavorites(String businessFavoritesType, Long accountId);
}
