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

    private static final Logger logger = LoggerFactory.getLogger(FavoriteAspect.class);

    @Pointcut("@annotation(indi.mat.work.project.annotation.Favorite)")
    private void fillFavoriteState() {
    }

    @AfterReturning(
            pointcut = "fillFavoriteState()",
            returning = "obj")
    private void afterReturning(JoinPoint jp, Object obj) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        Favorite favorite = method.getAnnotation(Favorite.class);
        Class c = favorite.type();
        String m = StringUtils.isBlank(favorite.method()) ? "setFavorite" : favorite.method();
        setFavorite(m, c, obj);
    }

    private void setFavorite(String setName, Class c, Object obj) {
        try {
            Method setFavoriteMethod = c.getMethod(setName, Boolean.class);
            setFavoriteMethod.invoke(obj, true);
        } catch (NoSuchMethodException e) {
            logger.error("annotation Favorite error:" + c.getName() + " " + e);
        } catch (IllegalAccessException e) {
            logger.error("annotation Favorite error:" + c.getName() + " " + e);
        } catch (InvocationTargetException e) {
            logger.error("annotation Favorite error:" + c.getName() + " " + e);
        }
    }
}
