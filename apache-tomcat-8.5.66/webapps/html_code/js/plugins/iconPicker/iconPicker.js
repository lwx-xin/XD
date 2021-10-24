/*
 * Bootstrap 3.3.6 IconPicker - jQuery plugin for Icon selection
 *
 * Copyright (c) 20013 A. K. M. Rezaul Karim<titosust@gmail.com>
 * Modifications (c) 20015 Paden Clayton<fasttracksites.com>
 *
 * Licensed under the MIT license:
 *   http://www.opensource.org/licenses/mit-license.php
 *
 * Project home:
 *   https://github.com/titosust/Bootstrap-icon-picker
 *
 * Version:  1.0.1
 *
 */

(function($) {

    $.fn.iconPicker = function( data ) {
		if(data==null)data={};
		if($.type(data)=="object"){
			initIconPicker(this,data);
		} else {
			setValue(this,data);
		}
		
		function setValue(iconPickerEles,value){
			iconPickerEles.each(function(){
				$(this).val(value);
				var iconBtn = $(this).next();
				if(value==""){
					value = "glyphicon  glyphicon-picture";
				}
				iconBtn.find("i").attr("class",value);
			})
		}
		
		function initIconPicker(iconPickerEles,options){
			
			var mouseOver=false;
			var $popup=null;
			var icons=options.icons;
			var default_icon = options.defaultIcon==null?"glyphicon  glyphicon-picture":options.defaultIcon;
			var settings = $.extend({
				
			}, options);
			iconPickerEles.each( function() {
				element=this;
				if(!settings.buttonOnly && $(this).data("iconPicker")==undefined ){
					$this=$(this).addClass("form-control");
					$wraper=$("<div/>",{class:"input-group"});
					$this.wrap($wraper);
	
					$button=$("<span class=\"input-group-addon pointer\"><i class=\""+default_icon+"\"></i></span>");
					$this.after($button);
					(function(ele){
						$button.click(function(){
							createUI(ele);
							showList(ele,icons);
						});
					})($this);
	
					$(this).data("iconPicker",{attached:true});
				}
			
				function createUI($element){
					$popup=$('<div/>',{
						css: {
							'top':$element.offset().top+$element.outerHeight()+6,
							'left':$element.offset().left
						},
						class:'icon-popup'
					})
	
					$popup.html('<div class="ip-control"> \
									  <ul> \
										<li><a href="javascript:;" class="btn" data-dir="-1"><span class="glyphicon  glyphicon-fast-backward"></span></a></li> \
										<li><input type="text" class="ip-search glyphicon  glyphicon-search" placeholder="Search" /></li> \
										<li><a href="javascript:;"  class="btn" data-dir="1"><span class="glyphicon  glyphicon-fast-forward"></span></a></li> \
									  </ul> \
								  </div> \
								 <div class="icon-list"> </div> \
								 ').appendTo("body");
					
					
					$popup.addClass('dropdown-menu').show();
					$popup.mouseenter(function() {  mouseOver=true;  }).mouseleave(function() { mouseOver=false;  });
	
					var lastVal="", start_index=0,per_page=30,end_index=start_index+per_page;
					$(".ip-control .btn",$popup).click(function(e){
						e.stopPropagation();
						var dir=$(this).attr("data-dir");
						start_index=start_index+per_page*dir;
						start_index=start_index<0?0:start_index;
						if(start_index+per_page<=210){
						  $.each($(".icon-list>ul li"),function(i){
							  if(i>=start_index && i<start_index+per_page){
								 $(this).show();
							  }else{
								$(this).hide();
							  }
						  });
						}else{
						  start_index=180;
						}
					});
					
					$('.ip-control .ip-search',$popup).on("keyup",function(e){
						if(lastVal!=$(this).val()){
							lastVal=$(this).val();
							if(lastVal==""){
								showList(icons);
							}else{
								showList($element, $(icons)
										.map(function(i,v){ 
												if(v.toLowerCase().indexOf(lastVal.toLowerCase())!=-1){return v} 
											}).get());
							}
							
						}
					});  
					$(document).mouseup(function (e){
						if (!$popup.is(e.target) && $popup.has(e.target).length === 0) {
							removeInstance();
						}
					});
	
				}
				function removeInstance(){
					$(".icon-popup").remove();
				}
				function showList($element,arrLis){
					$ul=$("<ul>");
					
					for (var i in arrLis) {
						$ul.append("<li><a style='width:32px;height:32px;' href=\"#\" title=\""+arrLis[i]+"\"><span class=\""+arrLis[i]+"\"></span></a></li>");
					};
	
					$(".icon-list",$popup).html($ul);
					$(".icon-list li a",$popup).click(function(e){
						e.preventDefault();
						var title=$(this).attr("title");
						if($element.is("input")){
							$element.val(title);
						} else {
							$element.html(title);
						}
						$element.parent(".input-group").find(".input-group-addon i").attr("class", title);
						removeInstance();
					});
				}
	
			});
			    
		}
	}

}(jQuery));
