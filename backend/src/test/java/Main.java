import org.mindrot.jbcrypt.BCrypt;

/**
 * @ClassName Main
 * @Description  TODO
 * @Author lybugproducer
 * @Date 2024/11/12 16:18
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
//        String password = "lybugproducer";
//        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
//        System.out.println(hashedPassword);

        String s = "$2a$10$fiVQwXJcoajOn5C7ljrzBuvFrsGt2amDbpZB3xekwP7JdHXpHi2Si";
        System.out.println(BCrypt.checkpw("lybugproducer", s));
    }
}
