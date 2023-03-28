package com.rebillard.exception;

public class HoleOfferCreationFailedException extends RuntimeException{
  public HoleOfferCreationFailedException() {
    super("can't create the hole offer");
  }

}
