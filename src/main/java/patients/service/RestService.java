package patients.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import patients.model.Patient;

@Path("/")
public class RestService {
	
	@GET
	@Produces("application/json")
	@Path("/patients/login")
	public Response login(@HeaderParam("Authorization") String auth,
						  @Context HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		try {
			if (auth == null) {
				return Response
							.status(Response.Status.UNAUTHORIZED)
							.build();
			} else {
				boolean authorized = AuthenticationService.INSTANCE.checkAuthorization(auth);
				if (authorized) {
					session.setAttribute("authorized", true);
					return Response
							.status(Response.Status.OK)
							.entity("Login accepted")
							.build();
				} else {
					return Response
							.status(Response.Status.UNAUTHORIZED)
							.entity("Wrong login")
							.build();
				}
			}
		} catch (Exception e) {
			return Response
						.status(Response.Status.INTERNAL_SERVER_ERROR)
						.build();
		}
	}
	
	@GET
	@Produces("application/json")
	@Path("/patients/logout")
	public Response logout(@Context HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.removeAttribute("authorized");
		
		return Response
				.status(Response.Status.OK)
				.entity("")
				.build();
		
	}
	
	@GET
	@Produces("application/json")
	@Path("/patients")
	public Response getPatients() {
		
		List<Patient> articles = PatientsService.INSTANCE.getPatients();
		
		return Response
				.status(Response.Status.OK)
				.entity(articles)
				.build();
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/patients")
	public Response getPatientsSlice(LazyLoadData lazyLoadData) {
		
		PatientSliceAndCount slice = PatientsService.INSTANCE.getPatientsSlice(lazyLoadData);
		
		return Response
				.status(Response.Status.OK)
				.entity(slice)
				.build();
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/insurances")
	public Response getInsurancesSlice(LazyLoadData lazyLoadData) {
		
		InsuranceSliceAndCount insurances = PatientsService.INSTANCE.getInsurancesSlice(lazyLoadData);
		
		return Response
				.status(Response.Status.OK)
				.entity(insurances)
				.build();
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/zips")
	public Response getZipsSlice(LazyLoadData lazyLoadData) {
		
		ZipSliceAndCount zips = PatientsService.INSTANCE.getZipsSlice(lazyLoadData);
		
		return Response
				.status(Response.Status.OK)
				.entity(zips)
				.build();
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/patients/create")
	public Response createPatient(Patient p) {
		
		PatientsService.INSTANCE.createPatient(p);
		
		return Response
				.status(Response.Status.OK)
				.build();
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/patients/update")
	public Response updatePatient(Patient p) {
		
		PatientsService.INSTANCE.updatePatient(p);
		
		return Response
				.status(Response.Status.OK)
				.build();
	}
	
	@POST
	@Produces("application/json")
	@Path("/patients/delete/{internalNumber}")
	public Response deletePatient(@PathParam("internalNumber") int internalNumber) throws SQLException {
		
		PatientsService.INSTANCE.deleteArticle(internalNumber);
		
		return Response
				.status(Response.Status.OK)
				.build();
	}

	
}
