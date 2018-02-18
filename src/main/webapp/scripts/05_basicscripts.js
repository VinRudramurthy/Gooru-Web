// JavaScript Document

// JavaScript Document

	
	
	function searchStandard1(){
		$(".searchStandard1 .standeredList").slideToggle();
		$(this).toggleClass("active");
	}
	
	function searchStandard2(){
		$(".searchStandard2 .standeredList").slideToggle();
		$(this).toggleClass("active");
	}
	function searchStandard3(){
		$(".searchStandard3 .standeredList").slideToggle();
		$(this).toggleClass("active");
	}
	function searchStandard4(){
		$(".searchStandard4 .standeredList").slideToggle();
		$(this).toggleClass("active");
	}
   
	
	
	function callaccrodian(){
	$('label.tree-toggler').click(function () {
		$(this).parent().children('ul.tree').toggle(300);
		
	});
	
	$('.oneLevel label').click(function () {
			$('label').toggleClass('active');
			$('.twoLevel label').removeClass('active');
			});
	$('.twoLevel label').click(function () {
			$('label').toggleClass('active');
			
	});
}

