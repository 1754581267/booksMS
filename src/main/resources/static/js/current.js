var xPage = {
	// 地址
	url : '',
	// 分页索引
	pageIndex : 1,
	// 数据数量
	pageSize : 5,
	// 数据最大页数
	pageMax : 1,
	// 数据数量
	dataCount : 0,
	// 分页序号
	pageList : [],
	// 搜索数据
	sd : {},
	// 分页数据
	dataList : [],
	// 获取职位
	ynWork : '',
	work : 0,
	// 多个id
	allId : '',
	ids : [],
	// 下一页操作
	nextPage: function () {
		if (xPage.pageIndex < xPage.pageMax) {
			xPage.pageIndex = xPage.pageIndex + 1;
			xPage.getData(xPage.pageIndex);
		}
	},
	// 上一页操作
	upPage: function () {
		if (xPage.pageIndex > 1) {
			xPage.pageIndex = xPage.pageIndex - 1;
			xPage.getData(xPage.pageIndex);
		}
	},
	// 序号遍历
	runPage : function(max) {
		xPage.pageMax = max;
		xPage.pageList = [];
		for (var i = 1; i <= max; i++) {
			xPage.pageList.push(i);
		}
	},
	// 获取数据属性
	getData: function (indexPage) {
		$.ajax({
			url : xPage.url,
			type : "POST",
			data : {
				pageIndex : indexPage,
				pageSize : xPage.pageSize,
				searchData : JSON.stringify(xPage.sd),
				str : 'paging'
			},
			dataType : "JSON",
			success : function (data) {
				xPage.pageIndex = data.pageIndex;
				xPage.dataList = data.dataList;
				xPage.dataCount = data.dataCount;
				xPage.runPage(data.pageCount);
				if (xPage.ynWork == "管理员") {
					if (data.code == "notadm") {
						xPage.work = 0;
					}
					if (data.code == "isadm") {
						xPage.work = 1;
					}
				}
				if (xPage.ynWork == "保管员") {
					if (data.code == "notkep") {
						xPage.work = 0;
					}
					if (data.code == "iskep") {
						xPage.work = 1;
					}
				}
			},
			error : function () {
				layer.alert("翻页失败，请联系管理员");
			}
		});
	},
	// 初始化
	init : function (url, work) {
		xPage.url = url;
		xPage.ynWork = work;
		xPage.getData(xPage.pageIndex);
	},
	// 全选
	all : function() {
		if (xPage.allId) {
			for (var i = 0; i < xPage.dataList.length; i++) {
				if (xPage.dataList[i].id != 1) {
					xPage.ids.push(xPage.dataList[i].id);
				}
			}
		} else {
			this.ids = [];
		}
	},
	delmore: function () {
		if (xPage.ids.length <= 0 ) {
			layer.alert("请选择要删除的数据");
		} else {
			var idstr = xPage.ids.join(",");
			xPage.del(idstr);
		}
	},
	del: function (ids) {
		if (xPage.work == 1) {
			layer.confirm('是否要删除？', {
				btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					url : xPage.url,
					type : "POST",
					data : {
						id : ids,
						str: 'del'
					},
					dataType : "JSON",
					success : function (code) {
						console.log(code.code);
						if (code.code == "delSuc") {
							layer.alert("删除成功");
							xPage.ids = [];
							xPage.allId = false;
							xPage.getData(xPage.pageIndex);
						} else if (code.code == "delErr") {
							layer.alert("删除失败");
						}
					},
					error : function () {
						layer.alert("删除失败，请联系管理员");
					}
				});
			}, function(){
				layer.msg('已取消删除', {icon: 1});
			});
		} else {
			layer.alert("无法操作");
		}
	}
}