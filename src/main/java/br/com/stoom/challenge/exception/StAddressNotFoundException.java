package br.com.stoom.challenge.exception;

public class StAddressNotFoundException extends IllegalArgumentException {

    public StAddressNotFoundException() {
        super("Id from Address not found");
    }
}
