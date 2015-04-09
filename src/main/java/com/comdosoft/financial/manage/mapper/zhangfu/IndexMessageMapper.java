package com.comdosoft.financial.manage.mapper.zhangfu;


public interface IndexMessageMapper {

    int CommentModeration(Byte status);
    
    int GoodModeration(Byte status);

    int openingAppliesModeration(Byte status);

    int returnModeration(Byte status);
    
    int changeModeration(Byte status);
    
    int repairsModeration(Byte status);
    
    int cancelsModeration(Byte status);
    
    int updateInfosModeration(Byte status);
    
    int storagesModeration(Integer status);
    
    int srefundsModeration(Integer status);
    
    int agentModeration(Integer status);
}