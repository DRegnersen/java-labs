package config.builder;

import config.Strategy;

public interface StrategyBuilder {
    ManagedClassesBuilder withStrategy(Strategy strategy);
}
