+function(moduleName){
	angular.module(moduleName, [ 'ionic','config','ionic-ui','ngCordova','auto-router','ionic-validate','security','rpc','ionic-citydata','ionic-citypicker','ionic-datepicker','filter'])
		   .run(function($ionicPlatform,$rootScope,$rpc,host,$window,$location) {
			   $rootScope.host = host;
					
				var promotionProductId = localStorage.getItem('promotionProductId');
				
				if(promotionProductId){
					$location.path('/detail/index/'+promotionProductId);
					localStorage.removeItem('promotionProductId');
				}
			})
			.config(function($ionicConfigProvider, $urlRouterProvider,$autoRouterProvider,$rpcProvider,api,urls,version) {
				// 设置视图缓存数量
				$ionicConfigProvider.views.maxCache(0);
				// 配置导航栏的位置，统一设置在尾部
				$ionicConfigProvider.tabs.position("bottom");
				// 设置导航栏标题位置
				$ionicConfigProvider.navBar.alignTitle("center");
				// 禁止JS滚动
				$ionicConfigProvider.scrolling.jsScrolling(false);
				// 设置rpc
				$rpcProvider.url(api.webRpc);
				// 设置版本号，用以控制缓存
				$autoRouterProvider.version(version);
				// 设置路由控制器配置
				$autoRouterProvider.resolve({
					check : [ "security",function(security) {
						var checkUrl = api.security;
						var loginUrl = "/member/signin";
						return security.check(checkUrl, loginUrl,{
							"/member/index":true
							, "/member/LogistiDcsemand":true
							, "/member/query":true
						});
					} ]
				});
				$autoRouterProvider.config(urls);
				// 设置默认页面
				$urlRouterProvider.otherwise('/main/index');
			});
}("mobileApp");


