package com.shopping;

public class ShippingAddress {
    private final String to;
    private final String lineOne;
    private final String lineTwo;
    private final String city;
    private final String state;
    private final String zip;

    public ShippingAddress(final String to, final String lineOne, final String lineTwo, final String city,
                           final String state, final String zip) {
        this.to = to;
        this.lineOne = lineOne;
        this.lineTwo = lineTwo;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getTo() {
        return to;
    }

    public String getLineOne() {
        return lineOne;
    }

    public String getLineTwo() {
        return lineTwo;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }
}
