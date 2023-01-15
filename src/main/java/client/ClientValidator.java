package client;

public class ClientValidator {

    public static boolean validateId(long id){
        if (id<1) {
            return false;
        };
        return true;
    }

    public static boolean validateName(String name){
        if (name.length()<2 || name.length()>1000) {
            return false;
        };
        return true;
    }
}
