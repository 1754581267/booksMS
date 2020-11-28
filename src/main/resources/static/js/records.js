var rec = new Vue({
    el : '#record',
    data : {
        xPage : xPage,
		allday : [
			{
				day : 30,
				text : "月排行榜"

			},
			{
				day : 7,
				text : "周排行榜"
			}
		],
		allId : '',
		ids : [],
    },
    methods : {
		all : function() {
			if (this.allId) {
				for (var i = 0; i < xPage.dataList.length; i++) {
					this.ids.push(xPage.dataList[i].bookId);
				}
			} else {
				this.ids = [];
			}
		},
		delmore : function() {
			if (this.ids.length <= 0 ) {
				layer.alert("请选择要删除的数据");
			} else {
				var idstr = this.ids.join(",");
				this.del(idstr);
			}
		},
		del: function (ids) {
		        if (xPage.work == 1) {
		            layer.confirm('是否要删除该书籍的记录？', {
		                btn: ['确定','取消'] //按钮
		            }, function(){
		                $.ajax({
		                    url : '/record.ajax',
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
		                            rec.ids = [];
		                            rec.allId = false;
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
})
rec.xPage.init("/record.ajax", "管理员");
rec.xPage.sd.date='';