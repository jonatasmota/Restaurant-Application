/**
 * 
 */
 var selectedTable;
 var myButtonDown = false;

$('.table').draggable({ containment: ".canvas", scroll: false, stop: function(event, ui){
	p = $(this).position();
	$(this).children('.tableX').val(p.left)
	$(this).children('.tableY').val(p.top)
	submitPosition($(this).attr('id'))
} });

$(document).ready(function(){
	tables = $('.table').each(function(index){
		$(this).css({left: $(this).children('.tableX').val() + 'px', top: $(this).children('.tableY').val() + 'px'}) 
	})
	
	if($('#floorPlanId').val() == 0){
		$('#addTables').prop('disabled', true)
	}else{
		$('#addTables').prop('disabled', false)
	}
})

$('.canvas').mousedown(function() {
    myButtonDown = true;
    $('body').addClass('noselect');
});

$(document).mouseup(function() {
    if (myButtonDown) {
        myButtonDown = false;
        $('body').removeClass('noselect');
    }
})

function submitPosition(id) {

	tableForSubmit = document.getElementById(id);
    var table = {
	tableId: 0,
	coordinates:{
		cId:0,
		xAxis:0,
		yAxis:0
	}
};
    table.tableId = tableForSubmit.querySelector(".tableId").value;
    table.coordinates.cId = tableForSubmit.querySelector(".cId").value;
    table.coordinates.xAxis = tableForSubmit.querySelector(".tableX").value;
    table.coordinates.yAxis = tableForSubmit.querySelector(".tableY").value;

	console.log(table);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/moveTable",
        data: JSON.stringify(table),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {  },
        error: function (e) {  }
    });

}

function seeTable(id){
	 
	 $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/moveTable",
        data: JSON.stringify(table),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {  },
        error: function (e) {  }
    });
}
 