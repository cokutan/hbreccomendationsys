package reccomendationengine;

import org.testcontainers.containers.PostgreSQLContainer;

public class SharedPostgresqlnMemoryDBContainer extends PostgreSQLContainer<SharedPostgresqlnMemoryDBContainer> {
	private static final String IMAGE_VERSION = "postgres:latest";
	private static SharedPostgresqlnMemoryDBContainer container;

	private SharedPostgresqlnMemoryDBContainer() {
		super(IMAGE_VERSION);
	}

	public static SharedPostgresqlnMemoryDBContainer getInstance() {
		if (container == null) {
			container = new SharedPostgresqlnMemoryDBContainer();
		}
		return container;
	}

	@Override
	public void start() {
		super.start();
		System.setProperty("DB_URL", container.getJdbcUrl());
		System.setProperty("DB_USERNAME", container.getUsername());
		System.setProperty("DB_PASSWORD", container.getPassword());
	}

	@Override
	public void stop() {
		// do nothing, JVM handles shut down
	}
}