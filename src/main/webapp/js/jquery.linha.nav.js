;(function($){
	$.fn.nav = function(options){
		return new $.nav(options, this);	
	};
	
	$.nav = function(options, elem){
		
		var padrao = {
			seletor: '.menu li',
			filho: 'ul:first', 	
			
			evento : 'mouseenter', 		
			eventoFim : 'mouseleave', 	
			
			anima: false,				
			slide : false,				
			tempoMostra: 200,			
			tempoEsconde: 200,			

			onInicio : null,			
			onFim: null					
		};
		var o = $.extend(padrao, options);
		
		if(elem === undefined){ elem = $(o.seletor);}
		
		return elem.each(function(){
				
			var t = $(this);
			
			//Eventos customizados
			if(o.evento){
				t.bind(o.evento, function(){return showNav(t);});
			}
			
			if(o.eventoFim){
				t.bind(o.eventoFim, function(){ return hideNav(t);});
			}
		});
	
		/**
		 * @param {Object} t - elemento this
		 */
		function showNav(t){
			if ($.isFunction(o.onInicio)) {o.onInicio.apply(t);}
			if (o.anima) {
				if (o.slide) {
					$(o.filho, t).slideDown(o.tempoMostra);
				}
				else {
					$(o.filho, t).fadeIn(o.tempoMostra);
				}
			}else{
				$(o.filho, t).show();
			}
		};
		/**
		 * @param {Object} t - elemento this
		 */
		function hideNav(t){
			if(o.anima){
				if (o.slide) {
						$(o.filho, t).slideUp(o.tempoEsconde);
				}else {
					$(o.filho, t).fadeOut(o.tempoEsconde);
				}
			}else{
				$(o.filho, t).hide();
			}
			if ($.isFunction(o.onFim)) {o.onFim.apply(t);}
		};
	};
})(jQuery);
