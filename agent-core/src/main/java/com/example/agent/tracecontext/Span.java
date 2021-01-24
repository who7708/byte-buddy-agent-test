package com.example.agent.tracecontext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Span {
    private String id;
    // 上级id
    private String pid;
    // 调用时间
    private String createTime;
    // 序号
    private String seq;

    public Span() {
        createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "Span{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", createTime='" + createTime + '\'' +
                ", seq='" + seq + '\'' +
                '}';
    }
}
