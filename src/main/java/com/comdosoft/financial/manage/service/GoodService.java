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
		Page<Good> goods = new Page<Good>(request, result, count);
		if (goods.getCurrentPage() > goods.getTotalPage()) {
			request = new PageRequest(goods.getTotalPage(), pageSize);
			result = goodMapper.findPageGoodsByKeys(request, status, keys);
			goods = new Page<Good>(request, result, count);
		}
		return goods;
	}
	
	public List<Good> findCheckedGoodsLikeKey(String keys){
		return goodMapper.selectByStatusAndName(Good.STATUS_CHECKED, "%"+keys+"%");
	}
	
	public Good findGoodInfo(Long id) {
		return goodMapper.findGoodInfo(id);
	}
	
	public Good findRowGood(Long id) {
		return goodMapper.findPageRowGood(id);
	}
	
	/**
	 * 初审不通过
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public Good statusFirstUnCheck(Long id){
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
	public Good statusFirstCheck(Long id){
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
	public Good statusUnCheck(Long id){
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
	public Good statusCheck(Long id, Boolean isThird){
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
	public Good statusStop(Long id){
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
	public Good statusWaitingFirstCheck(Long id){
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
	public Good publish(Long id) {
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
	public Good unPublish(Long id) {
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
	public Good lease(Long id) {
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
	public Good unLease(Long id) {
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
	public Good purchase(Long id) {
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
	public Good unPurchase(Long id) {
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
			Integer floorPurchaseQuantity, Float leaseDeposit,
			Integer leaseTime, Integer returnTime, String leaseDescription,
			String leaseAgreement, Integer[] channelIdList, String description,
			String[] photoUrlList, Integer[] goodIdList) {
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
				   Integer goodBrandId = goodBrandMapper.insertReturnId(goodBrand);
				   goodBrand.setId(goodBrandId);
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
		   good.setLeaseTime(leaseTime);
		   good.setReturnTime(returnTime);
		   good.setLeaseDescription(leaseDescription);
		   good.setLeaseAgreement(leaseAgreement);
		   
		   //其他
		   good.setDescription(description);
		   
		   good.setHasPurchase(false);
		   good.setHasLease(false);
		   good.setTotalScore(0);
		   good.setTotalComment(0);
		   good.setQuantity(0);
		   good.setCreatedUserId(1);//TODO
		   good.setCreatedUserType((byte)0);//TODO
		   good.setVolumeNumber(0);
		   good.setPurchaseNumber(0);
		   good.setCreatedAt(new Date());
		   good.setUpdatedAt(new Date());
		   Integer goodId = goodMapper.insert(good);
		   good.setId(goodId);
		   
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
				goodsPayChannel.setGoodId(good.getId());;
				goodsPayChannel.setPayChannelId(channelId);
				goodsPayChannel.setStatus((byte)0);
				goodsPayChannel.setCreateAt(new Date());
				goodsPayChannel.setUdpateAt(new Date());
				goodsPayChannelMapper.insert(goodsPayChannel);
			}
		   }
		   //设置图片 photoUrlList
		   if (photoUrlList != null) {
			   for (String photoUrl : photoUrlList) {
				GoodsPicture goodsPicture = new GoodsPicture();
				goodsPicture.setGoodId(good.getId());;
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
		   
		   
//			status : Byte
//			isPublished : Boolean
//			createdUserId : Integer
//			createdUserType : Byte
//			createdAt : Date
//			updatedAt : Date
//			volumeNumber : Integer  ??
//			purchaseNumber : Integer ??
//			quantity : Integer

//			
//			title : String
//			secondTitle : String
//			keyWorlds : String
//			factoryId : Integer
//			posCategoryId : Integer
//			modelNumber : String
//			encryptCardWayId : Integer
//			signOrderWayId : Integer
//			batteryInfo : String
//			shellMaterial : String
//			retailPrice : Integer
//			purchasePrice : Integer
//			floorPrice : Integer
//			floorPurchaseQuantity : Integer
//			price : Integer
//			leasePrice : Integer
//			leaseTime : Integer
//			goodBrandsId : Integer
//			leaseDeposit : Integer
//			returnTime : Integer
//			description : String
//			leaseDescription : String
//			leaseAgreement : String
	}
	
	
}
