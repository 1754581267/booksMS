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
		
		allChapter : [],
		book : '',
		cid : '',
		content : [],
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
		getCont : function(li) {
			this.chap = '';
			this.title = '';
			this.cont = '';
			this.book = li.name;
			$.ajax({
			    url : "/content.ajax",
			    type : "POST",
			    data : {
			        bookId : li.id,
			    },
			    dataType : "JSON",
			    success : function (code) {
					app.content = code.list.dataList;
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
		chapter : function(li) {
			if (this.chap != null) {
				this.title = this.chap.chapter;
				this.cont = this.chap.content;
				this.cid = li.id;
			} else {
				this.title = '';
				this.cont = '';
				this.cid = '';
			}
		},
		submit1 : function() {
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
				$("#addCont").modal('hide');
				var str = this.title + '&' + this.cont;
				$.ajax({
				    url : "/content.add.ajax",
				    type : "POST",
				    data : {
				        chap : this.chap,
				        id : this.cid,
				        content : str
				    },
				    dataType : "JSON",
				    success : function (code) {
				        if(code.code == "addSuc") {
				            layer.alert("章节编辑成功！");
				            xPage.getData(xPage.pageIndex);
				        }
				        if (code.code == "addErr") {
				            layer.alert("章节编辑失败！")
			        	        }
				    },
				    error : function () {
				        layer.alert("章节编辑失败，请联系管理员");
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
