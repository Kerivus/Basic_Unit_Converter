package com.example.win10.unitconverter;

public class Type {
    private long typeId;
    private String typeName;
    private boolean mIsFavourited;

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isFavourited(){
        return mIsFavourited;
    }

    public void setFavourited(boolean favourited){
        mIsFavourited = favourited;
    }
    @Override
    public String toString() {
        return typeName;
    }
}