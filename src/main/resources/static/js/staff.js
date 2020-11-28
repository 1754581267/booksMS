var app = new Vue({
    el : '#staff',
    data : {
        xPage : xPage,
        url: '',
        work :'',
		un :'',

        // 添加业务
        a1 : 0,
        a2 : 0,
        a3 : 0,
        a4 : 0,
        a5 : 0,
        a6 : 0,
        a7 : 0,
		mt : '',
        allWorks : ['管理员'],
		id : '',
        works: '',
        name: '',
        uname : '',
        pwd : '',
        state : '已锁定',
        sex: '',
        age: 0,
        phone: '',

        // 重置密码
        r1 : 0,
        r2 : 0,
        pwd1 : '',
        pwd2 : '',

		en: '',
		sub1: ''
    },
    methods: {
        forName : function() {
            $.ajax({
                url:"/main.ajax",
                type:"post",
                data:{
                    str:'work'
                },
                dataType:"JSON",
                success: function(code) {
                    app.un = code.name;
                    app.work = code.work;
                },
                error:function(){
                    alert("员工信息获取失败!请联系管理员");
                }
            })
        },
        emMore : function() {
            if (xPage.ids.length <= 0 ) {
                layer.alert("请选择要解锁的数据");
            } else {
                var idstr = xPage.ids.join(",");
                app.empower(idstr);
            }
        },
        empower: function(ids) {
            if (xPage.work === 1) {
                $.ajax({
                    url : "/staff.ajax",
                    type : "POST",
                    data : {
                        str: 'empower',
                        id: ids
                    },
                    dataType : "JSON",
                    success : function (code) {
                        console.log(code.code);
                        if (code.code === "updtSuc") {
                            layer.alert("解锁成功");
                            xPage.ids = [];
                            xPage.allId = '';
                            xPage.getData(xPage.pageIndex);
                        } else if(code.code == "updtErr") {
                            layer.alert("解锁失败");
                        }
                    },
                    error : function () {
                        layer.alert("解锁失败，请联系管理员");
                    }
                });
            } else {
                layer.alert("不是管理员，无法操作")
            }
        },
        reMore : function() {
            if (xPage.ids.length <= 0 ) {
                layer.alert("请选择要锁定的数据");
            } else {
                var idstr = xPage.ids.join(",");
                app.revoke(idstr);
            }
        },
        revoke: function(ids) {
            if (xPage.work == 1) {
                $.ajax({
                    url : "/staff.ajax",
                    type : "POST",
                    data : {
                        str: 'revoke',
                        id: ids
                    },
                    dataType : "JSON",
                    success : function (code) {
                        console.log(code.code);
                        if (code.code == "updtSuc") {
                            layer.alert("已锁定");
                            xPage.ids = [];
                            xPage.allId = '';
                            xPage.getData(xPage.pageIndex);
                        } else if(code.code == "myself") {
                            layer.alert("你的账户被锁定");
                            location.reload();
                        } else if(code.code == "updtErr") {
                            layer.alert("锁定失败");
                        }
                    },
                    error : function () {
                        layer.alert("锁定失败，请联系管理员");
                    }
                });
            } else {
                layer.alert("不是管理员，无法操作")
            }
        },
        inputpwd1 : function() {
            if (this.pwd1 == "") {
                $("#pwd1").html("<strong style=\"color: red\">  不能为空</strong>");
                this.r1 = 0;
            } else {
                $("#pwd1").html("<strong style=\"color: greenyellow\">  OK</strong>");
                this.r1 = 1;
            }
        },
        inputpwd2 : function() {
            if (this.pwd2 !== this.pwd1) {
                $("#pwd2").html("<strong style=\"color: red\">  和第一次输入不相同</strong>");
                this.r2 = 0;
            } else {
                $("#pwd2").html("<strong style=\"color: greenyellow\">  OK</strong>");
                this.r2 = 1;
            }
        },
        reset: function(id) {
            this.id = id;
            $("#reset").modal(
                {
                    keyboard:false,
                    backdrop:"static"
                }
            );
        },
        reSub : function() {
            if (this.pwd1 == "" || this.pwd2 == "") {
                $("#end").html("<strong style=\"color: red\">  不能为空</strong>");
            } else if ((this.r1 + this.r2) !== 2) {
                $("#end").html("<strong style=\"color: red\">  请填写正确的密码</strong>");
            } else if (this.pwd1 !== this.pwd2) {
                $("#end").html("<strong style=\"color: red\">  两次密码不相同</strong>");
            } else {
                $("#reset").modal('hide');
                $.ajax({
                    url: "/staff.ajax",
                    type: "POST",
                    data: {
                        id : this.id,
                        password: this.pwd2,
                        str: 'reset'
                    },
                    dataType: "JSON",
                    success: function (data) {
                        if (data.code == "resetSuc") {
                            layer.msg("重置成功", {time : 1000, icon : 1});
                        } else if (data.code == "resetErr") {
                            layer.msg("重置成功", {time : 1000, icon : 2});
                        }
                    },
                    error: function () {
                        layer.alert("修改失败, 请联系管理员");
                    }
                });
            }
        },
        addMod: function () {
            if (xPage.work == 1) {
				this.en = 'uname';
				this.mt = '添加';
				this.sub1 = 'add';
				this.id = '';
				this.works = '';
				this.name = '';
				this.uname = '';
				this.pwd = '';
				this.sex = '';
				this.age = '';
				this.phone = '';
				this.state = '已锁定';
                $("#addform")[0].reset();
                $("#addStaff").modal(
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
				this.en = 'upuname';
				this.mt = '修改';
				this.sub1 = 'updt';
		        this.id = li.id;
		        this.works = li.work;
		        this.name = li.name;
		        this.uname = li.userName;
		        this.pwd = li.password;
		        this.sex = li.gender;
		        this.age = li.age;
		        this.phone = li.phone;
				this.state = li.state;
		        $("#addStaff").modal(
		            {
		                keyboard:false,
		                backdrop:"static"
		            }
		        );
		    } else {
		        layer.alert("不是管理员，无法操作")
		    }
		},
        exist: function(uname) {
            $.ajax({
                url : "/staff.ajax",
                type : "POST",
                data : {
                    str : this.en,
                    id : this.id,
                    userName : uname
                },
                dataType : "JSON",
                success : function (code) {
                    if(code.code === "unameOK") {
                        $("#inputUName").html('');
                        app.a3 = 1;
                    }
                    if(code.code === "unameNot") {
                        $("#inputUName").html("<strong style=\"color: red\">用户名已存在</strong>");
                        app.a3 = 0;
                    }
                },
                error : function () {
                    layer.alert("添加失败，请联系管理员");
                }
            });
        },
        submit : function () {
            this.exist(this.uname);
            // 判断是否选择职位
            if (this.works == '') {
                $("#inputWork").html("<strong style=\"color: red\">请选择一个职位</strong>");
                this.a1 = 0;
            } else {
                $("#inputWork").html('');
                this.a1 = 1;
            }
            // 判断是否填写姓名
            if (this.name == '') {
                $("#inputName").html("<strong style=\"color: red\">请填写姓名</strong>");
                this.a2 = 0;
            } else {
                $("#inputName").html('');
                this.a2 = 1;
            }
            if (this.uname == '') {
                $("#inputUName").html("<strong style=\"color: red\">请输入用户名</strong>");
                this.a3 = 0;
            }
            if (this.pwd == '') {
                $("#inputPwd").html("<strong style=\"color: red\">请输入密码</strong>");
                this.a4 = 0;
            } else {
                $("#inputPwd").html("");
                this.a4 = 1;
            }
            // 是否选择性别
            if (this.sex == '') {
                $("#inputSex").html("<strong style=\"color: red\">请选择性别</strong>");
                this.a5 = 0;
            } else {
                $("#inputSex").html('');
                this.a5 = 1;
            }
            // 判断年龄是否正确
            if (this.age == 17) {
                $("#inputAge").html("<strong style=\"color: red\">该员工未成年</strong>");
                this.a6 = 0;
            } else {
                $("#inputAge").html('');
                this.a6 = 1;
            }
            // 判断手机号是否正确
            if (this.phone.length != 11) {
                $("#inputPhone").html("<strong style=\"color: red\">请输入正确的手机号码</strong>");
                this.a7 = 0;
            } else {
                $("#inputPhone").html('');
                this.a7 = 1;
            }
            if(this.a1 + this.a2 + this.a3 + this.a4 + this.a5 + this.a6 + this.a7 != 7) {
                layer.alert("请将信息填写正确");
            } else {
                $("#addStaff").modal('hide');
                $.ajax({
                    url : "/staff.ajax",
                    type : "POST",
                    data : {
                        str: this.sub1,
                        id : this.id,
                        work: this.works,
                        name: this.name,
                        userName: this.uname,
                        password: this.pwd,
                        gender: this.sex,
                        age: this.age,
                        phone: this.phone,
						state: this.state
                    },
                    dataType : "JSON",
                    success : function (code) {
                        if (code.code == "addSuc") {
                            layer.alert("添加成功");
                            xPage.getData(xPage.pageIndex);
                        } else if(code.code == "exist") {
							layer.alert("用户名已存在！");
                        } else if(code.code == "addErr") {
                            layer.alert("添加失败");
                        } else if(code.code == "updtSuc") {
                            layer.alert("更改成功");
                            xPage.getData(xPage.pageIndex);
                        } else if(code.code == "updtErr") {
                            layer.alert("更改失败");
                        }
                    },
                    error : function () {
                        layer.alert("添加失败，请联系管理员");
                    }
                });
            }
        },
    }
});
app.xPage.init("/staff.ajax", "管理员");
app.forName();
