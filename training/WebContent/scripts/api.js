
$(function(){
	$('#getDomCount').click(function(){
		$.get('http://localhost:18080/training/api', function( data ) {

			//$("body").append("<p>社員寮数:"+data.count+"件</p>");

			$("#domCount").text("社員寮数:"+data.count+"件");
		});
	});

	$('#getRoom').click(function(){


		$("#roomList").append("<tr><td>社員寮名</td><td>部屋番号</td></tr>");
		$.get('http://localhost:18080/training/api4', function( data ) {
			var rooms = data;
		    for (var i = 0; i < rooms.length; i++) {
		        let room = rooms[i];
		        $("#roomList").append("<tr><td>"+room.dormitory_name+"</td>" +
						"<td>"+room.room_id+"</td></tr>");
		    }
		    $("#roomList tr:nth-child(even)").css('background-color', '#eee');
		});
	});

});
