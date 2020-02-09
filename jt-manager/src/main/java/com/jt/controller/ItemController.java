package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.service.ItemService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;

/**
 *  http://localhost:8091/item/query?page=1&rows=20
 *  //rows是每页显示的数目，page是第几页
 */
	@RequestMapping("/query")
	public EasyUITable findItemByPage(int page,int rows){
		return itemService.findItemByPage(page,rows);
	}
	@RequestMapping("/save")
	public SysResult saveItem(Item item , ItemDesc itemDesc){
		try{
			itemService.saveItem(item,itemDesc);
			return SysResult.ok();
		}catch (Exception e){
			e.printStackTrace();
			return SysResult.fail("保存出粗，方晨");
		}
	}
	/**
	 * 需求:根据itemId查询商品详情信息
	 * url:/item/query/item/desc/147...
	 * 返回值结果:SysResult 携带数据返回()
	 */
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescByItemId(@PathVariable Long itemId){
		ItemDesc itemDesc=itemService.findItemDescByItemId(itemId);
		return SysResult.ok(itemDesc);

	}


	/**
	 * 需求：实现商品更新 item itemDesc
	 * url:/item/update
	 * 参数:表单数据
	 * 返回值结果:SysResult数据
	 */
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc){
		itemService.updateItem(item,itemDesc);
		return SysResult.ok();
	}


	/**
	 * url:/item/delete
	 * 编辑itemController
	 * 参数{"ids",ids} 100,101
	 */
	@RequestMapping("/delete")
	public SysResult deleteItem(Long[] ids){

		itemService.deleteItems(ids);
		return SysResult.ok();

	}


	/**
	 * 下架操作
	 * url:item/instock
	 * 参数{"ids",ids}
	 * 返回值Sysresult对象
	 */
	@RequestMapping("/instock")
	public SysResult inStockItems(Long[] ids){
		int status =2;
		itemService.updateStatus(ids,status);
		return SysResult.ok();
	}

	/**
	 * 上架操作
	 * url:item/instock
	 * 参数{"ids",ids}
	 * 返回值Sysresult对象
	 */
	@RequestMapping("/reshelf")
	public SysResult reShelfItems(Long[] ids){
		int status =1;
		itemService.updateStatus(ids,status);
		return SysResult.ok();
	}
}
