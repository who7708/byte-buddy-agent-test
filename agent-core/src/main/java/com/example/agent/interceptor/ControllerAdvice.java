package com.example.agent.interceptor;

import com.example.agent.track.TrackContext;
import com.example.agent.track.TrackManager;
import net.bytebuddy.asm.Advice;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/26
 */
public class ControllerAdvice {

    @Advice.OnMethodEnter
    public static void enter(@Advice.This Object obj,
                             @Advice.Origin("#t") String className,
                             @Advice.Origin("#m") String methodName,
                             @Advice.AllArguments Object[] allArguments) {
        String linkId = TrackManager.getCurrentSpan();
        if (null == linkId) {
            linkId = UUID.randomUUID().toString();
            TrackContext.setLinkId(linkId);
        }
        String entrySpan = TrackManager.createEntrySpan();
        System.out.println("链路追踪：" + entrySpan + " " + className + "." + methodName);
    }

    @Advice.OnMethodExit()
    public static void exit(@Advice.This Object obj,
                            @Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName,
                            @Advice.AllArguments Object[] allArguments) {
        for (Object allArgument : allArguments) {
            if (allArgument instanceof HttpServletResponse) {
                HttpServletResponse response = (HttpServletResponse) allArgument;
                response.setHeader("t_id", TrackManager.getCurrentSpan());
                response.setHeader("t_c_n", className);
                response.setHeader("t_m_n", methodName);
            }
        }
        TrackManager.getExitSpan();
    }

}
