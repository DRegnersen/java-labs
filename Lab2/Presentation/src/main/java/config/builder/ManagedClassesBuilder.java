package config.builder;

public interface ManagedClassesBuilder {
    SqlShowingValueBuilder withManagedClasses(Class<?>... managedClasses);
}
