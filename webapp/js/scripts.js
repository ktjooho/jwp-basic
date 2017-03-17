String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(".answerWrite input[type=submit]").click(addAnswer);
$(".link-delete-article").click(deleteAnswer);


function simple(e) {
	alert('test' + e.toString());
}
function onError(e) {
	alert('error' + e);
}
function onSuccess(json,status) {
	alert('Success' + status);
	
	var answerTemplate = $("#answerTemplate").html();
	var template = answerTemplate.format(json.writer,new Date(json.createdDate),json.contents,json.answerId);
	$(".qna-comment-slipp-articles").prepend(template)
	
}
function onErrorToDelete(e) {
	
}
function onSuccessToDelete(json, status) {
	
}

function deleteAnswer(e) {
	// 
	//Element에 있는 
	// 
	e.preventDefault();
	var queryString = $(e.toElement).closest("form[class=form-delete]").serialize();
	$.ajax({
		type : 'post',
		url : '/api/qna/deleteAnswer',
		data : queryString,
		dataType : 'json',
		error : onErrorToDelete, 
		success : onSuccessToDelete,
	});
}

function addAnswer(e) {
	e.preventDefault(); //submit이 자동으로 동작되는 것을 막는다. 
	//form data binding
	var queryString = $("form[name=answer]").serialize();
	//alert('QueryString:' + queryString);
	
	$.ajax({
		type : 'post',
		url : '/api/qna/addAnswer', 
		data : queryString,
		dataType : 'json',
		error : onError, 
		success : onSuccess,
	})
}

