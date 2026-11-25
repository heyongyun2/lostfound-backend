package org.example.springboot.entity;

public class Claim {

    private Integer claimId;

    private Integer itemId;
    private Integer userId;
    private String verifyMark;
    private String verifyLocation;
    private String verifyTime;
    private String status; // 例如 pending, approved, rejected
    private String createTime;
    private String itemName;
    private String imageUrl;
    private String studentId;
    private String email;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getClaimId() {
        return claimId;
    }

    public void setClaimId(Integer claimId) {
        this.claimId = claimId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getVerifyMark() {
        return verifyMark;
    }

    public void setVerifyMark(String verifyMark) {
        this.verifyMark = verifyMark;
    }

    public String getVerifyLocation() {
        return verifyLocation;
    }

    public void setVerifyLocation(String verifyLocation) {
        this.verifyLocation = verifyLocation;
    }

    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
