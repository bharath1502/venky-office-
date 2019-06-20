/**
 * 
 */

var jcrop_api;
var pagelogo;
$('#imageUpload')
		.change(
				function() {
					$('.jcrop-holder').show();
					$('#canvas').hide();
					var checkFilesLength = document
							.getElementById('imageUpload');
					if (checkFilesLength.files.length >= 1) {
						if ($('.jcrop-holder').children().length >= 1) {
							jcrop_api.destroy();
						}
					}

					var reader = new FileReader();
					reader.onload = function(e) {
						$('#userSelectedImage').attr("src", e.target.result)
								.css('opacity', 0);
						var imgwidth = $('#userSelectedImage').width();
						var imgheight = $('#userSelectedImage').height();
						if (imgwidth >= 350 && imgheight >= 250) {
							$('#userSelectedImage').attr("src", "").css(
									'opacity', 1);
							$('#imageError')
									.text(
											webMessages.pleaseSelecttheImageinBetweentheRange350250);
											loadMsgTitleText();

						} else {
							$('#imageError').text("");
							$('#userSelectedImage')
									.attr("src", e.target.result).css(
											'opacity', 1);
						}

						$('#userSelectedImage').Jcrop({
							onChange : SetCoordinates,
							onSelect : SetCoordinates,
							minSize : [ 260, 140 ],
							maxSize : [ 260, 140 ]

						}, function() {
							jcrop_api = this;
						});
						
						/*results = image.next('.results' ),
						x = $('.cropX', results),
		     			y = $('.cropY', results),
						w = $('.cropW', results),
						h = $('.cropH', results);
*/
						
						/*$('#userSelectedImage').cropbox( {width: imgwidth, height: imgheight, showControls: 'auto' } ).on('cropbox', function( event, results, img ) {
						x.text( results.cropX );
						y.text( results.cropY );
						w.text( results.cropW );
						h.text( results.cropH );
						});
*/
						
						$('.jcrop-holder img').attr("src", e.target.result);

					}
					reader.readAsDataURL($(this)[0].files[0]);
				});

$('#btnCrop').click(function() {
	saveCroppedImage();
});

function SetCoordinates(c) {
	$('#imgX1').val(c.x);
	$('#imgY1').val(c.y);
	$('#imgWidth').val(c.w);
	$('#imgHeight').val(c.h);
	$('#btnCrop').show();
	$('#cropsave').hide();
	$('#clearCrop').show();
};
$('#cropsave').on("click", function() {
	saveCroppedImage();
	$('.jcrop-holder').hide();
	$('#btnCrop').hide();
	$('#cropsave').hide();
	$('#clearCrop').show();
});
$('#clearCrop').on("click", function() {
	clearUploadedImage();
	imageSrcValidate();
});

function clearUploadedImage() {
	jcrop_api.destroy();
	$('#btnCrop').hide();
	$('#cropsave').hide();
	$('#clearCrop').hide();
	$('#imageUpload').val('');
	$('#userSelectedImage').attr('src', '#');
	$('#userSelectedImage').attr("style", "");
	$('#userSelectedImage').hide();
	$('#userSelectedImage').val('');
	document.getElementById("userSelectedImage").innerHTML = "";
	$('#payPageLogo').val('');
	$('#canvas').hide();
}

function saveCroppedImage() {
	$('#cropsave').show();
	$('#canvas').show();
	$('#canvas').css('border', '2px solid #ffffff');
	var x1 = $('#imgX1').val();
	var y1 = $('#imgY1').val();
	var width = $('#imgWidth').val();
	var height = $('#imgHeight').val();
	var canvas = $("#canvas")[0];
	var context = canvas.getContext('2d');
	$('#cropsave').show();

	var img = new Image();
	img.onload = function() {
		canvas.height = height;
		canvas.width = width;
		context.drawImage(img, x1, y1, width, height, 0, 0, width, height);
		$('#payPageLogo').val(canvas.toDataURL());
		document.getElementById('userSelectedImage').innerHTML = canvas
				.toDataURL();
	};
	img.src = $('#userSelectedImage').attr("src");

};

$('#cropsave').on("click", function() {
	$('.jcrop-holder').hide();
	$('#btnCrop').hide();
	$('#cropsave').hide();
	imageSrcValidate();
});

$(document).ready(function() {

});

$('#btnImageUpload').click(function() {
	$("input[type='file']").trigger('click');

});

function validateHeader() {
	var header = get('header').value.trim();
	if (isEmpty(header)) {
		setError(get('header'), webMessages.pleaseEnterHeaderText);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('header'), '');
		return true;
	}
}

function validateFooter() {
	var footer = get('footer').value.trim();
	if (isEmpty(footer)) {
		setError(get('footer'), webMessages.pleaseEnterFooterText);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('footer'), '');
		return true;
	}
}
function imageSrcValidate() {

	var src = document.getElementById("userSelectedImage").getAttribute('src')
			.trim();
	var payPageLogo = get('payPageLogo').value.trim();
	if (isEmpty(payPageLogo)) {
		$('#imageError').text(webMessages.pleaseUploadthePayPageLogo);
		loadMsgTitleText();
		return false;
	} else if (isEmpty(src)) {
		$('#imageError').text(webMessages.pleaseUploadthePayPageLogo);
		loadMsgTitleText();
		return false;
	} else {
		$('#imageError').text("");
		return true;
	}
}

function validatePayPageConfig() {
	if (!validateHeader() | !validateFooter() | !imageSrcValidate()) {
		return false;
	}
	return true;
}

function cancel() {
	window.location.href = "dash-board";
}