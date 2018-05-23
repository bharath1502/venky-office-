/* JS Utils */
$(document).ready(function() {
	$(".field-element-row label").each(function () {
		$(this).attr('title', $(this).text());
		var loadReqField = $(this).find('span');
		if (this.offsetWidth < this.scrollWidth) {
			if (loadReqField.hasClass('required-field')) {
				loadReqField.addClass('overflow-stop');
			}
		}
	});

	$(".discriptionErrorMsg").tooltipOnOverflow();
	$(".field-element-row label").tooltipOnOverflow();

	$('[data-toggle="tooltip"]').tooltip();
});

/* Common function to Check the width of the labels and message and add tooltip if it exceeds width*/
(function($) {
    'use strict';
    $.fn.tooltipOnOverflow = function() {
        $(this).on("mouseenter", function() {
            if (this.offsetWidth < this.scrollWidth) {
                $(this).attr('title', $(this).text());
                $(this).removeAttr("title");
            } else {
                $(this).removeAttr("title");
                $(this).removeAttr('data-original-title');
            }
        });
    };
})(jQuery);

function loadMsgTitleText () {
	$(".discriptionErrorMsg").each(function () {
		$(this).attr('data-original-title', $(this).text());
	});
}