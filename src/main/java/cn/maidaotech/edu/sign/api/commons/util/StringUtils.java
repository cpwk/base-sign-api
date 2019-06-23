package cn.maidaotech.edu.sign.api.commons.util;

import com.sunnysuperman.commons.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends StringUtil {

    public static final String UTF8 = "UTF-8";
    public static final Charset UTF8_CHARSET = Charset.forName(UTF8);

    private static final String NUMERIC = "0123456789";

    private static final String ALPHA_NUMERIC = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final Pattern EMAIL_CHECKER = Pattern
            .compile("^([a-z0-9A-Z]+[-|\\._]?)+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    private static final Pattern USERNAME_CHECKER = Pattern.compile("^[a-zA-Z]\\w{5,17}$");

    private static final Pattern URL_CHECKER = Pattern.compile("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");

    private static final Pattern MOBILE_CHECKER = Pattern.compile("^1[3,4,5,7,8]\\d{9}$");

    public static String replaceHttp(String s) {
        if (isNull(s))
            return "";
        return s.replaceAll("http:", "https:");
    }

    public static String getFullTimeStr(String fmt) {
        return new SimpleDateFormat(fmt).format(new Date());
    }

    public static int[] getRandAmount(double amount, int count) {
        int sum = (int) (amount * 100);

        int[] splits = new int[count];
        Random random = new Random();
        int remainsCount = count - 1;
        for (int i = 0; i < count - 1; i++) {
            splits[i] = random.nextInt(sum - remainsCount) + 1;
            sum = sum - splits[i];
            remainsCount--;
        }
        splits[count - 1] = sum;

        return splits;
    }

    public static int[] getAvgAmount(double amount, int count) {
        int sum = (int) (amount * 100);
        int[] splits = new int[count];
        int avg = (sum / count);
        for (int i = 0; i < count; i++) {
            splits[i] = avg;
        }
        return splits;
    }

    /*
     * 校验过程： 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
     * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
     * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
     */
    /*
     * 校验银行卡卡号
     */
    public static boolean isBankCard(String bankCard) {
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeBankCard
     * @return
     */
    static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    public static boolean isIdCard(String no) {
        // 对身份证号进行长度等简单判断
        if (no == null || no.length() != 18 || !no.matches("\\d{17}[0-9X]")) {
            return false;
        }
        // 1-17位相乘因子数组
        int[] factor = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        // 18位随机码数组
        char[] random = "10X98765432".toCharArray();
        // 计算1-17位与相应因子乘积之和
        int total = 0;
        for (int i = 0; i < 17; i++) {
            total += Character.getNumericValue(no.charAt(i)) * factor[i];
        }
        // 判断随机码是否相等
        return random[total % 11] == no.charAt(17);
    }

    public static List<String> getLuceneStrsByStr(String str) {
        List<String> ss = new ArrayList<String>();
        if (!isNull(str))
            for (String s : str.split(" ")) {
                if (!(s.trim().equals(""))) {
                    ss.add(s);
                }
            }
        return ss;
    }

    public static String getToken() {
        return randomString(ALPHA_NUMERIC, 64);
    }


    public static String getMD5(String text, String salt) {
        return DigestUtils.md5Hex(text + salt);
    }

    public static Double roundDouble(double val, int precision) {
        Double ret = null;
        try {
            double factor = Math.pow(10, precision);
            ret = Math.floor(val * factor + 0.5) / factor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    public static String subStr(String s, int length, boolean addsub) {
        String result = "";
        if (isNull(s) || s.length() <= length) {
            result = s;
        } else {
            result = s.substring(0, length);
            if (addsub) {
                result += "...";
            }
        }
        return result;
    }

    public static boolean isNull(Object o) {
        return o == null || o.toString().trim().equals("");
    }

    public static boolean isUnknown(Object o) {
        return o == null || o.toString().trim().equals("") || "unknown".equalsIgnoreCase(o.toString());
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static String trim(String str) {
        return str != null ? str.trim() : null;
    }

    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    public static boolean isNumeric(String s) {
        if (s == null) {
            return false;
        }
        int len = s.length();
        if (len == 0) {
            return false;
        }
        char c;
        for (int i = 0; i < len; i++) {
            c = s.charAt(i);
            if (c >= 48 && c <= 57) {
                continue;
            }
            return false;
        }
        return true;
    }

    public static boolean isEqual(String s1, String s2) {
        if (isEmpty(s1)) {
            return false;
        }
        return s1.equals(s2);
    }

    public static boolean isNotEqual(String s1, String s2) {
        return !isEqual(s1, s2);
    }

    // init 0 不初始化 1 产品型,banner 2 头像型
    public static List<String> getSplitFiles(String sample, int inittype) {
        List<String> list = new ArrayList<String>();
        if (!(sample == null || sample.trim().equals(""))) {
            String[] ss = sample.split(",");
            if (!(ss == null || ss.length == 0)) {
                for (String s : ss)
                    list.add(s);
            }
        } else {
            if (inittype == 1)
                list.add("assets/images/noimg.png");
            else if (inittype == 2)
                list.add("assets/images/avatar.png");
        }
        return list;
    }

    public static String listToStr(List<String> list) {

        StringBuffer sb = new StringBuffer();

        if (!(list == null || list.size() == 0)) {
            for (String s : list) {
                sb.append(s + ",");
            }
            String str = sb.toString();
            return str.substring(0, str.length() - 1);
        }

        return "";
    }

    public static List<String> strToList(String sample) {
        List<String> list = new ArrayList<String>();
        if (!(sample == null || sample.trim().equals(""))) {
            String[] ss = sample.split(",");
            if (!(ss == null || ss.length == 0)) {
                for (String s : ss)
                    list.add(s.trim());
            }
        }
        return list;
    }

    public static String randomString(String salt, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int len = salt.length();
        for (int i = 0; i < length; i++) {
            sb.append(salt.charAt(random.nextInt(len)));
        }
        return sb.toString();
    }

    public static String randomNumericString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int len = NUMERIC.length();
        for (int i = 0; i < length; i++) {
            sb.append(NUMERIC.charAt(random.nextInt(len)));
        }
        return sb.toString();
    }

    public static boolean isEmail(String input) {
        if (input == null)
            return false;
        if (input.length() > 50)
            return false;
        Matcher matcher = EMAIL_CHECKER.matcher(input);
        return matcher.matches();
    }

    public static boolean isUrl(String input) {
        Matcher matcher = URL_CHECKER.matcher(input);
        return matcher.matches();
    }


    public static String trimMobile(String s) {
        s = s.replaceAll("-", "");
        if (s.startsWith("+86"))
            s = s.substring(2);
        return s;

    }

    public static boolean isChinaMobile(String input) {
        if (input == null)
            return false;
        if (input.length() != 11)
            return false;
        Matcher matcher = MOBILE_CHECKER.matcher(input);
        return matcher.matches();
    }

    public static String outTags(String s) {
        if (isNull(s))
            return "";
        return s.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "").replaceAll("<" +
                ".*?>", "");
    }

    public static String htmlEncode(String source) {
        if (source == null) {
            return "";
        }
        String html = "";
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            switch (c) {
                case '<':
                    buffer.append("&lt;");
                    break;
                case '>':
                    buffer.append("&gt;");
                    break;
                case '&':
                    buffer.append("&amp;");
                    break;
                case '"':
                    buffer.append("&quot;");
                    break;
                case 10:
                case 13:
                    break;
                default:
                    buffer.append(c);
            }
        }
        html = buffer.toString();
        return html;
    }

    public static String digitUppercase(double n) {
        String[] fraction = {"角", "分"};
        String[] digit = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String[][] unit = {{"元", "万", "亿"}, {"", "拾", "佰", "仟"}};

        String head = n < 0 ? "负" : "";
        n = Math.abs(n);

        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if (s.length() < 1) {
            s = "整";
        }
        int integerPart = (int) Math.floor(n);

        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p = "";
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[integerPart % 10] + unit[1][j] + p;
                integerPart = integerPart / 10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$",
                "零元整");
    }

    public static List<String> split(final String str, final String separatorChars) {
        return split(str, separatorChars, -1, false);
    }

    public static List<String> split(final String str, final String separatorChars, final int max,
                                     final boolean preserveAllTokens) {
        // Performance tuned for 2.0 (JDK1.4)
        // Direct code is quicker than StringTokenizer.
        // Also, StringTokenizer uses isSpace() not isWhitespace()

        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return new ArrayList<String>(0);
        }
        final List<String> list = new ArrayList<String>();
        int sizePlus1 = 1;
        int i = 0, start = 0;
        boolean match = false;
        boolean lastMatch = false;
        if (separatorChars == null) {
            // Null separator means use whitespace
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else if (separatorChars.length() == 1) {
            // Optimise 1 character case
            final char sep = separatorChars.charAt(0);
            while (i < len) {
                if (str.charAt(i) == sep) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else {
            // standard case
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        }
        if (match || preserveAllTokens && lastMatch) {
            list.add(str.substring(start, i));
        }
        return list;
    }


    public static String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        return org.apache.commons.lang3.StringUtils.replace(str, "'", "''");
    }

    public static boolean validateUsername(String username) {
        if (isEmpty(username)) {
            return false;
        }
        Matcher matcher = USERNAME_CHECKER.matcher(username);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.length() < 6 || password.length() > 18) {
            return false;
        }
        return true;
    }

    public static String encryptPassword(String password, String salt) {
        if (password == null) {
            return null;
        }
        String encryptPassword = DigestUtils.sha256Hex(password + salt);
        encryptPassword = DigestUtils.sha256Hex(encryptPassword);
        return encryptPassword;
    }
}
