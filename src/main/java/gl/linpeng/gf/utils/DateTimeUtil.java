package gl.linpeng.gf.utils;

import java.util.Date;

/**
 * DateTime util
 *
 * @author lin.peng
 * @since 1.0
 **/
public class DateTimeUtil {

    /**
     * Get time stamp of right now
     *
     * @return time stamp of right now
     */
    public static Long nowTimeStamp() {
        return new Date().getTime();
    }
}
