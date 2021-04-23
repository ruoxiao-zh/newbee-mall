package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.Carousel;

import java.util.List;

/**
 * @Author Richard
 * @Date 2021/4/23 3:46 PM
 */
public interface CarouselMapper {
    
    List<Carousel> getCarouselPage(Integer page, Integer limit);
    
    int getTotalCarousels();
    
    Integer deleteBatch(Integer[] id);
    
    Integer updateByPrimaryKey(Carousel carousel);
    
    Carousel selectByPrimaryKey(Integer carouselId);
}
