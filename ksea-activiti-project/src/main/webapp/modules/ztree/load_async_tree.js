//声明ztree全局变量
var zTreeObj;

// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
var setting = {
    data: {
        key: {
            name: "name",
            children: "childrens"
        },
        view: {
            showLine: false//不显示树上的连接线
        },
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parent",
            rootPId: 0
        }

    },
    async: {
        enable: true,
        type: "post",
        url: "/ztree/async/node",
        autoParam: ["id"],
        dataFilter: filter
    },
    view: {//表示tree的显示状态
        selectedMulti: false//表示禁止多选selectedMulti

    },
    check: {//表示tree的节点在点击时的相关设置
        enable: false,//是否显示radio/checkbox
        autoCheckTrigger: false,
        chkStyle: "radio",
        chkboxType: {"Y": "", "N": ""},//Y 属性定义 checkbox 被勾选后的情况；N 属性定义 checkbox 取消勾选后的情况；"p" 表示操作会影响父级节点；"s" 表示操作会影响子级节点。请注意大小写，不要改变
        radioType: "all"//设置tree的分组
    },
    callback: {//表示tree的一些事件处理函数
        onClick: handlerClick,
        beforeAsync: ztreeBeforeAsync,
        onAsyncSuccess: ztreeOnAsyncSuccess
    }
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

            var rootNode = {
                "id": -1,
                "parent": 0,
                "name": "项目树根节点",
                "iconOpen": "/plugin/ztree_v3/css/zTreeStyle/img/diy/1_open.png",
                "iconClose": "/plugin/ztree_v3/css/zTreeStyle/img/diy/1_close.png",
                "open": true,
                "childrens": data
            };

            console.info(JSON.stringify(rootNode))

            //加载ztree
            zTreeObj = $.fn.zTree.init($("#" + id), setting, rootNode);

        }
    });


}

//查看选中的节点的信息
function handlerClick(event, treeId, treeNode) {
    //获取选中的值
    $.ajax({
        async: false,//是否异步
        cache: false,//是否使用缓存
        type: 'POST',//请求方式：post
        dataType: 'json',//数据传输格式：json
        contentType: 'application/json; charset=utf-8',
        url: ctx + "/ztree/info/" + treeNode.id,
        error: function () {
            //请求失败处理函数
            alert('请求失败！');
        },
        success: function (data) {
            $("#node").html("");

            if (undefined == data || null == data || data.length == 0) return false;

            $("#node").html(
                "id:" + data.id + ",name:" +
                data.name + ",parent:" + data.parent
            )
            ;

        }
    });

}

//添加节点
function addNode() {
    //获取选中的节点
    var nodes = zTreeObj.getSelectedNodes();
    if (nodes.length == 0) {
        alert("请选择一个节点目录");
        return false;
    }
    var node = nodes[0];
    var parent = node.id;
    console.info(parent)

    //页面层
    layer.open({
        type: 2,
        title: "添加学生信息",
        closeBtn: 0,
        area: ['600px', '360px'],
        shadeClose: false, //点击遮罩关闭
        content: ctx + '/ztree/add/' + parent,
        btn: ['添加', '取消'],
        yes: function (index, layero) {

            //获取子页面的body内容，并给子页面赋值
            //   var body = layer.getChildFrame('body', index);
            //  body.find('input').val('Hi，我是从父页来的')


            //获取子页面的iframe并调用iframe中的方法
            var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
            iframeWin.submitForm();


            //添加完毕之后关闭模态框
            layer.close(index);


        },
        btn2: function (index, layero) {

            //return false 开启该代码可禁止点击该按钮关闭
        },
        cancel: function () {

            //return false 开启该代码可禁止点击该按钮关闭
        }
    });
    //刷新节点
    zTreeObj.reAsyncChildNodes(node, "refresh", true);
}

function ztreeBeforeAsync() {

}


function ztreeOnAsyncSuccess(event, treeId, treeNode) {
    /**
     * event:对异步加载的说明
     * treeId:整个树的treeDemo
     * treeNode:当前点击的节点
     */

};

function getUrl() {
    return ctx + "/ztree/async/node";

}

//选中刷新的节点
function refreshChildrens() {

    /*获取 zTree选中的节点*/
    var nodes = zTreeObj.getSelectedNodes();

    if (nodes.length > 0) {
        var node = nodes[0];

        console.info(node)
        // node.open();
        /*强行异步加载父节点的子节点。[setting.async.enable = true 时有效]*/
        zTreeObj.reAsyncChildNodes(node, "refresh", false);
    }


}

/**
 * 根据节点刷新
 * @param node
 */
function refreshNode(node) {

    // node.open();
    zTreeObj.reAsyncChildNodes(node, "refresh", true);
}

function filter(treeId, parentNode, childNodes) {
    /**
     * treeId:当前树的id:treeDemo
     * parentNode:父节点，也就是当前点击的节点
     * childNodes:子节点，当前点击节点的所有儿子节点
     *
     */

    console.info(childNodes)

    if (!childNodes) return null;
    for (var i = 0, l = childNodes.length; i < l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
    }
    return childNodes;
}

