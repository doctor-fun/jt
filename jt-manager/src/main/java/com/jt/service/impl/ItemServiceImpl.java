package com.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.sun.media.jfxmedia.events.VideoRendererListener;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	/**
	 * 第一页，每页20条
	 * select * from tb_item limit 起始位置：每页多少条
	 * 0，20
	 * 20，40
	 * 40，20
	 * （page-1）rows,20
	 * @param page 第几页
	 * @param rows 每页写多少数据
	 * @return
	 */

	@Override
	public EasyUITable findItemByPage(int page, int rows) {
//		int total= itemMapper.selectCount(null);
//		int start=(page-1)*rows;
//		List<Item> itemList=itemMapper.findItemByPage(start,rows);

		QueryWrapper<Item> querWrapper=new QueryWrapper<>();
		querWrapper.orderByDesc("updated");
		IPage<Item> iPage=new Page<>(page,rows);
		IPage<Item> itemIPage = itemMapper.selectPage(iPage, querWrapper);
		int total = (int)itemIPage.getTotal();
		List<Item> itemList = itemIPage.getRecords();
		return new EasyUITable(total,itemList);
	}

	/**
	 * 加Transactional后
	 * 事务回滚策略
	 * 运行时异常，事务自动回滚
	 * 检查异常/编译异常，spring不负责事务的回滚
	 * 由程序员直接trycatch捕获异常
	 * 也可以@Transactional(rollbackFor = Exception.class)要求spring也负责检查异常和编译异常时发生回滚
	 * @param item
	 */
	//是否添加事务
	@Transactional
	@Override
	public void saveItem(Item item, ItemDesc itemDesc) {
		item.setStatus(1)
			.setCreated(new Date())
				.setUpdated(item.getCreated());
		//mp:当数据入库之后，会自动回显主键信息
		itemMapper.insert(item);
		System.out.println(item.getId());

		/**
		 * item是主键自增，入库后才有id，怎么办，对象刚生成的时候没有,但是上面那句执行后id就有了
		 * 整个商品信息就被补全了
		 */
		itemDesc.setItemId(item.getId()).
				setCreated(item.getCreated())
				.setUpdated(item.getCreated());


		itemDescMapper.insert(itemDesc);

//		int a =1/0;执行到这，如果不加@Transactional注解，那么抛出异常的同时，上面的语句仍然会插入数据到库


	}

	@Override
	public ItemDesc findItemDescByItemId(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}

	/**
	 * 一般更新操作使用主键作为更新操作，前端
	 * @param item
	 * @param itemDesc
	 */
	@Transactional
	@Override
	public void updateItem(Item item, ItemDesc itemDesc) {
		item.setUpdated(new Date());
		//主键充当where条件，其他属性充当set属性值
		itemMapper.updateById(item);

		itemDesc.setItemId(item.getId())
				.setUpdated(item.getCreated());

		//时间保持一致
		itemDescMapper.updateById(itemDesc);
	}
	@Transactional
	@Override
	public void deleteItems(Long[] ids) {
		List<Long> idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		itemDescMapper.deleteBatchIds(idList);
	}

	/**
	 *
	 * @param ids
	 * @param status
	 */
	@Transactional
	@Override
	public void updateStatus(Long[] ids, int status) {

		Item item=new Item();
		item.setStatus(status).setUpdated(new Date());


		UpdateWrapper<Item> updateWrapper=new UpdateWrapper<>();
		List<Long> idList=Arrays.asList(ids);
		updateWrapper.in("id",idList	);
		itemMapper.update(item,updateWrapper);
	}

	@Override
	public Item findItemById(Long itemId) {
		return itemMapper.selectById(itemId);
	}
}
