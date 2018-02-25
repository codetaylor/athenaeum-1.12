package com.codetaylor.mc.athenaeum.registry.strategy;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IForgeRegistryEventRegistrationStrategy<T extends IForgeRegistryEntry<T>> {

  void register(IForgeRegistry<T> forgeRegistry);
}
