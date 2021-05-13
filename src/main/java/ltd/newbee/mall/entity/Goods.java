package ltd.newbee.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Author Richard
 * @Date 2021/5/11 4:25 PM
 */
public class Goods {
    public Long    goodsId;
    public String  goodsName;
    public String  goodsIntro;
    public Long    goodsCategoryId;
    public String  goodsCoverImg;
    public String  goodsCarousel;
    public String  goodsDetailContent;
    public Integer originalPrice;
    public Integer sellingPrice;
    public Integer stockNum;
    public String  tag;
    public String  goodsSellStatus;
    public Integer createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public String  createTime;
    public Integer updateUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public String  updateTime;
    
    public Long getGoodsId() {
        return goodsId;
    }
    
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
    
    public String getGoodsName() {
        return goodsName;
    }
    
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    
    public String getGoodsIntro() {
        return goodsIntro;
    }
    
    public void setGoodsIntro(String goodsIntro) {
        this.goodsIntro = goodsIntro;
    }
    
    public Long getGoodsCategoryId() {
        return goodsCategoryId;
    }
    
    public void setGoodsCategoryId(Long goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }
    
    public String getGoodsCoverImg() {
        return goodsCoverImg;
    }
    
    public void setGoodsCoverImg(String goodsCoverImg) {
        this.goodsCoverImg = goodsCoverImg;
    }
    
    public String getGoodsCarousel() {
        return goodsCarousel;
    }
    
    public void setGoodsCarousel(String goodsCarousel) {
        this.goodsCarousel = goodsCarousel;
    }
    
    public String getGoodsDetailContent() {
        return goodsDetailContent;
    }
    
    public void setGoodsDetailContent(String goodsDetailContent) {
        this.goodsDetailContent = goodsDetailContent;
    }
    
    public Integer getOriginalPrice() {
        return originalPrice;
    }
    
    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }
    
    public Integer getSellingPrice() {
        return sellingPrice;
    }
    
    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    
    public Integer getStockNum() {
        return stockNum;
    }
    
    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }
    
    public String getTag() {
        return tag;
    }
    
    public void setTag(String tag) {
        this.tag = tag;
    }
    
    public String getGoodsSellStatus() {
        return goodsSellStatus;
    }
    
    public void setGoodsSellStatus(String goodsSellStatus) {
        this.goodsSellStatus = goodsSellStatus;
    }
    
    public Integer getCreateUser() {
        return createUser;
    }
    
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
    
    public String getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    public Integer getUpdateUser() {
        return updateUser;
    }
    
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
    
    public String getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    
    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsIntro='" + goodsIntro + '\'' +
                ", goodsCategoryId=" + goodsCategoryId +
                ", goodsCoverImg='" + goodsCoverImg + '\'' +
                ", goodsCarousel='" + goodsCarousel + '\'' +
                ", goodsDetailContent='" + goodsDetailContent + '\'' +
                ", originalPrice=" + originalPrice +
                ", sellingPrice=" + sellingPrice +
                ", stockNum=" + stockNum +
                ", tag='" + tag + '\'' +
                ", goodsSellStatus='" + goodsSellStatus + '\'' +
                ", createUser=" + createUser +
                ", createTime='" + createTime + '\'' +
                ", updateUser=" + updateUser +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
