package ltd.newbee.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author Richard
 * @Date 2021/4/30 4:11 PM
 */
public class Category {
    public Integer categoryId;
    public Byte    categoryLevel;
    public Integer parentId;
    public String  categoryName;
    public Integer categoryRank;
    public Byte    isDeleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date    createTime;
    public Integer createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date    updateTime;
    public Integer updateUser;
    
    public Integer getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    
    public Byte getCategoryLevel() {
        return categoryLevel;
    }
    
    public void setCategoryLevel(Byte categoryLevel) {
        this.categoryLevel = categoryLevel;
    }
    
    public Integer getParentId() {
        return parentId;
    }
    
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public Integer getCategoryRank() {
        return categoryRank;
    }
    
    public void setCategoryRank(Integer categoryRank) {
        this.categoryRank = categoryRank;
    }
    
    public Byte getIsDeleted() {
        return isDeleted;
    }
    
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Integer getCreateUser() {
        return createUser;
    }
    
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public Integer getUpdateUser() {
        return updateUser;
    }
    
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
    
    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryLevel=" + categoryLevel +
                ", parentId=" + parentId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryRank=" + categoryRank +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                '}';
    }
}
