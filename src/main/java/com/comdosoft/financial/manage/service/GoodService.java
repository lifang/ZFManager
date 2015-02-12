package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.GoodBrand;
import com.comdosoft.financial.manage.domain.zhangfu.GoodCardType;
import com.comdosoft.financial.manage.domain.zhangfu.GoodRelation;
import com.comdosoft.financial.manage.domain.zhangfu.GoodsPayChannel;
import com.comdosoft.financial.manage.domain.zhangfu.GoodsPicture;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodBrandMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodCardTypeMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodRelationMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodsPayChannelMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodsPictureMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class GoodService {

	@Autowired
	private GoodMapper goodMapper;
	@Autowired
	private GoodBrandMapper goodBrandMapper;
	@Autowired
	private GoodCardTypeMapper goodCardTypeMapper;
	@Autowired
	private GoodsPayChannelMapper goodsPayChannelMapper;
	@Autowired
	private GoodsPictureMapper goodsPictureMapper;
	@Autowired
	private GoodRelationMapper goodRelationMapper ;
	
	public Page<Good> findPages(int page, int pageSize, Byte status, String keys){
		if (keys != null) {
			keys = "%"+keys+"%";
		}
		long count = goodMapper.countByKeys(status, keys);
		if (count == 0) {
			return new Page<Good>(new PageRequest(1, pageSize), new ArrayList<Good>(), count);
		}
		
		PageRequest request = new PageRequest(page, pageSize);
		List<Good> result = goodMapper.findPageGoodsByKeys(request, status, keys);
		Page<Good> goods = new Page<>(request, result, count);
		if (goods.getCurrentPage() > goods.getTotalPage()) {
			request = new PageRequest(goods.getTotalPage(), pageSize);
			result = goodMapper.findPageGoodsByKeys(request, status, keys);
			goods = new Page<>(request, result, count);
		}
		return goods;
	}
	
	public List<Good> findCheckedGoodsLikeKey(String keys){
		return goodMapper.selectByStatusAndName(Good.STATUS_CHECKED, "%"+keys+"%");
	}
	
	public Good findGoodInfo(Integer id) {
		return goodMapper.findGoodInfo(id);
	}
	
	public Good findRowGood(Integer id) {
		return goodMapper.findPageRowGood(id);
	}
	
	/**
	 * 初审不通过
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public Good statusFirstUnCheck(Integer id){
		Good good = goodMapper.findPageRowGood(id);
		if (good.getStatus() == Good.STATUS_WAITING_FIRST_CHECK) {
			good.setStatus(Good.STATUS_FIRST_UN_CHECKED);
			goodMapper.updateByPrimaryKey(good);
		}
		return good;
	}
	
	/**
	 * 初审通过
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public Good statusFirstCheck(Integer id){
		Good good = goodMapper.findPageRowGood(id);
		if (good.getStatus() == Good.STATUS_WAITING_FIRST_CHECK
				|| good.getStatus() == Good.STATUS_FIRST_UN_CHECKED) {
			good.setStatus(Good.STATUS_FIRST_CHECKED);
			goodMapper.updateByPrimaryKey(good);
		}
		return good;
	}
	
	/**
	 * 审核不通过
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public Good statusUnCheck(Integer id){
		Good good = goodMapper.findPageRowGood(id);
		if (good.getStatus() == Good.STATUS_WAITING_FIRST_CHECK
				|| good.getStatus() == Good.STATUS_FIRST_CHECKED) {
			good.setStatus(Good.STATUS_UN_CHECKED);
			goodMapper.updateByPrimaryKey(good);
		}
		return good;
	}
	
	/**
	 * 审核通过
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public Good statusCheck(Integer id, Boolean isThird){
		Good good = goodMapper.findPageRowGood(id);
		if (good.getStatus() == Good.STATUS_WAITING_FIRST_CHECK
				|| good.getStatus() == Good.STATUS_FIRST_UN_CHECKED
				|| good.getStatus() == Good.STATUS_FIRST_CHECKED
				|| good.getStatus() == Good.STATUS_UN_CHECKED) {
			good.setStatus(Good.STATUS_CHECKED);
			if (isThird == null || isThird == false) {
				good.setBelongsTo(good.getFactoryId());
			} else {
				good.setBelongsTo(null);
			}
			goodMapper.updateByPrimaryKey(good);
		}
		return good;
	}
	
	/**
	 * 停止
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public Good statusStop(Integer id){
		Good good = goodMapper.findPageRowGood(id);
		if (good.getStatus() == Good.STATUS_CHECKED) {
			good.setStatus(Good.STATUS_STOP);
			good.setIsPublished(false);
			goodMapper.updateByPrimaryKey(good);
		}
		return good;
	}
	
	/**
	 * 启用
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public Good statusWaitingFirstCheck(Integer id){
		Good good = goodMapper.findPageRowGood(id);
		if (good.getStatus() == Good.STATUS_STOP) {
			good.setStatus(Good.STATUS_WAITING_FIRST_CHECK);
			goodMapper.updateByPrimaryKey(good);
		}
		return good;
	}


	/**
	 * 上架
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public Good publish(Integer id) {
		Good good = goodMapper.findPageRowGood(id);
		if (good.getStatus() == Good.STATUS_CHECKED) {
			good.setPublishedAt(new Date());
			good.setIsPublished(true);
			goodMapper.updateByPrimaryKey(good);
		}
		return good;
	}
	
	/**
	 * 下架
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public Good unPublish(Integer id) {
		Good good = goodMapper.findPageRowGood(id);
		if (good.getStatus() == Good.STATUS_CHECKED) {
			good.setIsPublished(false);
			goodMapper.updateByPrimaryKey(good);
		}
		return good;
	}
	
	/**
	 * 可租赁
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public Good lease(Integer id) {
		Good good = goodMapper.findPageRowGood(id);
		if (good.getStatus() == Good.STATUS_CHECKED) {
			good.setHasLease(true);
			goodMapper.updateByPrimaryKey(good);
		}
		return good;
	}
	
	/**
	 * 不可租赁
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public Good unLease(Integer id) {
		Good good = goodMapper.findPageRowGood(id);
		if (good.getStatus() == Good.STATUS_CHECKED) {
			good.setHasLease(false);
			goodMapper.updateByPrimaryKey(good);
		}
		return good;
	}
	
	/**
	 * 可批购
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public Good purchase(Integer id) {
		Good good = goodMapper.findPageRowGood(id);
		if (good.getStatus() == Good.STATUS_CHECKED) {
			good.setHasPurchase(true);
			goodMapper.updateByPrimaryKey(good);
		}
		return good;
	}
	
	/**
	 * 不可批购
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public Good unPurchase(Integer id) {
		Good good = goodMapper.findPageRowGood(id);
		if (good.getStatus() == Good.STATUS_CHECKED) {
			good.setHasPurchase(false);
			goodMapper.updateByPrimaryKey(good);
		}
		return good;
	}

	@Transactional("transactionManager")
	public void create(String title, String secondTitle, String keyWorlds,
			Integer posCategoryId, Integer factoryId, String goodBrandName,
			String modelNumber, Integer encryptCardWayId,
			Integer signOrderWayId, Integer[] cardTypeIdList,
			String batteryInfo, String shellMaterial, Float price,
			Float retailPrice, Float purchasePrice, Float floorPrice,
			Integer floorPurchaseQuantity, Float leaseDeposit, Float leasePrice,  
			Integer leaseTime, Integer returnTime, String leaseDescription,
			String leaseAgreement, Integer[] channelIdList, String description,
			String[] photoUrlList, Integer[] goodIdList, Integer createUserId, Byte userType) {
	   Good good = new Good();
	   //基础信息
	   good.setTitle(title);
	   good.setSecondTitle(secondTitle);
	   good.setKeyWorlds(keyWorlds);
	   good.setPosCategoryId(posCategoryId);
	   good.setFactoryId(factoryId);
	   List<GoodBrand> brands = goodBrandMapper.selectByName(goodBrandName);
	   if (goodBrandName != null && goodBrandName.length() > 0) {
		   GoodBrand goodBrand = null;
		   if (brands.size() > 0) {
			   goodBrand = brands.get(0);
		   } else {
			   goodBrand = new GoodBrand();
			   goodBrand.setCreatedAt(new Date());
			   goodBrand.setName(goodBrandName);
			   goodBrandMapper.insert(goodBrand);
		   }
		   good.setGoodBrandsId(goodBrand.getId());
	   }
	   good.setModelNumber(modelNumber);
	   good.setEncryptCardWayId(encryptCardWayId);
	   good.setSignOrderWayId(signOrderWayId);
	   good.setBatteryInfo(batteryInfo);
	   good.setShellMaterial(shellMaterial);
	   
	   //价格信息
	   if (price != null) {
		   good.setPrice((int)(price*100));
	   }
	   if (retailPrice != null) {
		   good.setRetailPrice((int)(retailPrice*100));
	   }
	   
	   //批购信息
	   if (purchasePrice != null) {
		   good.setPurchasePrice((int)(purchasePrice*100));
	   }
	   if (floorPrice != null) {
		   good.setFloorPrice((int)(floorPrice*100));
	   }
	   good.setFloorPurchaseQuantity(floorPurchaseQuantity);
	   
	   //租赁设置
	   if (leaseDeposit != null) {
		   good.setLeaseDeposit((int)(leaseDeposit*100));
	   }
	   if (leasePrice != null) {
		   good.setLeasePrice((int)(leasePrice*100));
	   }
	   good.setLeaseTime(leaseTime);
	   good.setReturnTime(returnTime);
	   good.setLeaseDescription(leaseDescription);
	   good.setLeaseAgreement(leaseAgreement);
	   
	   //其他
	   good.setDescription(description);
	   good.setStatus(Good.STATUS_WAITING_FIRST_CHECK);
	   good.setIsPublished(false);
	   good.setHasPurchase(false);
	   good.setHasLease(false);
	   good.setTotalScore(0);
	   good.setTotalComment(0);
	   good.setQuantity(0);
	   good.setCreatedUserId(createUserId);
	   good.setCreatedUserType(userType);
	   good.setVolumeNumber(0);
	   good.setPurchaseNumber(0);
	   good.setCreatedAt(new Date());
	   good.setUpdatedAt(new Date());
	   goodMapper.insert(good);
	   
	   //设置卡类型 cardTypeIdList
	   if (cardTypeIdList != null) {
		   for (Integer cardTypeId : cardTypeIdList) {
			GoodCardType cardType = new GoodCardType();
			cardType.setCardTypeId(cardTypeId);
			cardType.setGoodId(good.getId());
			goodCardTypeMapper.insert(cardType);
		}
	   }
	   //设置支付通道 channelIdList   
	   if (channelIdList != null) {
		   for (Integer channelId : channelIdList) {
			GoodsPayChannel goodsPayChannel = new GoodsPayChannel();
			goodsPayChannel.setGoodId(good.getId());
			goodsPayChannel.setPayChannelId(channelId);
			goodsPayChannel.setCreateAt(new Date());
			goodsPayChannelMapper.insert(goodsPayChannel);
		}
	   }
	   //设置图片 photoUrlList
	   if (photoUrlList != null) {
		   for (String photoUrl : photoUrlList) {
			GoodsPicture goodsPicture = new GoodsPicture();
			goodsPicture.setGoodId(good.getId());
			goodsPicture.setUrlPath(photoUrl);
			goodsPicture.setCreatedAt(new Date());
			goodsPictureMapper.insert(goodsPicture);
		}
	   }
	   //设置关联商品 goodIdList
	   if (goodIdList != null) {
		   for (Integer relativeGoodId : goodIdList) {
			GoodRelation goodRelation = new GoodRelation();
			goodRelation.setRelativeGoodId(relativeGoodId);
			goodRelation.setGoodId(good.getId());
			goodRelationMapper.insert(goodRelation);
		}
	   }
	}
	
	
	@Transactional("transactionManager")
	public void update(Integer id, String title, String secondTitle, String keyWorlds,
			Integer posCategoryId, Integer factoryId, String goodBrandName,
			String modelNumber, Integer encryptCardWayId,
			Integer signOrderWayId, Integer[] cardTypeIdList,
			String batteryInfo, String shellMaterial, Float price,
			Float retailPrice, Float purchasePrice, Float floorPrice,
			Integer floorPurchaseQuantity, Float leaseDeposit, Float leasePrice,
			Integer leaseTime, Integer returnTime, String leaseDescription,
			String leaseAgreement, Integer[] channelIdList, String description,
			String[] photoUrlList, Integer[] goodIdList) {
	   Good good = goodMapper.selectByPrimaryKey(id);
	   //基础信息
	   good.setTitle(title);
	   good.setSecondTitle(secondTitle);
	   good.setKeyWorlds(keyWorlds);
	   good.setPosCategoryId(posCategoryId);
	   good.setFactoryId(factoryId);
	   List<GoodBrand> brands = goodBrandMapper.selectByName(goodBrandName);
	   if (goodBrandName != null && goodBrandName.length() > 0) {
		   GoodBrand goodBrand = null;
		   if (brands.size() > 0) {
			   goodBrand = brands.get(0);
		   } else {
			   goodBrand = new GoodBrand();
			   goodBrand.setCreatedAt(new Date());
			   goodBrand.setName(goodBrandName);
			   goodBrandMapper.insert(goodBrand);
		   }
		   good.setGoodBrandsId(goodBrand.getId());
	   } else {
		   good.setGoodBrandsId(null);
	   }
	   good.setModelNumber(modelNumber);
	   good.setEncryptCardWayId(encryptCardWayId);
	   good.setSignOrderWayId(signOrderWayId);
	   good.setBatteryInfo(batteryInfo);
	   good.setShellMaterial(shellMaterial);
	   
	   //价格信息
	   if (price != null) {
		   good.setPrice((int)(price*100));
	   } else {
		   good.setPrice(null);
	   }
	   if (retailPrice != null) {
		   good.setRetailPrice((int)(retailPrice*100));
	   } else {
		   good.setRetailPrice(null);
	   }
	   
	   //批购信息
	   if (purchasePrice != null) {
		   good.setPurchasePrice((int)(purchasePrice*100));
	   } else {
		   good.setPurchasePrice(null);
	   }
	   if (floorPrice != null) {
		   good.setFloorPrice((int)(floorPrice*100));
	   } else {
		   good.setFloorPrice(null);
	   }
	   good.setFloorPurchaseQuantity(floorPurchaseQuantity);
	   
	   //租赁设置
	   if (leaseDeposit != null) {
		   good.setLeaseDeposit((int)(leaseDeposit*100));
	   } else {
		   good.setLeaseDeposit(null);
	   }
	   if (leasePrice != null) {
		   good.setLeasePrice((int)(leasePrice*100));
	   } else {
		   good.setLeasePrice(null);
	   }
	   good.setLeaseTime(leaseTime);
	   good.setReturnTime(returnTime);
	   good.setLeaseDescription(leaseDescription);
	   good.setLeaseAgreement(leaseAgreement);
	   
	   //其他
	   good.setDescription(description);
	   
	   good.setUpdatedAt(new Date());
	   goodMapper.updateByPrimaryKey(good);
	   
	   //设置卡类型 cardTypeIdList
	   goodCardTypeMapper.deleteByGoodId(good.getId());
	   if (cardTypeIdList != null) {
		   for (Integer cardTypeId : cardTypeIdList) {
			GoodCardType cardType = new GoodCardType();
			cardType.setCardTypeId(cardTypeId);
			cardType.setGoodId(good.getId());
			goodCardTypeMapper.insert(cardType);
		}
	   }
	   //设置支付通道 channelIdList  
	   goodsPayChannelMapper.deleteByGoodId(good.getId());
	   if (channelIdList != null) {
		   for (Integer channelId : channelIdList) {
			GoodsPayChannel goodsPayChannel = new GoodsPayChannel();
			goodsPayChannel.setGoodId(good.getId());
			goodsPayChannel.setPayChannelId(channelId);
			goodsPayChannel.setCreateAt(new Date());
			goodsPayChannelMapper.insert(goodsPayChannel);
		}
	   }
	   //设置图片 photoUrlList
	   goodsPictureMapper.deleteByGoodId(good.getId());
	   if (photoUrlList != null) {
		   for (String photoUrl : photoUrlList) {
			GoodsPicture goodsPicture = new GoodsPicture();
			goodsPicture.setGoodId(good.getId());
			goodsPicture.setUrlPath(photoUrl);
			goodsPicture.setCreatedAt(new Date());
			goodsPictureMapper.insert(goodsPicture);
		}
	   }
	   //设置关联商品 goodIdList
	   goodRelationMapper.deleteByGoodId(good.getId());
	   if (goodIdList != null) {
		   for (Integer relativeGoodId : goodIdList) {
			GoodRelation goodRelation = new GoodRelation();
			goodRelation.setRelativeGoodId(relativeGoodId);
			goodRelation.setGoodId(good.getId());
			goodRelationMapper.insert(goodRelation);
		}
	   }
	}
	
}
