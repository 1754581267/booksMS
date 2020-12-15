var app = new Vue({
    el : '#book',
    data : {
        xPage : xPage,
        url: '',
        name :'',
        work :'',
		
        // 添加业务
        a1 : 0,
        a2 : 0,
        a3 : 0,
        a4 : 0,
		mt : '',
        allgenre : ['文学','玄幻','武侠','科学','哲学','古文','儿童读物'],
		id : '',
        genres: '',
        name: '',
        author : '',
        note : '',
		
		// 章节
		allChapter : [],
		chapData: {
			dataCount :0,
			pageCount :1,
			pageIndex :1,
			pageSize :10,
			pageList : []
		},
		bookId : '',
		content : [],
		
		book : '',
		allId : '',
		cid : '',
		chap : '',
		title : '',
		cont : '',
		c1 : 0,
		c2 : 0,
		c3 : 0,
		mt :'',
		en: '',
		sub1: ''
    },
    methods: {
		runPage : function(max) {
			this.chapData.pageList = [];
			for (var i = 1; i <= max; i++) {
				this.chapData.pageList.push(i);
			}
		},
		// 下一页操作
		nextPage: function () {
			if (this.chapData.pageIndex < this.chapData.pageCount) {
				this.chapData.pageIndex = this.chapData.pageIndex + 1;
				this.getCont(this.chapData.pageIndex);
			}
		},
		// 上一页操作
		upPage: function () {
			if (this.chapData.pageIndex > 1) {
				this.chapData.pageIndex = this.chapData.pageIndex - 1;
				this.getCont(this.chapData.pageIndex);
			}
		},
		chapAdd : function() {
			this.id = '';
			this.chap = this.chapData.dataCount + 1;
			this.title = '';
			this.cont = '';
		},
		getCont : function(index, li) {
			this.id = '';
			this.title = '';
			this.cont = '';
			if (li != null) {
				this.book = li.name;
				this.bookId = li.id; 
			}	
			$.ajax({
			    url : "/content.ajax",
			    type : "POST",
			    data : {
					str : 'paging',
			        id : this.bookId,
					pageSize : this.chapData.pageSize,
					pageIndex : index
			    },
			    dataType : "JSON",
			    success : function (code) {
					app.content = code.list.dataList;
					app.chapData.dataCount = code.list.dataCount;
					app.chapData.pageIndex = code.list.pageIndex;
					app.chapData.pageCount = code.list.pageCount;
					app.chapData.pageSize = code.list.pageSize;
					app.runPage(app.chapData.pageCount);
					app.chap = code.list.dataCount + 1;
					$("#addCont").modal({
						keyboard:false,
						backdrop:"static"
					});
			    },
			    error : function () {
			        layer.alert("获取章节内容失败！请联系管理员");
			    }
			});
		},
		chap_del : function(id) {
			if (xPage.work == 1) {
				layer.confirm('是否要删除？', {
					btn: ['确定','取消'] //按钮
				}, function(){
					console.log(this.bookId);
					$.ajax({
						url : '/content.ajax',
						type : "POST",
						data : {
							str: 'del',
							id : id,
							bookId : app.bookId
						},
						dataType : "JSON",
						success : function (code) {
							console.log(code.code);
							if (code.code == "delSuc") {
								layer.alert("删除成功");
								app.getCont(app.chapData.pageIndex);
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
		},
		chap_updt : function(li) {
			this.cid = li.id;
			this.bookId = li.bookId;
			this.chap = li.serial;
			this.title = li.chapter;
			this.cont = li.content;
		},
		chapAddForm : function(str) {
			if (this.chap == '') {
			    $("#inputChap").html("<strong style=\"color: red\">请选择章节</strong>");
			    this.c1 = 0;
			} else {
			    $("#inputChap").html('');
			    this.c1 = 1;
			}
			if (this.title == '') {
				$("#inputTitle").html("<strong style=\"color: red\">不能为空</strong>");
				this.c2 = 0;
			} else {
				$("#inputTitle").html('');
				this.c2 = 1;
			}
			if (this.cont == '') {
				$("#inputCont").html("<strong style=\"color: red\">不能为空</strong>");
				this.c3 = 0;
			} else {
				$("#inputCont").html('');
				this.c3 = 1;
			}
			if (this.c1 + this.c2 + this.c3 != 3) {
				layer.alert("请将信息填写正确");
			} else {
				$.ajax({
				    url : "/content.ajax",
				    type : "POST",
				    data : {
						str : str,
				        id : this.cid,
						bookId : this.bookId,
				        serial : this.chap,
						chapter : this.title,
				        content : this.cont
				    },
				    dataType : "JSON",
				    success : function (code) {
				        if(code.code == "addSuc") {
				            layer.alert("添加章节成功！");
							app.getCont(app.chapData.pageIndex);
				        }
				        if (code.code == "addErr") {
				            layer.alert("添加章节失败！")
			        	}
						if(code.code == "updtSuc") {
						    layer.alert("修改章节成功！");
							app.getCont(app.chapData.pageIndex);
						}
						if (code.code == "updtErr") {
						    layer.alert("修改章节失败！")
						}
				    },
				    error : function () {
				        layer.alert("编辑章节失败，请联系管理员");
				    }
				});
			}
		},
        addMod: function () {
            if (xPage.work == 1) {
				this.en = 'name';
				this.mt = '添加';
				this.sub1 = 'add';
				this.id = '',
				this.genres = '';
				this.name = '';
				this.author = '';
				this.note = '';
                $("#addform")[0].reset();
                $("#addBook").modal(
                    {
                        keyboard:false,
                        backdrop:"static"
                    }
                );
            } else {
                layer.alert("不是管理员，无法操作")
            }
        },
		updt : function(li) {
		    if (xPage.work == 1) {
				this.en = 'upname';
				this.mt = '修改';
				this.sub1 = 'updt';
				this.id = li.id;
		        this.genres = li.genre;
		        this.name = li.name;
		        this.author = li.author;
		        this.note = li.note;
		        $("#addBook").modal(
		            {
		                keyboard:false,
		                backdrop:"static"
		            }
		        );
		    } else {
		        layer.alert("不是管理员，无法操作")
		    }
		},
        exist: function(name) {
            $.ajax({
                url : "/book.ajax",
                type : "POST",
                data : {
                    str : this.en,
                    id : this.id,
                    name : name
                },
                dataType : "JSON",
                success : function (code) {
                    if(code.code === "nameOK") {
                        $("#inputName").html('');
                        app.a2 = 1;
                    }
                    if(code.code === "nameNot") {
                        $("#inputName").html("<strong style=\"color: red\">用户名已存在</strong>");
                        app.a2 = 0;
                    }
                },
                error : function () {
                    layer.alert("添加失败，请联系管理员");
                }
            });
        },
        submit : function () {
			this.exist(this.name);
            // 判断是否选择类别
            if (this.genres == '') {
                $("#inputGenre").html("<strong style=\"color: red\">请选择一个类别</strong>");
                this.a1 = 0;
            } else {
                $("#inputGenre").html('');
                this.a1 = 1;
            }
            // 判断是否填写书名
            if (this.name == '') {
                $("#inputName").html("<strong style=\"color: red\">请填写书名</strong>");
                this.a2 = 0;
            }
            if (this.author == '') {
                $("#inputAuthor").html("<strong style=\"color: red\">请输入作者名</strong>");
                this.a3 = 0;
            } else {
                $("#inputAuthor").html("");
                this.a3 = 1;
            }
            if (this.note == '') {
                $("#inputNote").html("<strong style=\"color: red\">请输入简介(没有填'无')</strong>");
                this.a4 = 0;
            } else {
                $("#inputNote").html("");
                this.a4 = 1;
            }
            var that = this;
            setTimeout(function () {
                if(that.a1 + that.a2 + that.a3 + that.a4 != 4) {
                    layer.alert("请将信息填写正确");
                } else {
                    $("#addBook").modal('hide');
                    var myFile = $("#bookImg")[0].files[0];
                    var formData = new FormData();
                    formData.append("id", that.id);
                    formData.append("genre", that.genres);
                    formData.append("name", that.name);
                    formData.append("author", that.author);
                    formData.append("note", that.note);
                    formData.append("myFile", myFile);
                    formData.append("str", that.sub1);
                    $.ajax({
                        url: '/book.ajax',
                        type: 'post',
                        data: formData,
                        dataType: 'JSON',

                        // 上传配置
                        contentType: false,
                        processData: false,

                        success: function (code) {
                            if(code.code == "addSuc") {
                                layer.alert("添加成功！");
                                xPage.getData(xPage.pageIndex);
                            }
                            if (code.code == "addErr") {
                                layer.alert("添加失败！")
                            }
                            if (code.code == "updtSuc") {
                                layer.alert("修改成功！");
                                xPage.getData(xPage.pageIndex);
                            }
                            if (code.code == "updtErr") {
                                layer.alert("修改失败！")
                            }
                        },
                        error: function () {
                            layer.alert("添加失败，请联系管理员！");
                        }
                    });
                }
            },200);

        },
    }
});
app.xPage.init("/book.ajax", "管理员");
