//検索フォーム非表示
function tabChange(){
	radio = document.getElementsByName('tab')
	if(radio[0].checked) {
		//フォーム
		document.getElementById('tab1').style.display = "none";
	}else if(radio[1].checked) {
		//フォーム
		document.getElementById('tab1').style.display = "";
	}
}

//オンロード
window.onload = function(){
	tabChange();
	hide();
}
//クリアボタン
function clr(){
	document.searchForm.dormitory_id.value = "" ;
	document.searchForm.dormitory_name.value = "" ;
	document.searchForm.room_id.value = "" ;
	document.searchForm.meter.value = "" ;
	document.searchForm.create_dt_from.value = "" ;
	document.searchForm.create_dt_to.value = "" ;
	document.searchForm.plan.value = "" ;
	//document.getElementById("idText").value="";
}

//一括削除表示
function hide(){
	const delList = document.querySelectorAll('.delCheck');
		delList.forEach((del) => {
			if(document.getElementById("delCheckHide").checked == true){
				del.style.display = "";
			}else{
				del.style.display = "none";
			}
	});
}