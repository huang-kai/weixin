package net.home.pojo;

public class IncomingMessage {
    
    private String toUserName;
    
    private String fromUserName;
    
    private String createTime;
    
    private String msgType;
    
    private String content;
    
    private long msgId;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return "InputMessage [toUserName=" + toUserName + ", fromUserName=" + fromUserName + ", createTime="
                + createTime + ", msgType=" + msgType + ", content=" + content + ", msgId=" + msgId + "]";
    }
}
