// JavaScript Document

$(window).load(
		function() {
			"use strict";
			var preloaderDelay = 800;
			var preloaderFadeOutTime = 1000;
			function hidePreloader() {
				var loadingAnimation = $('#loading-animation');
				var preloader = $('.main');
				loadingAnimation.fadeOut();
				preloader.delay(preloaderDelay).fadeOut(preloaderFadeOutTime,
						function() {
						});
			}
			hidePreloader();

		});