+function(moduleName){
	angular.module(moduleName, [])
	       .controller("mainController",function($scope){
	    	   $scope.name = "main";
	       })
	       .controller("logController",function($scope){
	    	   $scope.name = "Log";
	       })
	       .controller("springDataJPAController",function($scope){
	    	   $scope.name = "Spring Data JPA";
	    	   
	    	   /**
	    		 * 保存
	    		 */
	    		$scope.saveCrudTest = function(){
	    			$.ajax({
	    				url:"CrudTestController/saveCrudTest",
	    				type:"GET",
	    				success:function(response){
	    					alert("success");
	    				},
	    				error:function(error){
	    					alert("error");
	    					console.error(error);
	    				}
	    			});
	    		};
	    		
	    		/**
	    		 * 修改
	    		 */
	    		$scope.updateCrudTest = function(){
	    			$.ajax({
	    				url:"CrudTestController/updateCrudTest",
	    				type:"GET",
	    				success:function(response){
	    					alert("success");
	    				},
	    				error:function(error){
	    					alert("error");
	    					console.error(error);
	    				}
	    			});
	    		};
	    		
	    		/**
	    		 * 删除
	    		 */
	    		$scope.deleteCrudTest = function(){
	    			$.ajax({
	    				url:"CrudTestController/deleteCrudTest",
	    				type:"GET",
	    				success:function(response){
	    					alert("success");
	    				},
	    				error:function(error){
	    					alert("error");
	    					console.error(error);
	    				}
	    			});
	    		};
	    		
	    		/**
	    		 * 详情
	    		 */
	    		$scope.crudTestDetail = function(){
	    			$.ajax({
	    				url:"CrudTestController/crudTestDetail",
	    				type:"POST",
	    				success:function(response){
	    					console.log(response);
	    					alert("success");
	    				},
	    				error:function(error){
	    					alert("error");
	    					console.error(error);
	    				}
	    			});
	    		};
	       })
	       .controller("shiroController",function($scope){
	    	   $scope.name = "shiro";
	    	   
	    	   /**
	    		 * 登录测试
	    		 */
	    	   $scope.loginTest = function(){
	    	       $.ajax({
	    				url:"ShiroTestController/loginTest",
	    				type:"GET",
	    				success:function(response){
	    					alert("success");
	    				},
	    				error:function(error){
	    					alert("error");
	    				}
	    		   });
	    	   };
	    		
	    	   /**
	    		* 退出测试
	    		*/
	    	   $scope.logoutTest = function(){
	    	       $.ajax({
	    				url:"ShiroTestController/logoutTest",
	    				type:"GET",
	    				success:function(response){
	    					alert("success");
	    				},
	    				error:function(error){
	    					alert("error");
	    				}
	    		   });
	    	   };
	    	   
	    	   /**
	    		* 获取当前登录用户测试
	    		*/
	    	   $scope.currentUserTest = function(){
	    	       $.ajax({
	    				url:"ShiroTestController/currentUserTest",
	    				type:"GET",
	    				success:function(response){
	    					alert("success");
	    				},
	    				error:function(error){
	    					alert("error");
	    				}
	    		   });
	    	   };
	       })
	       .controller("memberController",function($scope,$rootScope){
	    	   $scope.name = "member";
	    	   
	    	   /**
	    		 * 注册
	    		 */
	    	   $scope.VRegist = {
    			   mobile: "",
    			   password: ""
	    	   }
	    	   $scope.regist = function(){
	    	       $.ajax({
	    				url:"MemberController/regist",
	    				type:"POST",
	    				data: $scope.VRegist,
	    				success:function(response){
	    					alert("注册成功");
	    					
	    					// 注册环信
	    					$.ajax({
	    	    				url:"ChatController/regist",
	    	    				type:"POST",
	    	    				data: "userName=" + $scope.VRegist.mobile,
	    	    				success:function(response){
	    	    					alert("注册环信成功");
	    	    				},
	    	    				error:function(error){
	    	    					alert("注册环信失败");
	    	    				}
	    	    		   });
	    				},
	    				error:function(error){
	    					console.error(error);
	    					alert("注册失败");
	    				}
	    		   });
	    	   };
	    	   
	    	   /**
	    		 * 修改用户
	    		 */
	    	   $scope.updateMember = function(){
	    	       $.ajax({
	    				url:"MemberController/updateMember",
	    				type:"GET",
	    				success:function(response){
	    					alert("success");
	    				},
	    				error:function(error){
	    					alert("error");
	    				}
	    		   });
	    	   };
	    		
	    	   /**
	    		* 登录
	    		*/
	    	   $scope.VLogin = {
    			   mobile: "",
    			   password: ""
	    	   }
	    	   $scope.login = function(){
	    	       $.ajax({
	    				url:"MemberController/login",
	    				type:"POST",
	    				data: "mobile=" + $scope.VLogin.mobile + "&password=" + $scope.VLogin.password,
	    				success:function(){
	    					alert("success");
	    					
	    					// 登录环信
	    					$rootScope.loginEm(true);
	    				},
	    				error:function(error){
	    					alert(error);
	    					console.error(error);
	    				}
	    		   });
	    	   };
	    	   
	    	   /**
	    	    * findByMobile
	    	    */
	    	   $scope.findByMobile = function(){
	    		   $.ajax({
	    			   url:"MemberController/findByMobile",
	    			   type:"GET",
	    			   success:function(response){
	    				   alert("success");
	    			   },
	    			   error:function(error){
	    				   alert("error");
	    			   }
	    		   });
	    	   };
	    	   
	    	   /**
	    		* 退出
	    		*/
	    	   $scope.logout = function(){
	    	       $.ajax({
	    				url:"MemberController/logout",
	    				type:"GET",
	    				success:function(response){
	    					alert("success");
	    					
	    					// 退出环信
	    					$rootScope.logoutEm();
	    				},
	    				error:function(error){
	    					alert("error");
	    				}
	    		   });
	    	   };
	       })
	       .controller("chatController",function($scope){
	    	   $scope.name = "chat";

	    	    // 创建一个Socket实例
	    	    var socket;
	    		
	    		if ('WebSocket' in window) {
	    			socket = new WebSocket("ws://localhost:8080/webSocketServer");
	    			
	    			alert("WebSocket");
	    		} else if ('MozWebSocket' in window) {
	    			socket = new MozWebSocket("ws://localhost:8080/webSocketServer");
	    			
	    			alert("MozWebSocket");
	    		} else {
	    			socket = new SockJS("http://localhost:8080/webSocketServer/sockjs");
	    			
	    			alert("SockJS");
	    		}
	    		
	    		// WebSocket连接成功的回调
	    		socket.onopen = function(event) { 
	    			alert("onopen");
	    			// 发送消息
	    			socket.send('client'); 
	    			// 关闭Socket
	    			// socket.close() 
	    		};
	    		
	    		// 收到服务端消息时的回调
    			socket.onmessage = function(event) { 
    				$scope.$apply(function(){
	    				$scope.receiveMsg = event.data;
	    			});
    				
    				console.log('onmessage',event);
    			}; 

    			// WebSoceket关闭的回调(可能是服务端关闭,也可能是客户端关闭,总之就是一方断掉了不能连通了)
    			socket.onclose = function(event) { 
    				alert("close");
    				console.log('socket has closed',event); 
    			}; 
	    		
	    	   /**
	    		* 发送消息
	    		*/
	    	   $scope.msg = "";
	    	   $scope.receivers = "";
	    	   $scope.sendMessage = function(msgType){
	    		   socket.send($scope.msg); 
	    	   };
	    	   
	    	 
	    		
	    		
	    		
	       })
}("controllers");




