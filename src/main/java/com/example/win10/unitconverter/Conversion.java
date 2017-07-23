package com.example.win10.unitconverter;

public class Conversion {
    private long typeId;
    private long fromUnitId;
    private long toUnitId;
    private double fromOffset;
    private double multiplier;
    private double divizor;
    private double toOffSet;

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public long getFromUnitId() {
        return fromUnitId;
    }

    public void setFromUnitId(long fromUnitId) {
        this.fromUnitId = fromUnitId;
    }

    public long getToUnitId() {
        return toUnitId;
    }

    public void setToUnitId(long toUnitId) {
        this.toUnitId = toUnitId;
    }

    public double getFromOffset() {
        return fromOffset;
    }

    public void setFromOffset(double fromOffset) {
        this.fromOffset = fromOffset;
    }


    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getDivizor() {
        return divizor;
    }

    public void setDivizor(double divizor) {
        this.divizor = divizor;
    }

    public double getToOffSet() {
        return toOffSet;
    }

    public void setToOffSet(double toOffSet) {
        this.toOffSet = toOffSet;
    }
}