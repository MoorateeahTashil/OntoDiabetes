<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="masterPage.jsp"%>

<%
if (session.getAttribute("userID") == null) {

	String redirectURL = "sessionExpired.jsp";
	response.sendRedirect(redirectURL);
}
%>

<%@ page import="OntoDiabetes.DatabaseConnection"%>


<%
final Connection con;
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
// variables
DatabaseConnection db = new DatabaseConnection();

final String url = db.getUrlConnection();
final String user = db.getUser();
final String password = db.getPassword();
final String id = request.getParameter("patientid");
// establish the connection
%>


<div class="container-fluid">

	<!-- Page Heading -->
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">Feet</h1>
	</div>

	<div class="container">

		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">

				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">Feet Care
							Guidelines</h6>
					</div>
					<div class="card-body">

						<div class="row">
							<div class="col-lg-12">
								<div class="p-5">
									<strong>PATIENT INSTRUCTIONS FOR THE CARE OF THE FOOT
										IN DIABETICS </strong>
									<ul>
										<li>Do not smoke</li>
										<li>Inspect feet regularly for cuts, blisters and
											scratches. Once a week a thorough examination with mirrors is
											necessary to see the soles of the feet and in between toes.</li>
										<li>Wash feet daily - dry carefully between toes.</li>
										<li>If feet feel cold at night during winter, wear socks.
										</li>
										<li>Do not walk on hot surfaces barefoot.</li>
										<li>Do not walk barefoot.</li>
										<li>Do not use any chemical agents to remove corns and
											callousses. See a doctor or a podiatrist (chiropodist) for
											these.</li>
										<li>Inspect visually and by hand the inside of shoes
											before wearing them especially foreign objects, nailpoints,
											torn linings and rough areas.</li>
										<li>Do not soak feet.</li>
										<li>For dry feet, use babyoil or emollient cream but not
											in between toes.</li>
										<li>Wear properly fitting stockings.Do not wear mended
											stockings, avoid stockings with seams, change stockings
											daily.</li>
										<li>Shoes should be comfortable at time of purchase - do
											not depend on them to stretch out. Always wear socks with
											shoes.</li>
										<li>Cut nails straight across.</li>
										<li>Do not cut corns and callousses. Take advice from
											doctor or chiropodist.</li>
										<li>Do not cross your legs while sitting. This may cause
											pressure on nerves and blood vessels.</li>
										<li>If fungal infections occur in between toes, leading
											to maceration of skin then a simple antimycotic topical
											application like Econazole or Tolnaftate will cure the
											infection fairly quickly.</li>
									</ul>
								</div>
							</div>
						</div>

					</div>
				</div>


			</div>
		</div>

	</div>

</div>

<%@ include file="masterPage2.jsp"%>

