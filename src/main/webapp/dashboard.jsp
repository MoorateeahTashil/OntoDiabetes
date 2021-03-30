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
// establish the connection
%>



<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Page Heading -->
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">Dashboard</h1>
	</div>
	<%
	con = DriverManager.getConnection(url, user, password);
	try {
		Statement st = con.createStatement();
		Statement st2 = con.createStatement();
		Statement st3 = con.createStatement();
		Statement st4 = con.createStatement();
		Statement st5 = con.createStatement();

		String query = "select * from [OntoDiabetes_Task] WHERE [assign_to] = " + session.getAttribute("userID") + ";";
		String CurrentStage = "select * from [OntoDiabetes_PatientStage] WHERE [patient_id] = "
		+ session.getAttribute("userID") + ";";
		String NumberOfTasksPending = "select COUNT(*) AS total  from [OntoDiabetes_Task] WHERE Status='Pending'  and [assign_to] = "
		+ session.getAttribute("userID") + ";";
		String NumberOfTasks = "select COUNT(*) AS total  from [OntoDiabetes_Task] WHERE  [assign_to] = "
		+ session.getAttribute("userID") + ";";
		
		
		String DoctorName = "select surname+ ' ' + ISNULL(middlename,'') + ' ' + lastname as name from OntoDiabetes_DoctorDetails join OntoDiabetes_Doctor_Patient on OntoDiabetes_DoctorDetails.userid = OntoDiabetes_Doctor_Patient.doctor_id where patient_id = "+ session.getAttribute("userID") + ";";

		// send and execute SQL query in Database software
		ResultSet rs = st.executeQuery(query);
		ResultSet rs1 = st2.executeQuery(CurrentStage);
		ResultSet rs2 = st3.executeQuery(NumberOfTasksPending);
		ResultSet rs3 = st4.executeQuery(NumberOfTasks);
		ResultSet rs4 = st5.executeQuery(DoctorName);

		int numberTaskP = 0;
		int numberTask = 0;
		while (rs1.next()) {
			CurrentStage = rs1.getString("stage");
		}

		while (rs2.next()) {
			numberTaskP = rs2.getInt("total");
		}

		while (rs3.next()) {
			numberTask = rs3.getInt("total");
		}
		
		while (rs4.next()) {
			DoctorName = rs4.getString("name");
		}
		
		if(DoctorName == null || DoctorName == "")
		{
			DoctorName = "Not yet Assigned";
		}

		float percentage = 100 - (((float)numberTaskP / (float) numberTask) * 100);
	%>

	<div class="row">

		<!-- Earnings (Monthly) Card Example -->
		<div class="col-xl-3 col-md-6 mb-4">
			<div class="card border-left-primary shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div
								class="text-xs font-weight-bold text-primary text-uppercase mb-1">
								Current Stage</div>
							<div class="h5 mb-0 font-weight-bold text-gray-800"><%=CurrentStage%></div>
						</div>
						<div class="col-auto">
							<i class="fas fa-briefcase-medical fa-2x text-gray-300"></i>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Earnings (Monthly) Card Example -->
		<div class="col-xl-3 col-md-6 mb-4">
			<div class="card border-left-success shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div
								class="text-xs font-weight-bold text-success text-uppercase mb-1">
								Doctor Assigned</div>
							<div class="h5 mb-0 font-weight-bold text-gray-800"><%=DoctorName%></div>
						</div>
						<div class="col-auto">
							<i class="fas fa-user-md fa-2x text-gray-300"></i>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Earnings (Monthly) Card Example -->
		<div class="col-xl-3 col-md-6 mb-4">
			<div class="card border-left-info shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div
								class="text-xs font-weight-bold text-info text-uppercase mb-1">Tasks
							</div>
							<div class="row no-gutters align-items-center">
								<div class="col-auto">
									<div class="h5 mb-0 mr-3 font-weight-bold text-gray-800"><%=percentage%>%
									</div>
								</div>
								<div class="col">
									<div class="progress progress-sm mr-2">
										<div class="progress-bar bg-info" role="progressbar"
											style="width: <%=percentage%>%"
											aria-valuenow="<%=percentage%>" aria-valuemin="0"
											aria-valuemax="100"></div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-auto">
							<i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Pending Requests Card Example -->
		<div class="col-xl-3 col-md-6 mb-4">
			<div class="card border-left-warning shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div
								class="text-xs font-weight-bold text-warning text-uppercase mb-1">
								Pending Tasks</div>
							<div class="h5 mb-0 font-weight-bold text-gray-800"><%=numberTaskP%></div>
						</div>
						<div class="col-auto">
							<i class="fa fa-tasks fa-2x text-gray-300"></i>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- Content Row -->
	<div class="row">

		<!-- Earnings (Monthly) Card Example -->
		<div class="col-xl-12 col-md-12 mb-4">
			<div class="card shadow">
				<div class="card-header">
					<h6 class="m-0 font-weight-bold text-primary">Task List</h6>
				</div>
				<div class="card-body">
					<div class="table-responsive">
						<table class="table table-bordered" id="dataTable" width="100%"
							cellspacing="0">
							<thead>
								<tr>
									<th>Description</th>
									<th>Status</th>
									<th>Link</th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th>Description</th>
									<th>Status</th>
									<th>Link</th>
								</tr>
							</tfoot>
							<tbody>

								<%
								while (rs.next()) {
								%>
								<tr>
									<td><%=rs.getString("description")%></td>
									<td><%=rs.getString("status")%></td>
									<td><a href="<%=rs.getString("link")%>"
										class="fa fa-link"></a></td>

								</tr>
								<%
								}

								} catch (Exception e) {
								%><%=e%>
								<%
								}

								con.close();
								%>

							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>


<%@ include file="masterPage2.jsp"%>
