package com.github.prbpedro.accountmanager.webapi;

import java.util.HashSet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.LoggerFactory;

import com.github.prbpedro.accountmanager.domain.util.Startup;
import com.github.prbpedro.accountmanager.webapi.controller.TransferTransactionController;

import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;

public class Main {

	public static void main(String[] args) {
		Server server = null;
		try {
			System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
			System.setProperty(org.slf4j.impl.SimpleLogger.SHOW_THREAD_NAME_KEY, "true");

			HashSet<Class<?>> he = new HashSet<Class<?>>();
			he.add(TransferTransactionController.class);
			ResourceConfig resourceConfig = new ResourceConfig(he);
			resourceConfig.registerClasses(OpenApiResource.class, AcceptHeaderOpenApiResource.class);

			ServletHolder apiServlet = new ServletHolder(new ServletContainer(resourceConfig));
			apiServlet.setInitOrder(1);

			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
			context.setContextPath("/");
			context.addServlet(apiServlet, "/account-manager/*");
			
			Startup.configure();
			
			ContextHandlerCollection contexts = new ContextHandlerCollection();
			contexts.setHandlers(new Handler[] { context });
			server = new Server(5000);
			server.setHandler(contexts);
			server.start();
			server.join();
			
		} catch (Exception ex) {
			LoggerFactory.getLogger(Main.class).error(ex.getMessage(), ex);
		} finally {
			if (server != null) {
				server.destroy();
			}
		}
	}
}
