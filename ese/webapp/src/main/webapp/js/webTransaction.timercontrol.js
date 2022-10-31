var timerCount;
var count;
var counter;//=setInterval(timer, 1000); //1000 will  run it every 1 second
var url;
var agentId;
var expiryTime;

function getWindowHeight(){
	var height = document.documentElement.scrollHeight;
	if(height<$(document).height())
		height = $(document).height();
	return height;
}

function enableExtendAlert(){	
	
	$('body').css('overflow','hidden');
	$('#pendingExtendAlertErrMsg').html('');
	$('#popupBackground').css('width','100%');
	$('#popupBackground').css('height',getWindowHeight());
	$('#popupBackground').css('top','0');
	$('#popupBackground').css('left','0');
	$('#popupBackground').show();
	$('#extendAlert').css({top:'50%',left:'50%',margin:'-'+($('#extendAlert').height() / 2)+'px 0 0 -'+($('#extendAlert').width() / 2)+'px'});
	$('#extendAlert').show();

	window.location.hash="#extendAlert";
}

function disableExtendAlert(){
	$('#pendingExtendAlertErrMsg').html('');
	$('#popupBackground').hide();
	$('#extendAlert').hide();
	$('#restartAlert').hide();
	$('body').css('overflow','');
}

function enableRestartAlert(){	
	$('body').css('overflow','hidden');
	$('#pendingRestartAlertErrMsg').html('');
	$('#popupBackground').css('width','100%');
	$('#popupBackground').css('height',getWindowHeight());
	$('#popupBackground').css('top','0');
	$('#popupBackground').css('left','0');
	$('#popupBackground').show();
	$('#restartAlert').css({top:'50%',left:'50%',margin:'-'+($('#restartAlert').height() / 2)+'px 0 0 -'+($('#restartAlert').width() / 2)+'px'});
	$('#restartAlert').show();

	window.location.hash="#restartAlert";
}

function showPopup(){
	enableExtendAlert();
}

function populateTimerCount(reInitialize,url,agentId,timerCount){
	$.post(url+"_populateTimerCount.action",{dt:new Date(),agentId:agentId,agentTimer:timerCount,reInitialize:reInitialize},function(data){
 	 	if(data.indexOf("j_username")!= -1){
 	 	 	cancelForm();
 	 	}
 	 	if(data=="failure"){
 	 	 	clearInterval(counter);
 		    redirectForm();
 	 	}else if(data=="expired"){
 	 	 	clearInterval(counter);
 		    redirectForm();
 	 	} 	 	
 	});
}

function cancelForm(){
	document.cancelform.action = url+"_create.action";	
	jQuery("#cancelFormAgentTimer").val(timerCount);
	document.cancelform.submit();
}

function redirectForm(){
	document.redirectform.action=url+"_redirect.action";	
	jQuery("#redirectFormAgentTimer").val(timerCount);
	document.redirectform.submit();	
}

function extendTransaction(){
	if(count>0){
		count+=timerCount;
		populateTimerCount(true,url,agentId,timerCount);
		disableExtendAlert();
	}else{
		redirectForm();
	}
}

function timer()
{
	count=count-1;
	  if (count <= 0)
	  {
	    clearInterval(counter);
	    redirectForm();
	     return;
	  }

	  if(count==10){
		  showPopup();
	  }
	  
	  document.getElementById("timer").innerHTML= expiryTime + count + " secs";
	  document.getElementById("timer1").innerHTML=count + " secs";
	  document.getElementById("timer2").innerHTML=count + " secs";
}

function startTransactionSession(){
	counter=setInterval(timer, 1000);
	populateTimerCount(false,url,agentId,timerCount);
}

function startTimer(ul){
	url = ul;
	startTransactionSession();	
}

function setFields(tCount,aId,exTime){
	timerCount = tCount;
	count=timerCount - 3;
	agentId = aId;
	expiryTime = exTime;
}