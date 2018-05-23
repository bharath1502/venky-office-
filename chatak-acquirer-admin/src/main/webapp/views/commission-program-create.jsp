<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
  int year = Calendar.getInstance().get(Calendar.YEAR);
%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Chatak Acquirer Admin</title>
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<style>
.pan-btn {
	padding: 10px
}

.head-border {
	border-bottom: 2px solid #bfbfbf;
}

.radio-btn-align {
	float: left;
	width: 17px;
}

.radio-btn-input-align {
	width: 160px;
	margin-left: 5px;
	font-size: 11px !important;
	margin-top: 5px;
}

.last-input-field {
	margin-left: -100px;
	width: 15%;
}

.add-btn-style {
	margin: 10px 30px
}

.other-fee-label {
	border-bottom: 2px solid #bfbfbf
}

.final-create-btn {
	margin-right: 20px
}

.bck-color {
	background: #dddddd;
}

.filter-content {
	margin-bottom: 20px;
}

.dollar {
	color: #373737;
	font-size: 13px;
	left: 20px;
	position: absolute;
	top: -14px;
}

.flat-dollar {
	color: #373737;
	font-size: 13px;
	left: 20px;
	position: absolute;
	top: 8px;
}
.marginML-25{
	margin-left:-25px;
	width:18.66%
}
.dynamicData
{
   margin-left: -15px;margin-right: 2px;
}
.dynamicradio
{
	padding: 0px; margin-left: -12px;
}
</style>
</head>
<body>
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<header class="col-sm-12 all-page-header">
				<!--Header Logo Start -->
				<!--Navigation Block Start -->
				<%-- <jsp:include page="header.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
				<!--Navigation Block Start -->
				<!--Header Logo End -->
			</header>
			<!--Header Block End -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="commission-program-create.label.commissionprogram" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="footer.copyright2" /><spring:message code="commission-program-create.label.create" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,commissionProgramsEdit)}">
					<div class="tab-header-container-first">
						<a href="show-commission-program-search"><spring:message code="commission-program-create.label.searchtab" /></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,commissionProgramsCreate)}">
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="commission-program-create.label.createtab" /></a>
					</div>
					</c:if>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<form:form action="commissionProgramCreate" modelAttribute="commissionDTO" name="commissionDTO">
						<div class="col-sm-12">
							<!-- Page Form Start -->
							<div class="form-horizontal main-content-holder bck-color">
								<div class="row">
									<div class="field-element-row margin0 paddingLR15"></div>
								</div>
								<div class="col-xs-12">
									<div class="discriptionErrorMsg col-xs-12">
										<span class="red-error">&nbsp;${error }</span> <span
											class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>
								<!--Panel Action Button Start -->
								<div class="col-sm-12" style="margin-left: -44px;">
									
									<div class="form-group pan-btn" >
										<fieldset class="col-sm-6">
											<label class="control-label col-sm-6"><spring:message code="commission-program-create.label.commissionprogramname" />
											<span class='required-field'>*</span></label>
											<div class="col-sm-6">
												<input type="text" name="commissionName" id="commissionName" maxlength="50" onkeydown="validateSpace(this)"
													class="form-control commissionName" onblur="this.value=this.value.trim();" >
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="commissionNameEr" class="red-error">&nbsp;</span>
													</div>
											</div>
										</fieldset>
									 <fieldset class="col-sm-6">
												<label class="control-label col-sm-6"><spring:message code="commission-program-create.label.status" /><span class='required-field'>*</span></label>
												<div class="col-sm-6">
												<select class="form-control marginB5 status"
													name="status" id="status" ><option value=""><spring:message code="commission-program-create.label.select" /></option>
													<option value="Active" class="active"><spring:message code="commission-program-create.label.active" /></option>
													<option value="Suspend" class="suspend"><spring:message code="commission-program-create.label.suspend" /></option></select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="statusEr" class="red-error">&nbsp;</span>
													</div>
												</div>
									</fieldset>
											</div>
										<fieldset class="col-sm-12" style="margin-top: -13px;">
											<!-- <label class="col-sm-3 head-border">Discount Rate</label> -->
										</fieldset>
										<fieldset class="col-sm-12 padding0 filter-content" style="margin-left: 12px;">
											<fieldset class="col-sm-4" style="width:18.66%">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="commission-program-create.label.commissiontype" /><span class='required-field'>*</span></label><select
													class="form-control marginB5  commissionType" 
													name="otherCommissionDTO[0].commissionType" id="otherCommissionDTO[0].commissionType" 
													 onchange ="showValue(id)">
													<option value=""><spring:message code="commission-program-create.label.select" /></option>
													<option value="flat" ><spring:message code="commission-program-create.label.flat" /></option>
													<option value="slab" ><spring:message code="commission-program-create.label.slab" /></option></select>
													
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="otherCommissionDTO[0].commissionTypeEr" class="red-error">&nbsp;</span>
														</div>
											</fieldset>
											
											<fieldset class="col-sm-4 marginML-25 flat">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="commission-program-create.label.flatfee" /><span class='required-field'>*</span></label> <input type="text" value=""
													name="otherCommissionDTO[0].flatFee" id="otherCommissionDTO[0].flatFee" class="form-control flatFee"
													  style="padding-left: 25px" onblur="this.value=this.value.trim();"/>
												<h3 class="flat-dollar"><spring:message code="commission-program-create.label.$" /></h3>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="otherCommissionDTO[0].flatFeeEr" class="red-error">&nbsp;</span>
														</div>
											</fieldset>
											
											<fieldset class="col-sm-4 marginML-25 from">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="commission-program-create.label.fromvolume" /><span class='required-field'>*</span></label> <input type="text" value=""
													 name="otherCommissionDTO[0].fromValue" id="otherCommissionDTO[0].fromValue" 
													 class="form-control fromValue"
													style="padding-left: 25px"  onblur="this.value=this.value.trim();"/>
												<h3 class="flat-dollar"><spring:message code="commission-program-create.label.$" /></h3>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="otherCommissionDTO[0].fromValueEr" class="red-error">&nbsp;</span>
														</div>
											</fieldset>
											<fieldset class="col-sm-4 marginML-25 to">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="commission-program-create.label.tovolume" /><span class='required-field'>*</span></label> <input type="text" value=""
													name="otherCommissionDTO[0].toValue" id="otherCommissionDTO[0].toValue" 
													class="form-control toValue"
													style="padding-left: 25px"  onblur="this.value=this.value.trim();"/>
												<h3 class="flat-dollar"><spring:message code="commission-program-create.label.$" /></h3>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="otherCommissionDTO[0].toValueEr" class="red-error">&nbsp;</span>
														</div>
											</fieldset>
											
											<fieldset class="col-sm-4 marginML-25 amnt">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="commission-program-create.label.amount" /><span class='required-field'>*</span></label> <input type="text" value=""
													name="otherCommissionDTO[0].amount" id="otherCommissionDTO[0].amount"  
													class="form-control amount"
													style="padding-left: 25px" onblur="this.value=this.value.trim();"/>
												<h3 class="flat-dollar"><spring:message code="commission-program-create.label.$" /></h3>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="otherCommissionDTO[0].amountEr" class="red-error">&nbsp;</span>
														</div>
											</fieldset>
										</fieldset>
										<div class="added-row"></div> 
									</div>
									<!--Panel Action Button End -->
								<button type="button" class="addRow add-btn-style">
									<span class="glyphicon glyphicon-plus"></span>
								</button> 
								<!-- Page Form End -->
							</div>
							<div class="form-horizontal main-content-holder bck-color">
								<fieldset class="col-sm-12">
									<label class="col-sm-2 other-fee-label"><spring:message code="commission-program-create.label.$" /><spring:message code="commission-program-create.label.otherfees" /></label>
								</fieldset>
								<div class="form-group">
									<label class="control-label col-sm-4"><spring:message code="commission-program-create.label.monbfeeamt" /></label>
									<div class="col-sm-4">
										<input type="text" class="form-control setupFee" placeholder="00.00" value="0"
											style="padding-left: 25px;" name="merchantOnBoardingFee" id="merchantOnBoardingFee" onblur="this.value=this.value.trim();validateMerchantOnBoardingFee()" />
										<h3 class="dollar"><spring:message code="commission-program-create.label.$" /></h3>
										<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="merchantOnBoardingFeeEr" class="red-error">&nbsp;</span>
														</div>
									</div>
								</div>
						

							</div>
							<div class="col-sm-12 button-content">
								<fieldset class="col-sm-7 pull-right">
									<input type="submit" class="form-control button pull-right marginL10" value="<spring:message code="commission-program-create.label.createbutton" />" id ="createFeePgm" />
									<input type="button" class="form-control button pull-right" value="<spring:message code="commission-program-create.label.resetbutton" />" onclick= "resetCommissionCreate()" />
									<input type="button" class="form-control button pull-right marginL10" value="<spring:message code="commission-program-create.label.cancelbutton" />" id="cancel" />
								</fieldset>
							</div>
						</div>
					</form:form>
				</div>
			</article>
			<!--Article Block End-->
			<footer class="footer">
				<jsp:include page="footer.jsp"></jsp:include>
			</footer>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script src="../js/commission-program.js"></script>
	<script>
		var row = 0;

		$(document).ready(function() {
			
			$(".flat").hide();
			$(".from").hide();
			$(".to").hide();
			$(".amnt").hide();
			
			$( "#navListId3" ).addClass( "active-background" );
			
			$(".addRow").click(function() {
				
				var val = document.getElementById("otherCommissionDTO[0].commissionType").value;
				
				if(val=="slab"){
					
					row++;
					var newFilterRow = "<fieldset class='col-sm-12 padding0 filter-content remove-row' style='margin-left: 12px;'>"
					+"<fieldset class='col-sm-4' style='width:18.66%'>"
					+"</fieldset>"
					
					+"<fieldset class='col-sm-4 marginML-25 from'>"
					  +"<label data-toggle="tooltip" data-placement="top" title=""><span class='required-field'></span></label> <input type='text' value=''"
					  +"name='otherCommissionDTO["+row+"].fromValue' id='otherCommissionDTO["+row+"].fromValue' class='form-control fromValue'"
					  +"style='padding-left: 25px' />"
					  +"<h3 class='flat-dollar'>$</h3>"
					  +"<div class='discriptionErrorMsg'>"
					  +"<span id='otherCommissionDTO["+row+"].fromValueEr' class='red-error'>&nbsp;</span>"
					  +"</div>"
					  +"</fieldset>"
					  +"<fieldset class='col-sm-4 marginML-25 to'>"
					  +"<label data-toggle="tooltip" data-placement="top" title=""><span class='required-field'></span></label> <input type='text' value=''"
					  +"name='otherCommissionDTO["+row+"].toValue' id='otherCommissionDTO["+row+"].toValue' class='form-control toValue'"
					  +"style='padding-left: 25px' />"
					  +"<h3 class='flat-dollar'>$</h3>"
					  +"<div class='discriptionErrorMsg'>"
					  +"<span id='otherCommissionDTO["+row+"].toValueEr' class='red-error'>&nbsp;</span>"
					  +"	</div>"
					  +"</fieldset>" 
					  +"<fieldset class='col-sm-4 marginML-25 amnt'>"
					  +"<label data-toggle="tooltip" data-placement="top" title=""><span class='required-field'></span></label> <input type='text' value=''"
					  +"name='otherCommissionDTO["+row+"].amount' id='otherCommissionDTO["+row+"].amount' class='form-control amount'"
					  +"style='padding-left: 25px' />"
					  +"<h3 class='flat-dollar'>$</h3>"
					  +"<div class='discriptionErrorMsg'>"
					  +"<span id='otherCommissionDTO["+row+"].amountEr' class='red-error'>&nbsp;</span>"
					  +"</div>"
					  +"</fieldset>"
					  +"<span class='glyphicon glyphicon-trash delete-icon' style = 'margin: 25px 20px 0px 0px'></span></fieldset></fieldset>";
					  $(".added-row").append(newFilterRow);
				}
			});
			
		
		
		 	
		 	$('body').on('click', '.delete-icon', function() {
				var deletedSelctOption = $(this).siblings().eq(0).find('.commissionType').find('option:selected').attr('class');
				$(this).parent().remove();
				
			}); 

		 	$('body').on('blur','.fromValue',function() {
		 		fromVolume($(this));
			});
		 	
		 	$('body').on('blur','.toValue',function() {
		 		toVolume($(this));
			});
		 	$('body').on('blur','.amount',function() {
		 		amount($(this));
			});
		 	$('body').on('blur','.commissionType',function() {
		 		commissionType($(this));
			});
		 	$('body').on('blur','.flatFee',function() {
		 		flatFee($(this));
			});
		 	$('body').on('blur','.status',function() {
		 		statuss($(this));
			});
		 	
		 	$('body').on('blur','.commissionName',function() {
		 		commissionNames($(this));
			});
			$('#cancel').on('click', function() {
				window.location.href = 'show-commission-program-search';
			});
			
			$('#createFeePgm').on('click', function() {
				
			
				var value =document.getElementById("otherCommissionDTO[0].commissionType").value.trim();
				
				if(value=="slab"){
				
			 $('.fromValue,.toValue,.amount,.commissionName,.status,.commissionType').trigger('blur');}
				else if(value=="flat")
				{
					$('.flatFee,.status,.commissionName,.commissionType').trigger('blur');
				}
				else
				{
					$('.status,.commissionName,.commissionType').trigger('blur');
				}
				
		
				var isValid = true;
				$("[id$='Er']").each(function() {
					if($(this).text().trim().length > 0) {
						isValid = false;
						return false;
					}
				});

				return isValid;
			});
		
		});
	</script>
</body>
</html>