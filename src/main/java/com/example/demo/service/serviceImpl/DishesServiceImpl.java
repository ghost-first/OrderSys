package com.example.demo.service.serviceImpl;

import com.example.demo.dao.DishesMapper;
import com.example.demo.entity.Dishes;
import com.example.demo.entity.DishesExample;
import com.example.demo.service.DishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DishesServiceImpl implements DishesService {

    @Autowired
    private DishesMapper dishesMapper;

//    public DishesMapper getDishesMapper() {
//        return dishesMapper;
//    }
//
//    public void setDishesMapper(DishesMapper dishesMapper) {
//        this.dishesMapper = dishesMapper;
//    }


    public List<Dishes> findAll(){
        return dishesMapper.selectByExample(null);
    }

    public List<Dishes> findSomeDishes(String dishName,Double minPrice,Double maxPrice,Integer isrec){
        DishesExample de = new DishesExample();
        DishesExample.Criteria criteria = de.createCriteria();
        if(dishName!=null){
            criteria.andDishNameLike("%"+dishName+"%");
        }
        criteria.andPriceBetween(minPrice,maxPrice);
        criteria.andIsrecEqualTo(isrec);
        return dishesMapper.selectByExample(de);
    }

    public Dishes findByDid(int did){
        return dishesMapper.selectByPrimaryKey(did);
    }

    public List<Dishes> findByCondition(DishesExample ge){
        return dishesMapper.selectByExample(ge);
    }

    public boolean addDishes(Dishes dishes) {
        return dishesMapper.insertSelective(dishes)>0;
    }

    public boolean removeDishes(int did) {
        return dishesMapper.deleteByPrimaryKey(did)>0;
    }

    public boolean removeDishesByCondition(DishesExample ge) {
        return dishesMapper.deleteByExample(ge)>0;
    }

    public boolean editDishes(Dishes dishes) {
        return dishesMapper.updateByPrimaryKeySelective(dishes)>0;
    }

}
