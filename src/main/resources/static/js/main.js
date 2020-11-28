var main = new Vue({
	el:'#box',
	data:{
		name:'',
		work:'',
		pwd1:'',
		pwd2:'',
		pwd3:'',
		f1:0,
		f2:0,
		f3:0,
	},
	methods:{
		forName : function() {
			$.ajax({
				url:"/main.ajax",
				type:"post",
				data:{
					str:'work'
				},
				dataType:"JSON",
				success: function(code) {
					main.name = code.name;
					main.work = code.work;
				},
				error:function(){
					layer.alert("员工信息获取失败!请联系管理员");
				}
			})
		},
		outLogin : function() {
			$.ajax({
				url:"/main.ajax",
				type:"post",
				data:{
					str:'out'
				},
				dataType:"JSON",
				success: function(code) {
					if(code.code == "ok") {
						layer.msg('退出成功!', {
							icon: 1,
							time: 2000,
							},
							function() {
								location.reload();
							}
						);
					} else {
						layer.msg("退出失败!", {icon: 2});
					}
				},
				error:function(){
					alert("退出失败!请联系管理员");
				}
			})
		},
		pwd : function() {
			$("#mod").modal(
			    {
			        keyboard:false,
			        backdrop:"static"
			    }
			);
		},
		inputpwd1 : function() {
			if (this.pwd1 == "") {
			    $("#pwd1").html("<strong style=\"color: red\">  不能为空</strong>");
			    this.f1 = 0;
			} else {
			    $.ajax({
			        url : "/main.ajax",
			        type : "POST",
			        data : {
			            password : this.pwd1,
						str : 'pwd'
			        },
			        dataType : "JSON",
			        success : function (data) {
			            if (data.code == "not") {
			                $("#pwd1").html("<strong style=\"color: red\">与原密码不相同</strong>");
			                main.f1 = 0;
			            } else if(data.code == "same") {
			                $("#pwd1").html("<strong style=\"color: greenyellow\"> OK</strong>");
			                main.f1 = 1;
			            }
			        },
			        error : function () {
			            layer.alert("模态框出现异常");
			        }
			    })
			}
		},
		inputpwd2 : function() {
			if (this.pwd2 == "") {
			    $("#pwd2").html("<strong style=\"color: red\">  不能为空</strong>");
			    this.f2 = 0;
			}else if (this.pwd2 == this.pwd1) {
			    $("#pwd2").html("<strong style=\"color: red\">  不能与原密码相同</strong>");
			    this.f2 = 0;
			} else {
			    $("#pwd2").html("<strong style=\"color: greenyellow\">  OK</strong>");
			    this.f2 = 1;
			}
		},
		inputpwd3 : function() {
			if (this.pwd3 !== this.pwd2) {
			    $("#pwd3").html("<strong style=\"color: red\">  和第一次输入不相同</strong>");
			    this.f3 = 0;
			} else {
			    $("#pwd3").html("<strong style=\"color: greenyellow\">  OK</strong>");
			    this.f3 = 1;
			}
		},
		change : function() {
			if (this.pwd1 == "" || this.pwd2 == "" || this.pwd3 == "") {
			    $("#end").html("<strong style=\"color: red\">  不能为空</strong>");
			} else if ((this.f1 + this.f2 + this.f3) !== 3) {
			    $("#end").html("<strong style=\"color: red\">  请填写正确的密码</strong>");
			} else if (this.pwd2 === this.pwd1) {
			    $("#end").html("<strong style=\"color: red\">  与原密码不能相同</strong>");
			} else if (this.pwd2 !== this.pwd3) {
			    $("#end").html("<strong style=\"color: red\">  两次密码不相同</strong>");
			} else {
				$.ajax({
					url: "/main.ajax",
					type: "POST",
					data: {
						password: this.pwd2,
						str: 'change'
					},
					dataType: "JSON",
					success: function (data) {
						if (data.code == "change") {
							layer.msg("修改成功，请重新登录", {
								icon : 1,
								time : 2000
							},
							function () {
								location.reload();
							})
						} else if (data.code == "not") {
							layer.alert("修改失败");
						}
					},
					error: function () {
						layer.alert("修改失败, 请联系管理员");
					}
				});
			}
		}
	}
});
main.forName();