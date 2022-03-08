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


    private DishesMapper dishesMapper;

    @Autowired
    public void setDishesMapper(DishesMapper dishesMapper) {
        this.dishesMapper = dishesMapper;
    }

    /**
     * 展示所有菜品
     * @return
     */
    public List<Dishes> findAll(){
        return dishesMapper.selectByExample(null);
    }

    /**
     * 根据菜品名、价格区间对菜品进行模糊查询
     * @param dishName
     * @param minPrice
     * @param maxPrice
     * @return
     */
    public List<Dishes> findSomeDishes(String dishName,Double minPrice,Double maxPrice,Integer isrec){
        DishesExample de = new DishesExample();
        DishesExample.Criteria criteria = de.createCriteria();
        if(dishName!=null){
            criteria.andDishNameLike("%"+dishName+"%");
        }
        criteria.andPriceBetween(minPrice,maxPrice);
        if(isrec!=null)
            criteria.andIsrecEqualTo(isrec);
        return dishesMapper.selectByExample(de);
    }

    /**
     *根据具体id查询菜品信息
     * @param dishId
     * @return
     */
    public Dishes findByDid(int dishId){
        return dishesMapper.selectByPrimaryKey(dishId);
    }


    public List<Dishes> findByCondition(DishesExample ge){
        return dishesMapper.selectByExample(ge);
    }

    /**
     * 增加菜品
     * @param dishes
     * @return
     */
    public boolean addDishes(Dishes dishes) {
        return dishesMapper.insertSelective(dishes)>0;
    }

    /**
     * 删除菜品
     * @param dishid
     * @return
     */
    public boolean removeDishes(int dishid) {
        return dishesMapper.deleteByPrimaryKey(dishid)>0;
    }


    public boolean removeDishesByCondition(DishesExample ge) {
        return dishesMapper.deleteByExample(ge)>0;
    }

    /**
     * 修改菜品信息
     * @param dishes
     * @return
     */
    public boolean editDishes(Dishes dishes) {
        return dishesMapper.updateByPrimaryKeySelective(dishes)>0;
    }

}
