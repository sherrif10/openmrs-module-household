<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:require privilege="Manage Household Locations" otherwise="/login.htm"
	redirect="/module/household/editHouseholdLocationLevel.form" />

<%@ include file="/WEB-INF/template/header.jsp"%>

<h2><spring:message code="household.title" /></h2>

<%@ include file="localHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>

<!-- SPECIALIZED STYLES FOR THIS PAGE -->
<style type="text/css">
	td.tableCell {padding-left:4px; padding-right:4px; padding-top:2px; padding-bottom:2px; vertical-align:top}
</style>

<!-- JQUERY FOR THIS PAGE -->
<script type="text/javascript"><!--

	var $j = jQuery;

	$j(document).ready(function(){

		// toggle the required checkbox
		$j('#requiredCheckbox').click(function () {
			if ($j(this).is(':checked')){
				$j('#required').attr('value', "true");
			}
			else {
				$j('#required').attr('value', "false");
			} 
		});		
	});
-->
</script>
<!-- END JQUERY -->



<br/>
<br/>

<!--  DISPLAY ANY ERROR MESSAGES -->
<c:if test="${fn:length(errors.allErrors) > 0}">
	<c:forEach var="error" items="${errors.allErrors}">
		<span class="error"><spring:message code="${error.code}"/></span><br/><br/>
	</c:forEach>
	<br/>
</c:if>


<div><b class="boxHeader"><spring:message code="household.householdLocation.editLevel" /></b>


<form id="editHouseholdLocationLevel" action="updateHouseholdLocationLevel.form" method="post">
<input type="hidden" name="levelId" value="${level.id}"/>

<table cellspacing="0" cellpadding="0" class="box">

<tr>
	<td class="tableCell" style="font-weight:bold"><nobr><spring:message code="general.name" />:</nobr></td>
	<td class="tableCell">
		<input type="text" name="name" value="${level.name}"/>
	</td>
	<td class="tableCell" width="60%">&nbsp;</td>
</tr>

<tr>
	<td class="tableCell" style="font-weight:bold"><nobr><spring:message code="household.householdLocation.householdLocationField" />:</nobr></td>
	<td class="tableCell">
		<select name="householdLocationField">
			<option value=""></option>
			<c:forEach var="field" items="${householdLocationFields}">
				<c:if test="${field.name != null}">
					<option value="${field.name}" <c:if test="${level.householdLocationField == field}">selected</c:if> >
						<spring:message code="${field.name}"/>
					</option>
				</c:if>
			</c:forEach>
			<option value="">(<spring:message code="general.none"/>)</option>
		</select>
	</td>
	<td class="tableCell">&nbsp;</td>
</tr>

<tr>
	<td class="tableCell" style="font-weight:bold"><nobr><spring:message code="household.householdLocation.required" />:</nobr></td>
	<td class="tableCell">
		<input type="hidden" id="required" name="required" value="${level.required}"/>
		<input type="checkbox" id="requiredCheckbox" name="requiredCheckbox" value="true" <c:if test="${level.required == true}">checked</c:if> />
	</td>
</tr>

<tr>
	<td class="tableCell">
		<button type="submit">
			<spring:message code="general.save" text="Save"/>
		</button>
		<a href="${pageContext.request.contextPath}/module/household/manageHouseholdLocation.form">
			<button type="button">
				<spring:message code="general.cancel" text="Cancel"/>
			</button>
		</a>		
	</td>
	<td class="tableCell">&nbsp;</td>
	<td class="tableCell">&nbsp;</td>
</tr>


</table>


</form>

</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>