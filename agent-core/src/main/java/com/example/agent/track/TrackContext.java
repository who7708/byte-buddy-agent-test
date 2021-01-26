package com.example.agent.track;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/25
 */
public class TrackContext {

    private static final ThreadLocal<String> trackLocal = new ThreadLocal<String>();

    public static void clear() {
        trackLocal.remove();
    }

    public static String getLinkId() {
        return trackLocal.get();
    }

    public static void setLinkId(String linkId) {
        trackLocal.set(linkId);
    }

}
