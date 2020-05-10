package com.codetaylor.mc.athenaeum.module;

import net.minecraftforge.eventbus.api.Event;

public interface IFMLEventRoute<E extends Event> {

  void routeEvent(E event);

}
