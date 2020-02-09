package com.jt.vo;

import com.jt.pojo.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 *
 * <table class="easyui-datagrid" style="width:500px;height:300px" data-options="url:'datagrid_data.json',method:'get',fitColumns:true,singleSelect:false,pagination:true">
 * 				<thead>
 * 					<tr>
 * 						<th data-options="field:'code',width:100">Code</th>
 * 						<th data-options="field:'name',width:100">Name</th>
 * 						<th data-options="field:'price',width:100,align:'right'">Price</th>
 * 					</tr>
 * 				</thead>
 * 			</table>
 * 		easyUI会自动加上分页的参数
 *因为easyui有专门的显示格式，因此需要对其进行专门的vo对象对接
 * //根据json将数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITable {
    //total表示分页的总记录数，rows表示分页后的数据
    private Integer total;
    //row当前页的记录数
    private List<Item> rows;

}
