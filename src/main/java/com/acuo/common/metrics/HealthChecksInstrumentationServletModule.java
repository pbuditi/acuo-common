package com.acuo.common.metrics;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.*;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.servlet.ServletModule;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

public class HealthChecksInstrumentationServletModule extends ServletModule {

	private final HealthCheckRegistry healthCheckRegistry;

	public HealthChecksInstrumentationServletModule(HealthCheckRegistry healthCheckRegistry){
		this.healthCheckRegistry = healthCheckRegistry;
	}
	
	@Override
	protected void configureServlets() {
		bind(AdminServlet.class).asEagerSingleton();
		bind(MetricsServlet.class).asEagerSingleton();
		bind(PingServlet.class).asEagerSingleton();
		bind(ThreadDumpServlet.class).asEagerSingleton();

		serve("/admin").with(AdminServlet.class);
		serve("/admin/metrics").with(MetricsServlet.class);
		serve("/admin/ping").with(PingServlet.class);
		serve("/admin/threads").with(ThreadDumpServlet.class);
		serve("/admin/healthcheck").with(HealthCheckServlet.class);

		// Ensure that the binding exists, even if it is empty.
		Multibinder.newSetBinder(binder(), HealthCheck.class);
	}

	@Provides
	@Singleton
	@Inject
	public HealthCheckServlet buildHealthCheckServlet(Set<HealthCheck> healthChecks) {
		for (HealthCheck healthCheck : healthChecks) {
			healthCheckRegistry.register(healthCheck.getClass().getSimpleName(), healthCheck);
		}
		return new HealthCheckServlet();
	}
}