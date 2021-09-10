package indi.mat.work.project.util;

public class WebUtil {
      /**
     * 当前登录的用户
     */
    public static LoginUser currentUser() {
        try {
            return (LoginUser) RequestContextHolder.currentRequestAttributes()
                    .getAttribute(LOGIN_USER_IN_REQUEST, RequestAttributes.SCOPE_REQUEST);
        } catch (IllegalStateException e) {
            return null;
        }
    }

    /**
     * 当前登录用户的Email
     * @param defaultEmail 默认Email
     * @return 如果能够获取到登录用户, 则返回其Email, 否则返回defaultEmail
     */
    public static String currentUserEmail(String defaultEmail) {
        LoginUser user = currentUser();
        if(user == null) {
            return defaultEmail;
        }
        return user.getEmail();
    }

    /**
     * 当前登录用户的Email
     * @return 如果能够获取到登录用户, 则返回其Email, 否则返回null
     */
    public static String currentUserEmail() {
        return currentUserEmail(null);
    }

    /**
     * 当前登录用户的Name
     * @param defaultName 默认Name
     * @return 如果能够获取到登录用户, 则返回其Name, 否则返回defaultName
     */
    public static String currentUserName(String defaultName) {
        LoginUser user = currentUser();
        if(user == null) {
            return defaultName;
        }
        return user.getName();
    }

    /**
     * 当前登录用户的Name
     * @return 如果能够获取到登录用户, 则返回其Name, 否则返回null
     */
    public static String currentUserName() {
        return currentUserName(null);
    }
}
