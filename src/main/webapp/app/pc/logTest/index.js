$function(){
	/**
	 * 日志打印测试
	 */
	var logPrintTest = function(){
		$.ajax({
			url:"/LogTestController/logPrintTest.do",
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
	 * 日志桥接测试
	 */
	var logBridgTest = function(){
		$.ajax({
			url:"/LogTestController/logBridgTest.do",
			type:"GET",
			success:function(response){
				alert("success");
			},
			error:function(error){
				alert("error");
			}
		});
	};
}

 