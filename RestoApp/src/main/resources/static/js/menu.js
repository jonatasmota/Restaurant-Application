/**
 * 
 */



 $('document').ready(function(){
		
	//edit menu item
	$('.table .edit').on('click', function(event){
		event.preventDefault();
		var href=$(this).attr('href');
		
		$.get(href, function(menuItem){
			$('#menuName').val(menuItem.menuName);
			$('#menuItemName').val(menuItem.menuItemName);
			$('#menuItemPrice').val(menuItem.menuItemPrice);
			$('#menuItemImgUrl').val(menuItem.menuItemImgUrl);
			$('#menuItemDescription').val(menuItem.menuItemDescription);
		});
		$('#editMenuItemModal').modal();
	});
	
	//delete menu 
	$('.table #deleteMenu').on('click', function(event){
		event.preventDefault();
		var href = $(this).attr('href');
		$('#deleteMenuModal #deleteMenu').attr('href', href);
		$('#deleteMenuModal').modal();
	});
	
	//delete menu item
	$('.table #deleteMenuItem').on('click', function(event){
		event.preventDefault();
		var href = $(this).attr('href');
		$('#deleteMenuItemModal #deleteRef').attr('href', href);
		$('#deleteMenuItemModal').modal();
	});
});