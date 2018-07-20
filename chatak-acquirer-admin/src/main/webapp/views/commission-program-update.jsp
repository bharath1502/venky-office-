<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
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
.padding-left_25px
{
	padding-left:25px;
}
</style>
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
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
						<span class="breadcrumb-text"><spring:message code="commission-program-update.label.commissionprograms" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="commission-program-update.label.edit" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first">
						<a href="show-commission-program-search"><spring:message code="commission-program-update.label.searchtab" /></a>
					</div>
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="commission-program-update.label.edittab" /></a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<form:form action="commissionProgramUpdate" modelAttribute="commissionDTO" name="commissionDTO">
					<form:hidden path="commissionProgramId" />
					<input type="hidden" name="CSRFToken" value="${tokenval}">
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
								<div class="col-sm-12">
									
									<div class="form-group pan-btn" >
										<fieldset class="col-sm-6">
											<label class="control-label col-sm-6"><spring:message code="commission-program-update.label.commissionprogramname"/><span class='required-field'>*</span></label>
											<div class="col-sm-6">
												<form:input path="commissionName" id="commissionName" maxlength="50" 
													cssClass="form-control commissionName" readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="commissionNameEr" class="red-error">&nbsp;</span>
													</div>
											</div>
										</fieldset>
										
											<fieldset class="col-sm-6" >
												<label class="control-label col-sm-6"><spring:message code="commission-program-update.label.status"/><span class='required-field'>*</span></label>
												<div class="col-sm-6">
												<form:select cssClass="form-control status" path="status" id="status" ><option value=""><spring:message code="commission-program-update.label.select"/></option>
													<form:option value="Active"><spring:message code="commission-program-update.label.active"/></form:option>
													<form:option value="Suspend"><spring:message code="commission-program-update.label.suspend"/></form:option></form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="statusEr" class="red-error">&nbsp;</span>
													</div>
												</div>
									</fieldset>
											</div>
										<fieldset class="col-sm-12 padding0 filter-content">
											<fieldset class="col-sm-6">
												<label class="control-label col-sm-6"><spring:message code="commission-program-update.label.commissiontype"/><span class='required-field'>*</span></label>
												<div class="col-sm-6 ">
												<form:select
													cssClass="form-control commissionType" 
													path="otherCommissionDTO[0].commissionType" id="otherCommissionDTO[0].commissionType" 
													 onchange ="showValues(id)">
													<form:option value=""><spring:message code="commission-program-update.label.commissiontype"/><spring:message code="commission-program-update.label.select"/></form:option>
													<form:option value="flat"><spring:message code="commission-program-update.label.flat"/></form:option>
													<form:option value="slab"><spring:message code="commission-program-update.label.slab"/></form:option></form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="otherCommissionDTO[0].commissionTypeEr" class="red-error">&nbsp;</span>
													</div>
												</div>
											</fieldset>
											</fieldset>
											<div class="col-sm-12" style='margin-left: 220px;'>
											<fieldset class="col-sm-4 marginML-25 flat">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="commission-program-update.label.flatfee"/><span class='required-field'>*</span></label> <form:input
													path="otherCommissionDTO[0].flatFee" id="otherCommissionDTO[0].flatFee" cssClass="form-control padding-left_25px flatFee"
													  onblur="this.value=this.value.trim();"/>
												<h3 class="flat-dollar"><spring:message code="commission-program-update.label.$"/></h3>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="otherCommissionDTO[0].flatFeeEr" class="red-error">&nbsp;</span>
														</div>
											</fieldset>
											</div>
									 <fieldset class="col-sm-12 padding0 filter-content added-row" >
										<fieldset class="col-sm-12" style="margin-top: -13px;">
											<!-- <label class="col-sm-3 head-border">Discount Rate</label> -->
										</fieldset>
											<c:forEach var="rows" items="${commissionDTO.otherCommissionDTO}" varStatus="status">	
											<div class="col-sm-12 commission-values" style='margin-left: 220px;'>
											
											<fieldset class="col-sm-4 marginML-25 from">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="commission-program-update.label.fromvolume"/><span class='required-field'>*</span></label> <form:input
													  path="otherCommissionDTO[${status.index}].fromValue" id="otherCommissionDTO[${status.index}].fromValue" 
													 cssClass="form-control padding-left_25px fromValue" onblur="this.value=this.value.trim();"/>
												<h3 class="flat-dollar"><spring:message code="commission-program-update.label.$"/></h3>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="otherCommissionDTO[${status.index}].fromValueEr" class="red-error">&nbsp;</span>
														</div>
											</fieldset>
											<fieldset class="col-sm-4 marginML-25 to">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="commission-program-update.label.tovolume"/><span class='required-field'>*</span></label> <form:input
													path="otherCommissionDTO[${status.index}].toValue" id="otherCommissionDTO[${status.index}].toValue" 
													cssClass="form-control padding-left_25px toValue" onblur="this.value=this.value.trim();"/>
												<h3 class="flat-dollar"><spring:message code="commission-program-update.label.$"/></h3>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="otherCommissionDTO[${status.index}].toValueEr" class="red-error">&nbsp;</span>
														</div>
											</fieldset>
											
											<fieldset class="col-sm-4 marginML-25 amnt">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="commission-program-update.label.amount"/><span class='required-field'>*</span></label> <form:input
													path="otherCommissionDTO[${status.index}].amount" id="otherCommissionDTO[${status.index}].amount" 
													cssClass="form-control padding-left_25px amount"
													 onblur="this.value=this.value.trim();"/>
												<h3 class="flat-dollar"><spring:message code="commission-program-update.label.$"/></h3>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="otherCommissionDTO[${status.index}].amountEr" class="red-error">&nbsp;</span>
												</div>
											</fieldset>
											<c:if test="${status.index ne 0}">
											<fieldset class='col-sm-1 textCenter marginT30'>
												<span class='glyphicon glyphicon-trash delete-icons'
													style='cursor: pointer ;margin-left: 20px;'></span>
											</fieldset>
											</c:if>
											</div>
											</c:forEach>
										</fieldset>
										<div class="">
										</div> 
									</div>
									<!--Panel Action Button End -->
								<button type="button" class="addRow add-btn-style">
									<span class="glyphicon glyphicon-plus"></span>
								</button> 
								<!-- Page Form End -->
							</div>
							<div class="form-horizontal main-content-holder bck-color">
								<fieldset class="col-sm-12">
									<label class="col-sm-2 other-fee-label"><spring:message code="commission-program-update.label.otherfees"/></label>
								</fieldset>
								<div class="form-group">
									<label class="control-label col-sm-4"><spring:message code="commission-program-update.label.monbfeeamt"/></label>
									<div class="col-sm-4">
										<form:input cssClass="form-control padding-left_25px merchantOnBoardingFee" 
											 path="merchantOnBoardingFee" id="merchantOnBoardingFee" onblur="this.value=this.value.trim();validateMerchantOnBoardingFee()" />
										<h3 class="dollar"><spring:message code="commission-program-update.label.$"/></h3>
										<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title=""> <span id="merchantOnBoardingFeeEr" class="red-error">&nbsp;</span></div>
									</div>
								</div>
							</div>
							<div class="col-sm-12 button-content">
								<fieldset class="col-sm-7 pull-right">
									<input type="submit" class="form-control button pull-right marginL10" id ="createFeePgm" value="<spring:message code="commission-program-update.label.updatebutton"/>" />
									<input type="button" class="form-control button pull-right marginL10" value="<spring:message code="commission-program-update.label.cancelbutton"/>" id="cancel"/>
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
	<script src="../js/commission-program.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
	var array =  new Array('${commissionDTO.otherCommissionDTO}').toString();
	array = array.replace("[","");
	array = array.replace("]","");
	array = array.split(",");
	//alert(array.length);
	var row = array.length-1;

		$(document).ready(function() {
			var val = document.getElementById("otherCommissionDTO[0].commissionType").value;
			if(val=="slab"){
				$( ".flat").hide();
				$( ".from").show();
				$( ".to").show();
				$( ".amnt").show(); 
			}
			else{
				$( ".flat").show();
				$( ".from").hide();
				$( ".to").hide();
				$( ".amnt").hide(); 
				$(".delete-icons").hide();
			}
			
$( "#navListId3" ).addClass( "active-background" );
			
			$(".addRow").click(function() {
				
				var val = document.getElementById("otherCommissionDTO[0].commissionType").value;
				
				if(val=="slab"){
					
					row++;
					var newFilterRow = "<fieldset class='col-sm-12 padding0 filter-content remove-row' style='margin-left: 0px;'>"
					+"<fieldset class='col-sm-4' style='width:18.66%'>"
					+"</fieldset>"
					+"<div class='col-sm-12' style='margin-left: 220px;'>"
					+"<fieldset class='col-sm-4 marginML-25 from'>"
					  +"<label data-toggle="tooltip" data-placement="top" title="">From Volume<span class='required-field'>*</span></label> <input type='text' value=''"
					  +"name='otherCommissionDTO["+row+"].fromValue' id='otherCommissionDTO["+row+"].fromValue' class='form-control fromValue'"
					  +"style='padding-left: 25px' />"
					  +"<h3 class='flat-dollar'>$</h3>"
					  +"<div class='discriptionErrorMsg'>"
					  +"<span id='otherCommissionDTO["+row+"].fromValueEr' class='red-error'>&nbsp;</span>"
					  +"</div>"
					  +"</fieldset>"
					  +"<fieldset class='col-sm-4 marginML-25 to'>"
					  +"<label data-toggle="tooltip" data-placement="top" title="">To Volume<span class='required-field'>*</span></label> <input type='text' value=''"
					  +"name='otherCommissionDTO["+row+"].toValue' id='otherCommissionDTO["+row+"].toValue' class='form-control toValue'"
					  +"style='padding-left: 25px' />"
					  +"<h3 class='flat-dollar'>$</h3>"
					  +"<div class='discriptionErrorMsg'>"
					  +"<span id='otherCommissionDTO["+row+"].toValueEr' class='red-error'>&nbsp;</span>"
					  +"	</div>"
					  +"</fieldset>" 
					  +"<fieldset class='col-sm-4 marginML-25 amnt'>"
					  +"<label data-toggle="tooltip" data-placement="top" title="">Amount<span class='required-field'>*</span></label> <input type='text' value=''"
					  +"name='otherCommissionDTO["+row+"].amount' id='otherCommissionDTO["+row+"].amount' class='form-control amount'"
					  +"style='padding-left: 25px' />"
					  +"<h3 class='flat-dollar'>$</h3>"
					  +"<div class='discriptionErrorMsg'>"
					  +"<span id='otherCommissionDTO["+row+"].amountEr' class='red-error'>&nbsp;</span>"
					  +"</div>"
					  +"</fieldset>"
					 
					  +"<span class='glyphicon glyphicon-trash delete-icon' style = 'cursor: pointer ;margin: 30px 20px 20px 30px ;'></span></div></fieldset></fieldset>";
					  $(".added-row").append(newFilterRow);
				}
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

		 	$('body').on('click', '.delete-icon', function() {
				var deletedSelctOption = $(this).siblings().eq(0).find('.commissionType').find('option:selected').attr('class');
				$(this).parent().remove(); 
				
			}); 
		 	$('body').on('click', '.delete-icons', function() {
				var deletedSelctOption = $(this).parent().siblings().eq(0).find('#otherCommissionDTO[0].commissionType').find('option:selected').attr('class');
				$(this).parent().parent().remove();
				
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