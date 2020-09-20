package br.com.stoom.challenge.exception;

public class GoogleApiInvalidAddressInfo extends IllegalArgumentException {

    public GoogleApiInvalidAddressInfo() {
        super("Invalid address information to retrieve latitude and longitude from google");
    }
}
