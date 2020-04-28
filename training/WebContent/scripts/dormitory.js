//フォーム切り替え
function tabChange(){
	radio = document.getElementsByName('tab')
	if(radio[0].checked) {
		document.getElementById('tab1').style.display = "";
		document.getElementById('tab2').style.display = "none";
	}else if(radio[1].checked) {
		document.getElementById('tab1').style.display = "none";
		document.getElementById('tab2').style.display = "";
	}
}

//オンロード
window.onload = function(){
	tabChange();
	hide();
}

//クリアボタン
function clr(){
	document.searchForm.dId.value = "" ;
	document.searchForm.dName.value = "" ;
	document.searchForm.cDtFrom.value = "" ;
	document.searchForm.cDtTo.value = "" ;
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

//function hide(){
//	const delList = document.querySelectorAll('.delCheck');
//		delList.forEach((del) => {
//			if(del.style.display == "none"){
//				del.style.display = "";
//			}else{
//				del.style.display = "none";
//			}
//	});
//}

