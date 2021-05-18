package com.cctpl.fooddeliveryadmin.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class CardId {
    @Exclude
    public String CardId;
    public <T extends CardId> T withId(@NonNull final String id){
        this.CardId = id;
        return (T)this;
    }
}
