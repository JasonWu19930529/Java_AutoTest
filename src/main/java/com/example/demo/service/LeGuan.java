package com.example.demo.service;

import com.example.demo.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LeGuan {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

//    @FailRetry
//    @Transactional(rollbackFor = TryAgainException.class)
    public String updateGoodsQuantity(int version,int need) {
        try{
            Product product = sqlSessionTemplate.selectOne("com.example.demo.mapper.UserMapper.selectProduct","篮球");
            product.setNum(product.getNum() - need);
            if (product.getNum() >= 0){
                int update = sqlSessionTemplate.update("com.example.demo.mapper.UserMapper.updateProductMybatisPlus", product);
//                if (update == 0){
//                    throw new TryAgainException("因版本号不一致，未更新成功");
//                }
            }else {
                log.info("库存不足，无法正常购买");
                return "库存不足，无法正常购买";
            }
//            Product productNew = sqlSessionTemplate.selectOne("com.example.demo.mapper.UserMapper.selectProduct","篮球");
//            log.info("最新库存数量： " + productNew.getNum());
        }catch (Exception e){
            e.printStackTrace();
//            sqlSessionTemplate.rollback();
        }
        return "请求成功";
    }

}
