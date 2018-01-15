//声明ztree全局变量
var zTreeObj;

// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
var setting = {
    data: {//表示tree的数据格式

        key: {
            name: "name"
        },
        view: {
            showLine: false //不显示树上的连接线
        },
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parent",
            rootPId: 0
        }

    },
    view: {//表示tree的显示状态
        selectedMulti: false//表示禁止多选selectedMulti
    }/*,
    check: {//表示tree的节点在点击时的相关设置
        enable: true,//是否显示radio/checkbox
        chkStyle: "checkbox",//值为checkbox或者radio表示
        checkboxType: {p: "", s: ""},//表示父子节点的联动效果
        radioType: "level"//设置tree的分组
    },*/
    /*callback: {//表示tree的一些事件处理函数
          onClick:handlerClick,
         onCheck:handlerCheck
    }*/
}

function loadTree(url, id) {
    $.ajax({
        async: false,//是否异步
        cache: false,//是否使用缓存
        type: 'POST',//请求方式：post
        dataType: 'json',//数据传输格式：json
        contentType: 'application/json; charset=utf-8',
        url: url,
        error: function () {
            //请求失败处理函数
            alert('亲，请求失败！');
        },
        success: function (data) {

            var nodes = [];

            nodes.push({"id": -1, "parent": 0, "name": "项目树根节点","iconOpen":"/plugin/ztree_v3/css/zTreeStyle/img/diy/1_open.png","iconClose":"/plugin/ztree_v3/css/zTreeStyle/img/diy/1_close.png"});

            for (var i = 0; i < data.length; i++) {
                var node = {};
                node.id = data[i].id;
                node.name = data[i].name;
                node.parent = data[i].parent;
                node.icon="/plugin/ztree_v3/css/zTreeStyle/img/diy/2.png";

                nodes.push(node);
            }

            //加载ztree
            zTreeObj = $.fn.zTree.init($("#" + id), setting, nodes);

        }
    });


}