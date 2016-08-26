
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        var zNodes = [

			{ id: 11, pId: 1, name: "我的账户", open: true },
			{ id: 111, pId: 11, name: "商户基本信息" },
			{ id: 112, pId: 11, name: "商户账户管理" },
			{ id: 113, pId: 11, name: "商户操作员管理" },

		];
		

			var zNodes1 = [

			{ id: 11, pId: 1, name: "商户交易中心", open: true },
			{ id: 111, pId: 11, name: "订单查询" },
			{ id: 112, pId: 11, name: "退款申请查询" },
			{ id: 113, pId: 11, name: "财务明细" },

		];

			var zNodes2 = [

			{ id: 11, pId: 1, name: "商户安全中心", open: true },
			{ id: 111, pId: 11, name: "修改绑定手机" },
			{ id: 112, pId: 11, name: "修改绑定邮箱" },
			{ id: 113, pId: 11, name: "登录密码修改" },
			{ id: 113, pId: 11, name: "支付密码修改" },
			{ id: 113, pId: 11, name: "支付密码找回" },
			{ id: 113, pId: 11, name: "实名认证" },
			{ id: 113, pId: 11, name: "安全保护问题" },
			{ id: 113, pId: 11, name: "数字证书" },

		];


        $(document).ready(function() {
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            $.fn.zTree.init($("#treeDemo1"), setting, zNodes1);
            $.fn.zTree.init($("#treeDemo2"), setting, zNodes2);
            setCheck();
        });
