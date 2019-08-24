package com.github.prbpedro.accountmanager.webapi.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.github.prbpedro.accountmanager.domain.services.DatabaseService;
import com.github.prbpedro.accountmanager.domain.util.Startup;

@Path("data")
public class DatabaseDataController {

	@GET
	public Response getData() throws IOException {
		try {
			return Response.status(Response.Status.OK).entity(Startup.getInjector().getInstance(DatabaseService.class).getDatabaseData()).build();
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
