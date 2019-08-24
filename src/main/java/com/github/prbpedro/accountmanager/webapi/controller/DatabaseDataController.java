package com.github.prbpedro.accountmanager.webapi.controller;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.github.prbpedro.accountmanager.domain.services.interfaces.IDatabaseService;
import com.github.prbpedro.accountmanager.domain.util.Startup;

@Path("data")
public class DatabaseDataController {

	@GET
	public Response getData() {
		try {
			return Response.status(Response.Status.OK).entity(Startup.getContainer().select(IDatabaseService.class).get().getDatabaseData()).build();
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
