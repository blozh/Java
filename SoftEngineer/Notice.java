package SoftEngineer;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Notice {
    private String id;
    @JSONField (format="yyyy-MM-dd hh:mm:ss") 
    private Date createdTime;

    private Integer type;

    private String notice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
    }
}