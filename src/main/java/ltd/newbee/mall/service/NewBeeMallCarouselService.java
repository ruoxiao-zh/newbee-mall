package ltd.newbee.mall.service;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.controller.vo.NewBeeMallIndexCarouselVO;
import ltd.newbee.mall.entity.Carousel;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

import java.util.List;

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
    
    List<NewBeeMallIndexCarouselVO> getCarouselsForIndex(int number);
}
