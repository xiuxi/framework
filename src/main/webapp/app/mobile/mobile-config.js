+function(ng, $, moduleName) {
    'use strict';
    
    var module = ng.module(moduleName, []);
    var host = "";//本地使用
    // var host = "https://test.hjw.com/";//测试环境
    // var host = "https://hjw.com/";//生产环境
    // 定义版本号
    module.constant("version",function(){
    	return '1.1.1';
    });
    // 定义全局host变量
    module.constant("host",host);
    // 定义API的地址服务
    module.constant('api', {
    	webRpc:host + 'web-rpc',
        // 注册登录相关的URL
        sign:host + 'sign',
        // 安全相关的地址
        security:host + 'security/checkForMobile'
    });
    // 定义移动端的页面映射
    module.constant('urls',{
    	main:{
             modal: 'app/mobile/iframe_modal/index.html',
			$super:"app/mobile/main/super.html",
			index:"app/mobile/main/index/index.html",
			demo:"app/mobile/main/demo/index.html",
			demo2:"app/mobile/main/demo2/index.html"
    	},
    });
}(angular, jQuery, "config");
