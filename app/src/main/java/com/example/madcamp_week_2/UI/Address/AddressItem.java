package com.example.madcamp_week_2.UI.Address;

public class AddressItem implements Comparable {
    private byte[] image;
    private String name;
    private String address;
    private String number;

    public AddressItem(byte[] image, String name, String address, String number) {
        this.image = image;
        this.name = name;
        this.address = address;
        this.number = number;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int compareTo(Object o) {
        AddressItem i = (AddressItem) o;
        return this.name.compareTo(i.name);
    }

}
