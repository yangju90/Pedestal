package indi.mat.work.project.service.system;

import indi.mat.work.project.model.system.SystemFavoritesInfo;

import java.util.List;

public interface ISystemFavoritesInfoService {

    List<SystemFavoritesInfo> getBusinessTypeFavorites(String businessFavoritesType, Long accountId);
}
