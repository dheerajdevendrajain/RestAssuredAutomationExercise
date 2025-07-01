package auth;

public class AuthTokenHolder {

    private static ThreadLocal<String> authToken = new ThreadLocal<>();


    public static void setToken(String token) {
        authToken.set(token);
    }

    public static String getToken() {
        return authToken.get();
    }

    public static void clearToken() {
        authToken.remove();
    }
}