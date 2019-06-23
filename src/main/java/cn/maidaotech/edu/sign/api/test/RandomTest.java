package cn.maidaotech.edu.sign.api.test;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @program: sign-api
 * @description:
 * @author: <a href="http://edu.maidaotech.cn/">迈道教育</a>(ilike)
 * @create: 2019-06-21 14:26
 **/
public class RandomTest {
    public static void main(String[] args) {
        Random random = new Random(5);
        int times = 10;
        while (times > 0) {
            System.out.print(random.nextInt() + ",");
            times--;
        }
        System.out.println();
        Random random1 = new Random(5);
        int times1 = 10;
        while (times1 > 0) {
            System.out.print(random1.nextInt() + ",");
            times1--;
        }
        SecureRandom secureRandom = new SecureRandom();

        System.out.println(secureRandom.nextInt());

        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
    }
}
