package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallIndexCarouselVO;
import ltd.newbee.mall.dao.CarouselMapper;
import ltd.newbee.mall.entity.Carousel;
import ltd.newbee.mall.service.NewBeeMallCarouselService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Richard
 * @Date 2021/4/23 3:39 PM
 */
@Service
public class NewBeeMallCarouselServiceImpl implements NewBeeMallCarouselService {
    
    @Resource
    CarouselMapper carouselMapper;
    
    @Override
    public PageResult getCarouselPage(PageQueryUtil pageQueryUtil) {
        List<Carousel> carousels = carouselMapper.getCarouselPage(pageQueryUtil.getPage(), pageQueryUtil.getLimit());
        int            total     = carouselMapper.getTotalCarousels();
        
        return new PageResult(carousels, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
    }
    
    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        
        return carouselMapper.deleteBatch(ids) > 0;
    }
    
    @Override
    public String saveCarousel(Carousel carousel) {
        if (carouselMapper.insertSelective(carousel) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }
    
    @Override
    public String updateCarousel(Carousel carousel) {
        Carousel temp = carouselMapper.selectByPrimaryKey(carousel.getCarouselId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        
        temp.setCarouselRank(carousel.getCarouselRank());
        temp.setRedirectUrl(carousel.getRedirectUrl());
        temp.setCarouselUrl(carousel.getCarouselUrl());
        temp.setUpdateTime(new Date());
        if (carouselMapper.updateByPrimaryKeySelective(temp) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        
        return ServiceResultEnum.DB_ERROR.getResult();
    }
    
    @Override
    public Carousel getCarouselById(Integer id) {
        return carouselMapper.selectByPrimaryKey(id);
    }
    
    @Override
    public List<NewBeeMallIndexCarouselVO> getCarouselsForIndex(int number) {
        List<NewBeeMallIndexCarouselVO> newBeeMallIndexCarouselVOS = new ArrayList<>(number);
        List<Carousel>                  carousels                  = carouselMapper.findCarouselsByNum(number);
        if (!CollectionUtils.isEmpty(carousels)) {
            newBeeMallIndexCarouselVOS = BeanUtil.copyList(carousels, NewBeeMallIndexCarouselVO.class);
        }
        
        return newBeeMallIndexCarouselVOS;
    }
}
