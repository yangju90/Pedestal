package indi.mat.work.project.aspect;

import indi.mat.work.project.annotation.Favorite;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Aspect
@Component
public class FavoriteAspect {

    private static final Logger logger = LoggerFactory.getLogger(FavoritesAspect.class);

    @Autowired
    ISystemFavoritesInfoService service;

    @Pointcut("@annotation(com.newegg.staffing.annotation.Favorites)")
    private void fillFavoriteState() {
    }

    @AfterReturning(
            pointcut = "fillFavoriteState()",
            returning = "obj")
    private void afterReturning(JoinPoint jp, Object obj) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        Favorites favorite = method.getAnnotation(Favorites.class);
        String m = StringUtils.isBlank(favorite.method()) ? "setFavorites" : favorite.method();
        Long accountId = WebUtil.currentUser().getUserInfo().getId();
        Map<Long, Long> map = service.getBusinessTypeFavorites(favorite.type(), accountId).stream().collect(Collectors.toMap(x -> x.getFavoriteId(), x -> x.getId()));
        if (map.size() != 0) {
            if (obj instanceof IPage) {
                List<Object> list = ((IPage<Object>) obj).getRecords();
                if (list.size() != 0) {
                    for (int i = 0; i < list.size(); i++) {
                        setFavoriteId(m, list.get(i), map);
                    }
                }
            } else {
                setFavoriteId(m, obj, map);
            }
        }
    }

    private void setFavoriteId(String setName, Object obj, Map<Long, Long> map) {
        try {
            Method getIdMethod = obj.getClass().getMethod("getId");
            Long id = (Long) getIdMethod.invoke(obj);
            if (map.containsKey(id)) {
                Method setFavoriteMethod = obj.getClass().getMethod(setName, boolean.class);
                setFavoriteMethod.invoke(obj, true);
                Method setFavoriteIdMethod = obj.getClass().getMethod("setFavoritesId", Long.class);
                setFavoriteIdMethod.invoke(obj, map.get(id));
            }
        } catch (NoSuchMethodException e) {
            logger.error("annotation Favorite error:" + obj.getClass().getName() + " " + e);
        } catch (IllegalAccessException e) {
            logger.error("annotation Favorite error:" + obj.getClass().getName() + " " + e);
        } catch (InvocationTargetException e) {
            logger.error("annotation Favorite error:" + obj.getClass().getName() + " " + e);
        }
    }

}
