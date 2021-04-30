package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.Carousel;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

/**
 * @Author Richard
 * @Date 2021/4/23 3:38 PM
 */
public interface NewBeeMallCarouselService {
    
    PageResult getCarouselPage(PageQueryUtil pageQueryUtil);
    
    Boolean deleteBatch(Integer[] ids);
    
    String saveCarousel(Carousel carousel);
    
    String updateCarousel(Carousel carousel);
    
    Carousel getCarouselById(Integer id);
}
