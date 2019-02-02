package com.codetaylor.mc.athenaeum.network;

public class NetworkEntityIdSupplier {

  private int nextId;

  public int get() {

    return this.nextId;
  }

  public int getAndIncrement() {

    int nextId = this.nextId;
    this.nextId += 1;
    return nextId;
  }

}
