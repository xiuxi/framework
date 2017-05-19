+function(moduleName){
	angular.module(moduleName, ['ui.router','controllers'])
	       .run(function($rootScope) {
	    	/*********************************************************
	   		 * 环信begin
	   		 * *******************************************************/
	   		/**
	   		 * 登录环信
	   		 *
	   		 * force 是否强制登录(true：是)
	   		 */
	   		$rootScope.loginEm = function(force){
	   			$.ajax({
	    			 url:"ChatController/getWebIMToken.do",
	    			 type:"GET",
	    			 success:function(data){
	    				 var isOnLine = data.isOnLine;
	    				 
	 	   				 if(!isOnLine || force){ // 环信未登录 || 强制登录
	 	   					 $rootScope.logoutEm();

	 	   					 $rootScope.conn.open({
	 	   			             user : data.userName,
	 	   			             accessToken : data.token,
	 	   			             appKey : Easemob.im.config.appkey,
	 	   			             //apiUrl : Easemob.im.config.apiURL,
	 	   			         });
	 	   				 }
	    			 },
	    			 error:function(data){
	    		         if(data.errorType == "org.apache.shiro.authz.UnauthenticatedException"){
	 	   					 console.warn("用户尚未登录, 无法获取环信令牌");
	 	   				 }else{
	 	   					 console.error("环信登录异常: " + data.error);
	 	   				 }
	    			 }
	    		});
	   		};
	   		$rootScope.loginEm(true);
	   		
	   		/**
	   		 * 退出环信
	   		 */
	   		$rootScope.logoutEm = function(){
	   			$rootScope.conn.close();
	   		};

	   		// 处理连接时函数
	   		var handleOpen = function() {
	   	    	$rootScope.conn.setPresence();  //设置用户上线状态，必须调用

	   	        //启动心跳
	   	        if ($rootScope.conn.isOpened()) {
	   	        	$rootScope.conn.heartBeat($rootScope.conn);
	   	        }

	   	        console.info("环信已登录");
	   	    };

	   	    /**
	   	     * 初始化
	   	     */
	   	    $rootScope.conn = null;
	   	    var init = function(){
	   	    	// 创建一个新的连接
	   	    	$rootScope.conn = new Easemob.im.Connection();

	   	    	//初始化连接
	   			$rootScope.conn.init({
	   		        https : Easemob.im.config.https,
	   		        url: Easemob.im.config.xmppURL,
	   		        // 当连接成功时的回调方法
	   		        onOpened : function() {
	   		            handleOpen();
	   		        },
	   		        // 当连接关闭时的回调方法
	   		        onClosed : function() {
	   		        	console.info("环信已退出");
	   		        },
	   		        // 收到文本消息时的回调方法
	   		        onTextMessage : function(message) {
	   		        	console.info("新的聊天记录：");
	   		        	console.info(message);

	   		        	$rootScope.$broadcast('handleTextMessage',message);
	   		        },
	   		        // 异常时的回调方法
	   		        onError : function(message) {
	   		        	console.info("环信异常时的回调：" + message);
	   		        }
	   		    });
	   	    };
	   	    
	   	    // 初始化
	   		init();
	   		/*********************************************************
	   		 * 环信end
	   		 * *******************************************************/
	       })
		   .config(function($stateProvider,$urlRouterProvider){
			   // 设置默认页面
			   $urlRouterProvider.otherwise('/main');
			   
			   $stateProvider
			   // main 
			   .state('main',{    
				   url:"/main",
				   templateUrl:'app/pc/main/index.html',
				   controller:'mainController'
			   })
			   // logTest  
			   .state('main.logTest',{
				   url:"/logTest",
				   templateUrl:'app/pc/logTest/index.html',
				   controller:'logController'
			   })
			   // springDataJPATest  
			   .state('main.springDataJPATest',{
				   url:"/springDataJPATest",
				   templateUrl:'app/pc/springDataJPATest/index.html',
				   controller:'springDataJPAController'
			   })
			   // shiroTest
			   .state('main.shiroTest',{
				   url:"/shiroTest",
				   templateUrl:'app/pc/shiroTest/index.html',
				   controller:'shiroController'
			   })
			   // member
			   .state('main.member',{
				   url:"/member",
				   templateUrl:'app/pc/member/index.html',
				   controller:'memberController'
			   })
			   // chat
			   .state('main.chat',{
				   url:"/chat",
				   templateUrl:'app/pc/chat/index.html',
				   controller:'chatController'
			   });
		   });	
}("pcApp");




