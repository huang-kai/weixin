package net.home.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class IncomingMessage {
    
    @XStreamAlias("ToUserName")
    private String toUserName;
    
    @XStreamAlias("FromUserName")
    private String fromUserName;
    
    @XStreamAlias("CreateTime")
    private String createTime;
    
    @XStreamAlias("MsgType")
    private String msgType;
    
    @XStreamAlias("Content")
    private String content;
    
    @XStreamAlias("MsgId")
    private String msgId;
    
    @XStreamAlias("Url")
    private String url;
    
    @XStreamAlias("Event")
    private String event;
    
    @XStreamAlias("EventKey")
    private String eventKey;
    
    @XStreamAlias("Ticket")
    private String ticket;
    
    private String latitude;
    
    private String longitude;
    
    private String precision;
    
    @XStreamAlias("PicUrl")
    private String picUrl;
    
    @XStreamAlias("MediaId")
    private String mediaId;
    
    @XStreamAlias("Title")
    private String title;
    
    @XStreamAlias("Description")
    private String description;
    
    @XStreamAlias("Location_X")
    private String location_X;
    
    @XStreamAlias("Location_Y")
    private String location_Y;
    
    @XStreamAlias("Label")
    private String label;
    
    @XStreamAlias("Format")
    private String format;
    
    @XStreamAlias("Recognition")
    private String recognition;
    

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation_X() {
        return location_X;
    }

    public void setLocation_X(String location_X) {
        this.location_X = location_X;
    }

    public String getLocation_Y() {
        return location_Y;
    }

    public void setLocation_Y(String location_Y) {
        this.location_Y = location_Y;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

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

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "IncomingMessage [toUserName=" + toUserName + ", fromUserName=" + fromUserName + ", createTime="
                + createTime + ", msgType=" + msgType + ", content=" + content + ", msgId=" + msgId + ", url=" + url
                + ", event=" + event + ", eventKey=" + eventKey + ", ticket=" + ticket + ", latitude=" + latitude
                + ", longitude=" + longitude + ", precision=" + precision + ", picUrl=" + picUrl + ", mediaId="
                + mediaId + ", title=" + title + ", description=" + description + ", location_X=" + location_X
                + ", location_Y=" + location_Y + ", label=" + label + ", format=" + format + ", recognition="
                + recognition + "]";
    }
    
}
